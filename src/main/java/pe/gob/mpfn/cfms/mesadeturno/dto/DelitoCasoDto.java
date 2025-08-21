package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DelitoCasoDto {

    private String  numero;
    private String  articulo;
    private Integer idDelitoGenerico;
    private String  delitoGenerico;
    private Integer idDelitoSubgenerico;
    private String  delitoSubgenerico;
    private Integer idDelitoEspecifico;
    private String  delitoEspecifico;
    private Integer idOrigen;
    private String  origen;
    private Integer idGrado;
    private String  grado;

}