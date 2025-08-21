package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class HechosDto {

    private String  ubigeo;
    private Integer idTipoLugar;
    private Integer idTipoVia;
    private String  direccion;
    private Integer numeroDireccion;
    private Integer numeroFallecidos;
    private String  latitud;
    private String  longitud;
    private Date    fechaHecho;
    private Integer idTipoHecho;
    private String  descripcion;
    private String  idFiscal;
    private String  nombrefiscal;
    private Date    fechaDetencion;
    private String  encargado;

}