package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Data;

@Data
public class DatosReniecDto {
    private String foto;
    private String flagOrigen;
    private String idTipoDocIdent;
    private String desTipoDocIdent;
    private String numeroDocumento;
    private String flagVerificado;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombres;
    private String fechaNacimiento;
    private long edad;
    private String codigoGenero;
    private String alias;
    private Integer tipoEstadoCivil;
    private String codigoGradoInstruccion;
    private Integer actLaboral;
    private String otraActvLaboral;
    private String nombreMadre;
    private String estCivMadre;
    private String nombrePadre;
    private String estCivPadre;
    private String correoElecPrincipal;
    private String correoElecSecundario;
    private String telefonoPrincipal;
    private String telefonoSecundario;
    private Integer puebloIndigena;
    private Integer lenguaMaterna;
    private String flagAfroperuano;
    private String flagConDiscapacidad;
    private String flagPrivadaLibertad;
    private String flagVIHTBC;
    private String flagTrabHogar;
    private String flagLGTBI;
    private String flagDefensor;
    private String flagMigrante;
    private String flagVictimViole;
    private String flagFuncPublico;
}
