package pe.gob.mpfn.cfms.mesadeturno.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Usuario {

    public static final String JWT_CLAIM_NAME = "usuario";

    private String estado;
    private String ip;
    private String usuario;
    private Info info;
    private String codDependencia;
    private String dependencia;
    private String codDespacho;
    private String sede;
    private String despacho;
    private String codCargo;
    private String codSede;
    private String cargo;
    private String codDistritoFiscal;
    private String distritoFiscal;
    private String dniFiscal;
    private String direccion;
    private String fiscal;
    private String correoFiscal;
    private String codJerarquia;
    private String codCategoria;
    private String codEspecialidad;
    private String ubigeo;
    private String distrito;
    private String correo;
    private String telefono;
}