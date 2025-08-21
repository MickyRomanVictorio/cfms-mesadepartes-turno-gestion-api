package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Data;

@Data
public class MomentoDetencionDto {

    private String idDenuncia;
    private String idCaso;
    private String ubigeo;
    private String fechaDetencion;
    private String horaDetencion;
    private Integer idDependenciaPolicial;
    private String nombreDependenciaPolicial;
    private Integer idMotivoDetencion;
    private Integer flgTentativa;
    private String departamento;
    private String provincia;
    private String distrito;

    public MomentoDetencionDto() {
    }

    public MomentoDetencionDto(String idCaso) {
        this.idCaso = idCaso;
        this.idDependenciaPolicial = 0;
        this.idMotivoDetencion = 0;
        this.flgTentativa = 0;
    }
}