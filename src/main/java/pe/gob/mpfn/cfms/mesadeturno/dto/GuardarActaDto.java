package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GuardarActaDto {

    private String idCaso;
    private String numeroDocumento;
    private String contenidoActa;
    private String nombreArchivoOrigen;
    private String archivo;
    private String nombreDocumento;
    private int idTipoActa;
    private int idTipoCopia;

}