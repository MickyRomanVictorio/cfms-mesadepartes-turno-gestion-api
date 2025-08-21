package pe.gob.mpfn.cfms.mesadeturno.util;

public class UbigeoExtranjero {

    public static final String EXTRANJERO = "0000";

    public  static String esExtranjero(String ubigeo) {

        var ubigeo4 = ubigeo.substring(2);
        if (EXTRANJERO.equals(ubigeo4)) {
            return EXTRANJERO;
        }
        return ubigeo;


    }

}
