package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class PartePersonaNaturalDto extends ParteInvolucradaDto {
    private String  nombres;
    private String  apellidoPaterno;
    private String  apellidoMaterno;
    private Date    fechaNacimiento;
    private Integer edad;
    private Integer idEstadoCivil;
    private Integer idGradoInstruccion;
    private Integer idTipoActividadLaboral;
    private String  actividadLaboral;
    private String  sexo;
    private Integer idTipoLengua;
    private Integer idTipoNacionalidad;
    private String  nacionalidad;
    private Integer idTipoOrientacion;
    private Integer idTipoPueblo;
    private String  requiereTraductor;
    private String  esFallecido;
    private String  esAfroperuano;
    private String  esTrabajadorDelHogar;
    private String  esVictimaViolencia8020;
    private String  conDiscapacidad;
    private String  estaPrivadoLibertad;
    private String  coinfeccionVihTbc;
    private String  esMigrante;
    private String  esLgtbi;
    private String  esExtranjero;
    private String  estaVerificado;
    private Integer idPais;
    private String  nombreAlias;
    private String  fotoSujeto;
    private Double  pesoFotoSujeto;
    private String  esMenorDeEdad;
    private String  esProcurador;
    private String  condicion;
    private String  nombreMadre;
    private String  apellidoPaternoMadre;
    private String  apellidoMaternoMadre;
    private String  nombrePadre;
    private String  apellidoPaternoPadre;
    private String  apellidoMaternoPadre;
    private String  oficioTexto;

    private String  esFuncionarioPublico;
    private Integer idCargoFuncionarioPublico;
    private Integer idInstitucionPublica;
    private String  esDefensor;
    private Integer idTipoDefensor;
    private String esCuentaAsociacionDefensora;
    private Integer idCargoAsociacion;
    private String fechaValoracion;
    private String fechaDetencion;
    private Integer idTipoCondicionParte;
    private Integer idTipoViolencia;
    private Integer idFactorRiesgo;
    private Integer idTipoDiscapacidad;
    private String observacionesInvestigacion;
}
