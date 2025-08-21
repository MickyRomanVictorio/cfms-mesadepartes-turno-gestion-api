package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class DelitosCasoDto {
    private List<DelitoCasoDto> delitosAgregados;
    private List<DelitoAsociadoDto> delitosAsociados;
}
