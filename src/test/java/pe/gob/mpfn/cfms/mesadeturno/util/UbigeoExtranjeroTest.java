package pe.gob.mpfn.cfms.mesadeturno.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class UbigeoExtranjeroTest {

    @Test
    void esExtranjero() {

        var result = UbigeoExtranjero.esExtranjero("340000");

        Assertions.assertThat(result).hasSize(4);


    }

    @Test
    void noEsExtranjero() {
        var result = UbigeoExtranjero.esExtranjero("344567");

        Assertions.assertThat(result).hasSize(6);
    }
}