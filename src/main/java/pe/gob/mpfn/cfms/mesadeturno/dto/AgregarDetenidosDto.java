package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AgregarDetenidosDto {
    private String idSujetoCaso;
    private List<AgregarDelitosDto> delitos;
}