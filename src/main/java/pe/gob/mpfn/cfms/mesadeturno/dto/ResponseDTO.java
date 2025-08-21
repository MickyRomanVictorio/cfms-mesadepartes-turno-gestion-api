package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ResponseDTO<T> {
    private Integer codigo;
    private String mensaje;
    private T data;
}
