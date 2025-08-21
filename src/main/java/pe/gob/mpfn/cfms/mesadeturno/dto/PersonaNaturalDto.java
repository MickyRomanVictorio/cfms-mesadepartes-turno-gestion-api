package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaNaturalDto {

    private String tipoParte;
    private String tipoDocumento;
    private String numeroDocumento;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private String sexo;
    private Integer edad;
    private String correo;
    private String celular;

}