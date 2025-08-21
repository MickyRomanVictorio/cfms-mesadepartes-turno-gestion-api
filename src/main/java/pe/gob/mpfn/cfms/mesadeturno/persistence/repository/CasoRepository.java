package pe.gob.mpfn.cfms.mesadeturno.persistence.repository;

import pe.gob.mpfn.cfms.mesadeturno.bean.Usuario;
import pe.gob.mpfn.cfms.mesadeturno.dto.HechosCasoDto;

public interface CasoRepository {
    String registrarHechosCaso(HechosCasoDto hechosCasoDto, Usuario usuario) throws Exception;
}
