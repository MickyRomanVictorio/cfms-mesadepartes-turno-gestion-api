package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class DenunciaRegistradaDto {

    private String  idCaso;
    private String  numeroCaso;
    private String  fechaDetencionInicio;
    private String  fechaDetencionFin;
    private String  estadoRegistro;
    private Integer codigoTipoDenuncia;
    private String  tipoDenuncia;
    private String  fechaDenuncia;
    private String  horaDenuncia;
    private String  dependenciaPolicial;
    private String  nombreDenunciante;
    private String  celularDenunciante;
    private String  fiscalAsignado;
    private Integer numeroActas;
    private String  fichaRenadespple;
    private Integer numeroSujetosPersonaNatural;
    private Integer numeroSujetosPersonaJuridica;
}
