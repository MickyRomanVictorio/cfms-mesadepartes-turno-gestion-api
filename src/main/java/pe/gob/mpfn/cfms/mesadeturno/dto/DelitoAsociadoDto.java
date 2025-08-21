package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class DelitoAsociadoDto {

    private DetenidoDto detenido;
    private List<DelitoCasoDto> delitos;

}
