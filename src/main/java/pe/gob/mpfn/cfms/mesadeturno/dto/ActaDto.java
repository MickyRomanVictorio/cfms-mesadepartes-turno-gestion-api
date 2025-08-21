package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ActaDto {

    private String idDocumento;
    private String nombreDocumento;
    private String extensionDocumento;
    private String tipoDeCopia;

}
