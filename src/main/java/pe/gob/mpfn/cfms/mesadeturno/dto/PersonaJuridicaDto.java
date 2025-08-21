package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonaJuridicaDto {

    private String tipoParte;
    private String tipoDocumento;
    private String numeroDocumento;
    private String razonSocial;
    private String correo;
    private String celular;

}
