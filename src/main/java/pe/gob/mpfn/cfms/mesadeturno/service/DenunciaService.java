package pe.gob.mpfn.cfms.mesadeturno.service;

import org.springframework.http.ResponseEntity;
import pe.gob.mpfn.cfms.generales.libreria.core.dto.PaginacionWrapperDTO;
import pe.gob.mpfn.cfms.mesadeturno.dto.*;
import pe.gob.mpfn.cfms.mesadeturno.dto.partesinvolucradas.DatosGeneralesDenunciaRecord;
import pe.gob.mpfn.cfms.mesadeturno.dto.response.DatosGeneralesResponse;

public interface DenunciaService {
    ResponseEntity<PaginacionWrapperDTO<DenunciaRegistradaDto>> listarDenunciasRegistradas(ListarDenunciasDto listarDenunciasDto) throws Exception;
    DatosGeneralesResponse registrarDatosGenerales(DatosGeneralesDto datosGeneralesDto) throws Exception;
    String validarSiMesaEstaDeTurno();
    ResponseDTO<String> identificarDenunciaDuplicada(CompletarRegistrarDenunciaDto denunciaDto);
    ResponseEntity<String> completarRegistrarDenuncia(Integer tipoDenuncia, CompletarRegistrarDenunciaDto datosDenuncia) throws Exception;
    DatosGeneralesDenunciaRecord obtenerPorCasoId(String casoId);
    ResponseEntity<String> obtenerEspecialidad() throws Exception;
}