package pe.gob.mpfn.cfms.mesadeturno.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.gob.mpfn.cfms.mesadeturno.dto.DelitoDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.ImputadoRenadesppleDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.PersonaJuridicaDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.PersonaNaturalDto;
import pe.gob.mpfn.cfms.mesadeturno.persistence.repository.DelitoPartesRepository;
import pe.gob.mpfn.cfms.mesadeturno.service.DelitoPartesService;

import java.util.List;

@Service
public class DelitoPartesServiceImpl implements DelitoPartesService {

    @Autowired
    private DelitoPartesRepository delitoPartesRepository;

    @Override
    public ResponseEntity<List<DelitoDto>> listarDelitos(String idCaso) throws Exception {
        return new ResponseEntity<>(delitoPartesRepository.listarDelitos(idCaso), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<PersonaNaturalDto>> listarPersonasNaturales(String idCaso) throws Exception {
        return new ResponseEntity<>(delitoPartesRepository.listarPersonasNaturales(idCaso), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<PersonaJuridicaDto>> listarPersonasJuridicas(String idCaso) throws Exception {
        return new ResponseEntity<>(delitoPartesRepository.listarPersonasJuridicas(idCaso), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ImputadoRenadesppleDto>> listarImputadosRenadespple(String idCaso) throws Exception {
        return new ResponseEntity<>(delitoPartesRepository.listarImputadosRenadespple(idCaso), HttpStatus.OK);
    }
}
