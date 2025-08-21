package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HechosCasoDto {
    private String ubigeo;
    private String tipoLugar;
    private String tipoVia;
    private String direccion;
    private String numeroDireccion;
    private String latitud;
    private String longitud;
    private Date fechaHecho;
    private String horaHecho;
    private String formatoHecho;
    private String tipoHecho;
    private String descripcion;
    private String fiscal;
    private Date fechaDetencion;
    private String horaDetencion;
    private String formatoDetencion;
    private Integer cantidadFallecidos;
    private String encargado;
}
