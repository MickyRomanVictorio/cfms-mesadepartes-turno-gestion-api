package pe.gob.mpfn.cfms.mesadeturno.enums;

import lombok.Getter;

@Getter
public enum TipoSujeto {

    PNP("2"),
    CIUDADANO("1"),
    MINISTERIO_PUBLICO("21")
    ;

    private final String value;

    TipoSujeto(String value) {
        this.value = value;
    }
}
