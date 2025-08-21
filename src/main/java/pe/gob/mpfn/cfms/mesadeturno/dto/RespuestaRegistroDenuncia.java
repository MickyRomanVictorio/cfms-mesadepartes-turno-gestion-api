package pe.gob.mpfn.cfms.mesadeturno.dto;

import java.util.Date;

public record RespuestaRegistroDenuncia(
        String coCaso,
        String idCaso,
        String codigoEntidad,
        String idTipoEntidad,
        Date fechaRegistro
) {
}
