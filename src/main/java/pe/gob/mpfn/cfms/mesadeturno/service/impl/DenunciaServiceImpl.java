package pe.gob.mpfn.cfms.mesadeturno.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.gob.mpfn.cfms.generales.libreria.core.dto.PaginacionWrapperDTO;
import pe.gob.mpfn.cfms.generales.libreria.core.exception.UnprocessableEntityException;
import pe.gob.mpfn.cfms.mesadeturno.client.webclient.FirmaDigitalClient;
import pe.gob.mpfn.cfms.mesadeturno.common.util.JwtTokenUtility;
import pe.gob.mpfn.cfms.mesadeturno.dto.*;
import pe.gob.mpfn.cfms.mesadeturno.dto.partesinvolucradas.DatosGeneralesDenunciaRecord;
import pe.gob.mpfn.cfms.mesadeturno.dto.response.DatosGeneralesResponse;
import pe.gob.mpfn.cfms.mesadeturno.persistence.repository.DenunciaRepository;
import pe.gob.mpfn.cfms.mesadeturno.service.CargoService;
import pe.gob.mpfn.cfms.mesadeturno.service.DenunciaService;
import pe.gob.mpfn.cfms.mesadeturno.service.MensajeValidacionService;
import pe.gob.mpfn.cfms.mesadeturno.util.Fechas;

import static pe.gob.mpfn.cfms.mesadeturno.util.Constantes.*;

@Service
@Slf4j
public class DenunciaServiceImpl implements DenunciaService {
    private final DenunciaRepository denunciaRepository;
    private final JwtTokenUtility jwtTokenUtility;
    private final FirmaDigitalClient firmaDigitalService;
    private final CargoService cargoService;
    private final MensajeValidacionService mensajeValidacionService;
    public DenunciaServiceImpl(
            DenunciaRepository denunciaRepository,
            JwtTokenUtility jwtTokenUtility,
            FirmaDigitalClient firmaDigitalService,
            CargoService cargoService,
            MensajeValidacionService mensajeValidacionService
    ) {
        this.denunciaRepository = denunciaRepository;
        this.jwtTokenUtility = jwtTokenUtility;
        this.firmaDigitalService = firmaDigitalService;
        this.cargoService = cargoService;
        this.mensajeValidacionService = mensajeValidacionService;
    }

    @Override
    public ResponseEntity<PaginacionWrapperDTO<DenunciaRegistradaDto>> listarDenunciasRegistradas(ListarDenunciasDto listarDenunciasDto) throws Exception {
        PaginacionWrapperDTO<DenunciaRegistradaDto> denunciasRegistradas = denunciaRepository.listarDenunciasRegistradas(listarDenunciasDto);
        return new ResponseEntity<>(denunciasRegistradas, HttpStatus.OK);
    }

    @Override
    public DatosGeneralesResponse registrarDatosGenerales(DatosGeneralesDto datosGeneralesDto) throws Exception {
        return denunciaRepository.registrarDatosGenerales(datosGeneralesDto
                , jwtTokenUtility.getUsuario()
        );
    }

    @Override
    public String validarSiMesaEstaDeTurno() {
        return denunciaRepository.validarSiMesaEstaDeTurno(jwtTokenUtility.getUsuario().getCodDespacho());
    }

    @Override
    public ResponseDTO<String> identificarDenunciaDuplicada(CompletarRegistrarDenunciaDto denunciaDto) {
        if (denunciaDto == null || denunciaDto.getPartesInvolucradas() == null || denunciaDto.getDelitos() == null) {
            throw new UnprocessableEntityException(C_42202001_BODY_REQUERIDO,
                    mensajeValidacionService.obtenerMensajeValidacion(C_42202001_BODY_REQUERIDO));
        }
        return denunciaRepository.identificarDenunciaDuplicada(denunciaDto);
    }

    @Override
    public ResponseEntity<String> completarRegistrarDenuncia(Integer tipoDenuncia, CompletarRegistrarDenunciaDto datosDenuncia) throws Exception {

        if (datosDenuncia == null || datosDenuncia.getPartesInvolucradas() == null || datosDenuncia.getDelitos() == null) {
            throw new UnprocessableEntityException(C_42202001_BODY_REQUERIDO,
                    mensajeValidacionService.obtenerMensajeValidacion(C_42202001_BODY_REQUERIDO));
        }

        //casteo fecha AM / PM  a 24 horas
        datosDenuncia.getCargo().setFechaHecho(Fechas.convertirFormatoFechaHora(datosDenuncia.getCargo().getFechaHecho()));

        // 1. Registrar los datos de la denuncia
        RespuestaRegistroDenuncia dbResponse = denunciaRepository.completarRegistrarDenuncia(tipoDenuncia, datosDenuncia);
        log.info("idCaso: {}", dbResponse.idCaso());

        // 1.2. Obtener datos generales para denuncias de oficio
        if ( tipoDenuncia == TIPO_DENUNCIA_INVESTIGACION_OFICIO ) {
            DatosGeneralesOficioDto datosGenerales = denunciaRepository.obtenerDatosGeneralesDenunciaOficio(dbResponse.idCaso() );
            datosDenuncia.getCargo().setNumeroCaso(datosGenerales.getNumeroCaso());
        }
        datosDenuncia.getCargo().setNroCaso(datosDenuncia.getCargo().getNumeroCaso());

        // 2. Generar el movimiento para registrar el cargo de ingreso de denuncia
        MovimientoRegistradoDto movimientoRegistrado = denunciaRepository.generarMovimientoCargoIngresoDenuncia(dbResponse.idCaso());
        String idMovimiento = movimientoRegistrado.getIdMovimiento();

        // 2.1. Establecer la fecha de ingreso en el Jasper de cargo de ingreso de denuncia
        if ( tipoDenuncia == TIPO_DENUNCIA_INVESTIGACION_OFICIO ) {
            datosDenuncia.getCargo().setFechaIngreso(movimientoRegistrado.getFechaIngreso());
        }

        // 3. Obtener Jasper de cargo de ingreso de denuncia
        for(PartesIngresoDenunciaDto partes : datosDenuncia.getCargo().getPartes()) {
        	if(partes.getTipoDocumento().contains("-") && partes.getNumeroDocumento().contains("-")) {
        		partes.setTipoDocumento("");
        	}
        }

        // 4. cvd
        String cvd = firmaDigitalService.generarCodigoVerificacion();
        datosDenuncia.getCargo().setCvd(cvd);

        ResponseDTO<String> response = cargoService.procesarDataDenuncia(datosDenuncia, idMovimiento, dbResponse);

        return new ResponseEntity<>(response.getData(), HttpStatus.OK);
    }

	@Override
    public DatosGeneralesDenunciaRecord obtenerPorCasoId(String casoId) {

        return denunciaRepository.getDatosGeneralesDenuncia(casoId, jwtTokenUtility.getUsuario())
                .orElseThrow(RuntimeException::new);

    }

    @Override
    public ResponseEntity<String> obtenerEspecialidad() {
        String especialidad = jwtTokenUtility.getUsuario().getCodEspecialidad();
        return new ResponseEntity<>(denunciaRepository.obtenerEspecialidad(especialidad), HttpStatus.OK);

    }

}
