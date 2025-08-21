package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ParteInvolucradaDto {

    private Integer idTipoParteSujeto;
    private Integer idTipoPersona;
    private String  tipoAgraviado;
    private String  esDenunciadoDesconocido;
    private Integer idTipoDocumento;
    private String  numeroDocumento;
    private String  correoPrimario;
    private String  correoSecundario;
    private String  telefonoPrimario;
    private String  telefonoSecundario;
    private List<DireccionDto> direcciones;

}
