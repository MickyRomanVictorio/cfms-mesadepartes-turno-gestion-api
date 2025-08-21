package pe.gob.mpfn.cfms.mesadeturno.client.almacenamiento;

import lombok.Data;

@Data
public class RepositorioResponseDTO {
    private Integer codigo;
    private RepositorioPublicoDTO data;
    private String mensaje;

}
