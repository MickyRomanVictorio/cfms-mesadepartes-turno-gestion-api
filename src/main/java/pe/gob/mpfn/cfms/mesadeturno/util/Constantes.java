package pe.gob.mpfn.cfms.mesadeturno.util;

public class Constantes {
    private Constantes(){}
    public static final String CORREO_SUBJECT = "MESA DE PARTES MINISTERIO PÚBLICO - FISCALÍA DE LA NACIÓN";
    public static final String NO_TIPO_DOCUMENTO_CARGO_INGRESO_DENUNCIA = "CARGO DE INGRESO DE DENUNCIA";
    public static final String C_42202001_BODY_REQUERIDO = "42202001";
    public  static final String EXTENSION_PDF = ".pdf";
    public  static final long ID_OAID_CARGO_DENUNCIA = 1L;
    public static final String FLAG_AMBITO_INTERNO = "0";
    public static final Long CLASIFICACION_DOCUMENTO_CARGO = 1220L;
    public static final String TIPO_DOCUMENTO_CARGO_INGRESO_DENUNCIA = "08";
    public static final Long ID_TIPO_ORIGEN = 4L;
    public  static final String PROCESO_CARGO_FIRMA = "001";
    public  static final String PROCESO_ANEXO = "002";
    public  static final String PROCESO_CORREO = "003";
    public  static final String PROCESO_DOCUMENTO = "004";
    public static String CORREO_BODY = "Estimado(a) Sr(a). \n\n"
            + "NOMBRE_USUARIO \n\n"
            + "Su denuncia ha sido registrada satisfactoriamente en fecha dd/mm/aaaa a las \n\n"
            + "HH:mm hrs., generándose el Caso N° NUMERO_CASO, el cual ha sido \n\n"
            + "asignado a la FISCALIA - DESPACHO. \n\n"
            + "Asimismo, se adjunta al presente el Cargo de Ingreso de Denuncia. \n\n"
            + "Para hacer seguimiento del caso, ingrese al siguiente enlace: ENLACE. \n\n"
            + "Gracias por utilizar nuestra Mesa de Partes Electrónica. \n\n"
            + "Atentamente,\n\n"
            + "Ministerio Público – Fiscalía de la Nación \n"
            + "";

    public static final String ENLACE = "<a href=\"" + "http://cfng-mesadepartes-electronica-qa.apps.ocp4.cfe.mpfn.gob.pe/seguir-denuncia/verificacion" + "\">AQUI</a>";


//    public static String CORREO_BODY = "Estimado(a) Sr(a). <br><br>"
//            + "<b>NOMBRE_USUARIO</b> <br><br>"
//            + "Su denuncia ha sido registrada satisfactoriamente en fecha dd/mm/aaaa a las <br><br>"
//            + "HH:mm hrs., generándose el Caso N° <b>NUMERO_CASO</b>, el cual ha sido <br><br>"
//            + "asignado a la FISCALIA - DESPACHO. <br><br>"
//            + "Asimismo, se adjunta al presente el Cargo de Ingreso de Denuncia. <br><br>"
//            + "Para hacer seguimiento del caso, ingrese al siguiente enlace: <a href='ENLACE'>ENLACE</a>. <br><br>"
//            + "Gracias por utilizar nuestra Mesa de Partes Electrónica. <br><br>"
//            + "Atentamente,<br><br>"
//            + "<b>Ministerio Público – Fiscalía de la Nación</b> <br>";
    
//    public static String CORREO_BODY = "Estimado(a) Sr(a). \n\n"
//            + "<b>NOMBRE_USUARIO</b> \n\n"
//            + "Su denuncia ha sido registrada satisfactoriamente en fecha dd/mm/aaaa a las \n\n"
//            + "HH:mm hrs., generándose el Caso N° <b>NUMERO_CASO</b>, el cual ha sido \n"
//            + "asignado a la FISCALIA - DESPACHO. \n\n"
//            + "Asimismo, se adjunta al presente el Cargo de Ingreso de Denuncia. \n\n"
//            + "Para hacer seguimiento del caso, ingrese al siguiente enlace: ENLACE. \n\n"
//            + "Gracias por utilizar nuestra Mesa de Partes Electrónica. \n\n"
//            + "Atentamente,\n\n"
//            + "<b>Ministerio Público – Fiscalía de la Nación</b> \n"
//            + "";
    
    public static final int REPOSITORIO_TIPO_ORIGEN = 4;  /* TURNO */
    public static final int REPOSITORIO_EXTENSION_ARCHIVO = 438;  /* DOCUMENTO PDF */
    public static final int REPOSITORIO_OAID = 2;
    public static final int REPOSITORIO_OAID_CARGO_INGRESO_DENUNCIA = 1;
    public static final String REPOSITORIO_TIPO_DOCUMENTO_ACTA = "05";
    public static final String REPOSITORIO_TIPO_DOCUMENTO_CARGO_INGRESO_DENUNCIA = "08";
    public static final int TIPO_DENUNCIA_INVESTIGACION_OFICIO = 363;
    public static final String UNPROCESASABLE_ENTITY = "422";
    public  static final long ID_OAID_DOCUMENTO_PRINCIPAL = 1L;
    public static final String TYPE_PARTICIPANTES_DENUNCIA_DUPLICADA= "SISMPA.MPPK_REGISTRA_DENUNCIA_TURNO.TYPE_PARTICIPANTES_DENUNCIA_DUPLICADA";
    public static final String PARTICIPANTES_DENUNCIA_DUPLICADA = "SISMPA.MPPK_REGISTRA_DENUNCIA_TURNO.PARTICIPANTES_DENUNCIA_DUPLICADA";
    public static final String TYPE_DELITO = "SISMPA.MPPK_REGISTRA_DENUNCIA_TURNO.TYPE_DELITO";
    public static final String DELITO = "SISMPA.MPPK_REGISTRA_DENUNCIA_TURNO.DELITO";
}
