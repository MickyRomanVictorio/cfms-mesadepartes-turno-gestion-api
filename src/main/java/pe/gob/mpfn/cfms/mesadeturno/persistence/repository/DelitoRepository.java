package pe.gob.mpfn.cfms.mesadeturno.persistence.repository;

import pe.gob.mpfn.cfms.generales.libreria.core.dto.PaginacionWrapperDTO;
import pe.gob.mpfn.cfms.mesadeturno.dto.BuscarDelitosDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.DelitosDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.RegistrarDelitosDetenidosDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.TiposGradoDelitoDto;

import java.util.List;

public interface DelitoRepository {

    PaginacionWrapperDTO<DelitosDto> buscarDelitos(BuscarDelitosDto buscarDelitosDto) throws Exception;

    List<TiposGradoDelitoDto> listarTiposGradoDelitos() throws Exception;

    String registrarDelitos(RegistrarDelitosDetenidosDto registrarDelitosDetenidosDto) throws Exception;

    String registrarDetenidosDelitos(RegistrarDelitosDetenidosDto registrarDelitosDetenidosDto) throws Exception;
}
