package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegistrarDelitosDetenidosDto {
    private String idDenuncia;
    private List<AgregarDelitosDto> delitos;
    private List<AgregarDetenidosDto> detenidos;
}
