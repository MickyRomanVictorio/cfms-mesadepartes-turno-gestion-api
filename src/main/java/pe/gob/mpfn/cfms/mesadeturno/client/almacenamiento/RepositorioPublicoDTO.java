package pe.gob.mpfn.cfms.mesadeturno.client.almacenamiento;

import lombok.Data;

@Data
public class RepositorioPublicoDTO {
    private String nodeId;
    private String filename;
    private String contentType;
    private String extension;
    private Long numeroPaginas;
    private Double pesoArchivosMb;
}
