package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DireccionDto {

    private String  ubigeo;
    private Integer idTipoDomicilio;
    private String  codigoPostal;
    private String  ubigeoPueblo;
    private Integer idTipoVia;
    private String  direccionResidencia;
    private Integer numeroResidencia;
    private Integer idTipoUrbanizacion;
    private String  urbanizacion;
    private String  blockDireccion;
    private String  interior;
    private String  etapa;
    private String  manzana;
    private String  lote;
    private String  referencia;
    private String  latitud;
    private String  longitud;

}
