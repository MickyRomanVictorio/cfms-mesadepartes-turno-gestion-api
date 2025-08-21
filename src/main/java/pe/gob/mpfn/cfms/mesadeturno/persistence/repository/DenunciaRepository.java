package pe.gob.mpfn.cfms.mesadeturno.persistence.repository;

import pe.gob.mpfn.cfms.generales.libreria.core.dto.PaginacionWrapperDTO;
import pe.gob.mpfn.cfms.mesadeturno.bean.Usuario;
import pe.gob.mpfn.cfms.mesadeturno.dto.*;
import pe.gob.mpfn.cfms.mesadeturno.dto.partesinvolucradas.DatosGeneralesDenunciaRecord;
import pe.gob.mpfn.cfms.mesadeturno.dto.response.DatosGeneralesResponse;

import java.util.Optional;

public interface DenunciaRepository {

    PaginacionWrapperDTO<DenunciaRegistradaDto> listarDenunciasRegistradas(ListarDenunciasDto listarDenunciasDto) throws Exception;

    DatosGeneralesResponse registrarDatosGenerales(DatosGeneralesDto datosGeneralesDto, Usuario usuario) throws Exception;

    String validarSiMesaEstaDeTurno(String codigoDespacho);

    ResponseDTO<String> identificarDenunciaDuplicada(CompletarRegistrarDenunciaDto denunciaDto);

    RespuestaRegistroDenuncia completarRegistrarDenuncia(Integer tipoDenuncia, CompletarRegistrarDenunciaDto datosDenuncia) throws Exception;

    MovimientoRegistradoDto generarMovimientoCargoIngresoDenuncia(String idCaso) throws Exception;


    DatosGeneralesOficioDto obtenerDatosGeneralesDenunciaOficio( String idCaso ) throws Exception;
    
    Optional<DatosGeneralesDenunciaRecord> getDatosGeneralesDenuncia(String casoId, Usuario usuario);

    String obtenerEspecialidad( String especialidad );
}