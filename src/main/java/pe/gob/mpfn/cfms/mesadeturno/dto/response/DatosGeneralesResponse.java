package pe.gob.mpfn.cfms.mesadeturno.dto.response;

import java.util.Date;

public record DatosGeneralesResponse(
        String idDenuncia,
        String idCaso,
        String numeroCaso,
        String idDistritoFiscal,
        String nombreDistritoFiscal,
        String idEspecialidad,
        String nombreEspecialidad,
        String idFiscalia,
        String nombreFiscalia,
        String idDespacho,
        String nombreDespacho,
        String fechaRegistroCaso
) {
}
