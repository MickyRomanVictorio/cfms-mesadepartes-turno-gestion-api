package pe.gob.mpfn.cfms.mesadeturno.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DatosLlamadaDto {

    private Integer idTipoDocumentoIdentidad;
    private String  numeroDocumentoIdentidad;
    private boolean registrarDatosManual;
    private String  nombres;
    private String  apellidoPaterno;
    private String  apellidoMaterno;
    private String  correo;
    private String  numeroContacto;
    private Integer idDependenciaPolicial;
    private String  nombreDependenciaPolicial;
    private Date    fechaLlamada;
    private Integer  tipoDenuncia;
    private String  idDenuncia;
    private String  idCaso;
    private String  numeroCaso;
    private String  sexo;
    private Integer idDistritoFiscal;
    private String  nombreDistritoFiscal;
    private Integer idEspecialidad;
    private String  nombreEspecialidad;
    private String  idFiscalia;
    private String  nombreFiscalia;
    private String  idDespacho;
    private String  nombreDespacho;

}