package pe.gob.mpfn.cfms.mesadeturno.dto.informaciongeneral;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DireccionInfoDto {
    private String idDireccion;
    private Integer idTipoDireccion;
    private String desTipoDireccion;
    private Integer idPais;
    private String ubigeo;
    private String idDepartamento;
    private String desDepartamento;
    private String idProvincia;
    private String desProvincia;
    private String idDistrito;
    private String desDistrito;
    private Integer idTipoVia;
    private String desTipoVia;
    private String desResidencia;
    private Integer nroResidencia;
    private Integer tipoUrbanizacion;
    private String desUrbanizacion;
    private String block;
    private String interior;
    private String etapa;
    private String manzana;
    private String lote;
    private String referencia;
    private String latitud;
    private String longitud;
    private String idSujetoCaso;
}
