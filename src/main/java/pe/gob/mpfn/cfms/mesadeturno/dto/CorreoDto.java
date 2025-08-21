package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CorreoDto {

    private String asunto;
    private String destinatario;
    private String conCopia;
    private String cuerpo;
    private String adjunto;
    private String nombreAdjunto;

}

