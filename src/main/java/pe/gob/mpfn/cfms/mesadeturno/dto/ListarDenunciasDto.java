package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ListarDenunciasDto {

    private Integer pagina;
    private Integer limite;
    private String  numeroCaso;
    private Integer dependenciaPolicial;
    private String  fiscal;

}