package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class DocumentoDto {
    private int code;
    private String message;
    private List<ArchivoDto> data;
}
