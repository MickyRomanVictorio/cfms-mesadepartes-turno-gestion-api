package pe.gob.mpfn.cfms.mesadeturno.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import pe.gob.mpfn.cfms.mesadeturno.dto.*;
import pe.gob.mpfn.cfms.mesadeturno.dto.informaciongeneral.DireccionInfoDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.renadespple.OperacionFichaRequest;

public interface RenadesppleService {

    ResponseEntity<List<TiposGradoDelitoDto>> listarOficinasRenadespple() throws Exception;

    ResponseEntity<FichaRenadesppleDto> obtenerFichaRenadespple(String idCaso, String idCasoSujeto) throws Exception;

    ResponseEntity<List<ItemLista>> listarMotivosDetencion() throws Exception;

    ResponseEntity<List<ItemLista>> listarSituacionesJuridicas() throws Exception;

    ResponseEntity<List<DireccionInfoDto>> listarDireccionesInformacionGeneral(String idSujetoCaso) throws Exception;

    ResponseEntity<String> eliminarDireccionInformacionGeneral(String idDireccion) throws Exception;

    ResponseEntity<String> operacionDireccionInformacionGeneral(DireccionInfoDto direccionInfoDto) throws Exception;

    ResponseEntity<String> actualizarFichaRenadespple(OperacionFichaRequest operacionFichaRequest) throws Exception;

    ResponseEntity<MomentoDetencionDto> obtenerMomentoDetencion(String idCaso) throws Exception;

    ResponseEntity<InvestigacionPolicialDto> obtenerInvestigacionPolicial(String idSujetoCaso) throws Exception;

    ResponseEntity<DatosReniecDto> obtenerDatosReniec(String idSujetoCaso) throws Exception;
}
