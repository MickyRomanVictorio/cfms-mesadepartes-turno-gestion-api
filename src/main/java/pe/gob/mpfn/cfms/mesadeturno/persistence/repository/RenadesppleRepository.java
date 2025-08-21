package pe.gob.mpfn.cfms.mesadeturno.persistence.repository;

import org.springframework.http.ResponseEntity;
import pe.gob.mpfn.cfms.mesadeturno.dto.*;
import pe.gob.mpfn.cfms.mesadeturno.dto.informaciongeneral.DireccionInfoDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.renadespple.OperacionFichaRequest;

import java.util.List;

public interface RenadesppleRepository {

    List<FichaRenadesppleDto> obtenerFichaRenadespple(String idCaso, String idCasoSujeto) throws Exception;

    List<TiposGradoDelitoDto> listarOficinasRenadespple() throws Exception;

    List<ItemLista> listarMotivosDetencion() throws Exception;

    List<ItemLista> listarSituacionesJuridicas() throws Exception;

    String eliminarDireccionInfo(String idDireccion) throws Exception;

    String actualizarFichaRenadespple(OperacionFichaRequest operacionFichaRequest) throws Exception;

    List<DireccionInfoDto> listarDireccionesInformacionGeneral(String idSujetoCaso) throws Exception;

    String operacionDireccionInfo(DireccionInfoDto direccionInfoDto) throws Exception;

    MomentoDetencionDto obtenerMomentoDetencion(String idCaso) throws Exception;

    InvestigacionPolicialDto obtenerInvestigacionPolicial(String idSujetoCaso) throws Exception;

    DatosReniecDto obtenerDatosReniec(String idSujetoCaso) throws Exception;
}
