package pe.gob.mpfn.cfms.mesadeturno.common.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pe.gob.mpfn.cfms.mesadeturno.bean.JwtToken;
import pe.gob.mpfn.cfms.mesadeturno.bean.Usuario;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class JwtTokenUtility implements Serializable {
    private static final long serialVersionUID = -2550185165626007488L;
    public String token;
    public JwtToken payload;

    public String getNombreUsuario() {
        return this.payload.getUsuario().getInfo().getApellidoPaterno() + " "
                + this.payload.getUsuario().getInfo().getApellidoMaterno() + ", "
                + this.payload.getUsuario().getInfo().getNombres();
    }

    public Usuario getUsuario() {
        var user = payload.getUsuario();
        return user;
    }
}
