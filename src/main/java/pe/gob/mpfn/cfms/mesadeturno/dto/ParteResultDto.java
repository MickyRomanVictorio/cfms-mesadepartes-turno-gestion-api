package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Data;

@Data
public class ParteResultDto {
    private String nombreCompleto;
    private String tipoDocumento;
    private String numeroDocumento;
    private String idTipoParte;
    private String tipoParte;
    private String idCondicion;
    private String condicion;
}
