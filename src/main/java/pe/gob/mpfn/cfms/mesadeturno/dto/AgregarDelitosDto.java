package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgregarDelitosDto {
    private Integer idDelitoGenerico;
    private Integer idDelitoSubGenerico;
    private Integer idDelitoEspecifico;
    private Integer idTipoGradoDelito;
    private Integer idOrigenTurno;
}
