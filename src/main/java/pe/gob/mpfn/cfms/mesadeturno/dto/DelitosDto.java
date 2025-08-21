package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DelitosDto extends DelitoDto {

    private String nro;
    private String delitoGenerico;
    private String delitoSubgenerico;
    private String delitoEspecifico;
    private String delitoGenericoId;
    private String delitoSubgenericoId;
    private String delitoEspecificoId;

}
