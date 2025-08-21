package pe.gob.mpfn.cfms.mesadeturno.enums;

import lombok.Getter;

@Getter
public enum TipoDenuncia {

    DENUNCIA_VERBAL(362),
    INVESTIGRACION_DE_OFICIO(363)
    ;

    private final int value;

    TipoDenuncia(int value) {
        this.value = value;
    }
}
