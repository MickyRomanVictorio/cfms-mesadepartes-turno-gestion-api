package pe.gob.mpfn.cfms.mesadeturno.dto;

import java.util.Date;

public record DatosGeneralesDto(
        String tipoDocumentoIdentidad,
        String numeroDocumentoIdentidad,
        String nombres,
        String apellidoPaterno,
        String apellidoMaterno,
        String correo,
        String nroContacto,
        String idDependenciaPolicial,
        Date fechaLlamada,
        int tipoDenuncia
) {
}
