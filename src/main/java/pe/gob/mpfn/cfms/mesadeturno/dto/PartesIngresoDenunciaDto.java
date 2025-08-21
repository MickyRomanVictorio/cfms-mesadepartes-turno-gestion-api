package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PartesIngresoDenunciaDto {

    private String nombreCompleto;
    private String tipoDocumento;
    private String numeroDocumento;
    private String tipoParte;
    private String condicion;

}