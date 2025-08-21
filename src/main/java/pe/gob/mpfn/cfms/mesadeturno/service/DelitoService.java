package pe.gob.mpfn.cfms.mesadeturno.service;

import org.springframework.http.ResponseEntity;
import pe.gob.mpfn.cfms.generales.libreria.core.dto.PaginacionWrapperDTO;
import pe.gob.mpfn.cfms.mesadeturno.dto.BuscarDelitosDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.DelitosDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.RegistrarDelitosDetenidosDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.TiposGradoDelitoDto;

import java.util.List;

public interface DelitoService {

    ResponseEntity<List<TiposGradoDelitoDto>> listarTiposGradoDelitos() throws Exception;

    ResponseEntity<PaginacionWrapperDTO<DelitosDto>> buscarDelitos(BuscarDelitosDto buscarDelitosDto)
            throws Exception;

    ResponseEntity<String> registrarDelitos(RegistrarDelitosDetenidosDto registrarDelitosDetenidosDto) throws Exception;

    ResponseEntity<String> registrarDetenidos(RegistrarDelitosDetenidosDto registrarDelitosDetenidosDto) throws Exception;
}
