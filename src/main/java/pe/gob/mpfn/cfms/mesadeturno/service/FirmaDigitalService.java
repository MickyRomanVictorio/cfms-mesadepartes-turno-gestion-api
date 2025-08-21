package pe.gob.mpfn.cfms.mesadeturno.service;

public interface FirmaDigitalService {
    String obtenerPdfFirmado(String pdfStr);
    String generarCodigoVerificacion();
}
