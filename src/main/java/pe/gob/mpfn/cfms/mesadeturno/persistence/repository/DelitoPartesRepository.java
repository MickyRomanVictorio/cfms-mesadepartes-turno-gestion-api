package pe.gob.mpfn.cfms.mesadeturno.persistence.repository;

import pe.gob.mpfn.cfms.mesadeturno.dto.DelitoDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.ImputadoRenadesppleDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.PersonaJuridicaDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.PersonaNaturalDto;

import java.util.List;

public interface DelitoPartesRepository {

    public List<DelitoDto> listarDelitos(String idCaso ) throws Exception ;
    public List<PersonaNaturalDto> listarPersonasNaturales(String idCaso ) throws Exception ;
    public List<PersonaJuridicaDto> listarPersonasJuridicas(String idCaso ) throws Exception ;
    public List<ImputadoRenadesppleDto> listarImputadosRenadespple(String idCaso ) throws Exception ;

}