package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Data;

@Data
public class DatosCorreoDto {
    private String destinatario;
    private String nombreApellido;
    private String fecha;
    private String hora;
    private String nroCaso;
    private String fiscalia;
    private String despachoFiscal;
    private String enlace;
    private String adjunto;
    private String nombreAdjunto;
}
