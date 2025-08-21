package pe.gob.mpfn.cfms.mesadeturno.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.gob.mpfn.cfms.generales.libreria.core.dto.PaginacionWrapperDTO;
import pe.gob.mpfn.cfms.mesadeturno.dto.BuscarDelitosDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.DelitosDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.RegistrarDelitosDetenidosDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.TiposGradoDelitoDto;
import pe.gob.mpfn.cfms.mesadeturno.persistence.repository.DelitoRepository;
import pe.gob.mpfn.cfms.mesadeturno.service.DelitoService;

import java.util.List;

@Service
public class DelitoServiceImpl implements DelitoService {

    @Autowired
    private DelitoRepository delitoRepository;

    @Override
    public ResponseEntity<List<TiposGradoDelitoDto>> listarTiposGradoDelitos() throws Exception {
        return new ResponseEntity<>(delitoRepository.listarTiposGradoDelitos(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PaginacionWrapperDTO<DelitosDto>> buscarDelitos(BuscarDelitosDto buscarDelitosDto)
            throws Exception {

        PaginacionWrapperDTO<DelitosDto> delitosEncontrados = delitoRepository
                .buscarDelitos(buscarDelitosDto);

        return new ResponseEntity<>(delitosEncontrados, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> registrarDelitos(RegistrarDelitosDetenidosDto registrarDelitosDetenidosDto) throws Exception {
        return new ResponseEntity<>(delitoRepository.registrarDelitos(registrarDelitosDetenidosDto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> registrarDetenidos(RegistrarDelitosDetenidosDto registrarDelitosDetenidosDto) throws Exception {
        return new ResponseEntity<>(delitoRepository.registrarDetenidosDelitos(registrarDelitosDetenidosDto), HttpStatus.OK);
    }
}
