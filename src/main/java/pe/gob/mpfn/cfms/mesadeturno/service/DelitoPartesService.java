package pe.gob.mpfn.cfms.mesadeturno.service;

import org.springframework.http.ResponseEntity;
import pe.gob.mpfn.cfms.mesadeturno.dto.DelitoDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.ImputadoRenadesppleDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.PersonaJuridicaDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.PersonaNaturalDto;

import java.util.List;

public interface DelitoPartesService {

    public ResponseEntity<List<DelitoDto>> listarDelitos(String idCaso) throws Exception;
    public ResponseEntity<List<PersonaNaturalDto>> listarPersonasNaturales(String idCaso) throws Exception;
    public ResponseEntity<List<PersonaJuridicaDto>> listarPersonasJuridicas(String idCaso) throws Exception;
    public ResponseEntity<List<ImputadoRenadesppleDto>> listarImputadosRenadespple(String idCaso) throws Exception;
}