package pe.gob.mpfn.cfms.mesadeturno.service.impl;

import org.springframework.stereotype.Service;
import pe.gob.mpfn.cfms.mesadeturno.client.webclient.FirmaDigitalClient;
import pe.gob.mpfn.cfms.mesadeturno.service.FirmaDigitalService;

@Service
public class FirmaDigitalServiceImpl implements FirmaDigitalService {

    private final FirmaDigitalClient firmaDigitalClient;

    public FirmaDigitalServiceImpl(FirmaDigitalClient firmaDigitalClient) {
        this.firmaDigitalClient = firmaDigitalClient;
    }

    @Override
    public String obtenerPdfFirmado(String pdfStr) {
        return firmaDigitalClient.obtenerPdfFirmado( pdfStr );
    }

    @Override
    public String generarCodigoVerificacion() {
        return firmaDigitalClient.generarCodigoVerificacion();
    }
}
