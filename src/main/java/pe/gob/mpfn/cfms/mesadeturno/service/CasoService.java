package pe.gob.mpfn.cfms.mesadeturno.service;

import pe.gob.mpfn.cfms.mesadeturno.bean.Usuario;
import pe.gob.mpfn.cfms.mesadeturno.dto.HechosCasoDto;

public interface CasoService {
    String registrarHechosCaso(HechosCasoDto hechosCasoDto, Usuario usuario) throws Exception;
}
