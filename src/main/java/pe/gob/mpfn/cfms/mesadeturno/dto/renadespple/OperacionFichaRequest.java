package pe.gob.mpfn.cfms.mesadeturno.dto.renadespple;

import lombok.Data;

@Data
public class OperacionFichaRequest {
    private Integer numeroStep;
    private Integer operacion; // 0-Registrar, 1-Actualizar
    private String flagAvance;
    private String idFicha;
    private String idCaso;
    private String idSujetoCaso;
    private String idOficina;
    private String flagTentativa;
    private Integer idMotivoDetencion;
    private String informePolicial;
    private String idSituacionJuridica;
    private String fechaEmisionDocumento;
    private String fechaLibertad;
    private String alias;
    // Datos adicionales
    private String flagAfroperuana;
    private String flagDiscapacidad;
    private String flagPrivLibertad;
    private String flagVIHTBC;
    private String flagTrabHogar;
    private String flagLGTBIQ;
    private String flagDefensor;
    private String flagMigrante;
    private String flagVictimaViolencia;
    private String flagServPublico;
    private Integer tipoPueblo;
    private Integer tipoLengua;
}
