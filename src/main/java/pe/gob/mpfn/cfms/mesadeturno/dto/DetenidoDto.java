package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DetenidoDto {

    private String  nombreCompleto;
    private Integer edad;
    private String  idTipoDocumento;
    private String  tipoDocumento;
    private String  numeroDocumento;
    private String  nacionalidad;
    private Integer idTipoParteSujeto;

}