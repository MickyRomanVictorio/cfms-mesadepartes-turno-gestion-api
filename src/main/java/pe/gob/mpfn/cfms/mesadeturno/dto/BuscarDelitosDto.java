package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Getter;

@Getter
public class BuscarDelitosDto {
    private Integer pagina;
    private Integer limite;
    private String texto;
}
