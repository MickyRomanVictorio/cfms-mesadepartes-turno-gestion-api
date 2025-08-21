package pe.gob.mpfn.cfms.mesadeturno.service.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pe.gob.mpfn.cfms.mesadeturno.client.webclient.mensajevalidacion.MensajeValidacionClient;
import pe.gob.mpfn.cfms.mesadeturno.service.MensajeValidacionService;


@Service
public class MensajeValidacionServiceImpl implements MensajeValidacionService {

    private final MensajeValidacionClient mensajeValidacionClient;

    public MensajeValidacionServiceImpl(MensajeValidacionClient mensajeValidacionClient) {
        this.mensajeValidacionClient = mensajeValidacionClient;
    }
    @Cacheable(value = "mensaje", key = "#codigo")
    public String obtenerMensajeValidacion(String codigo) {
        return mensajeValidacionClient.obtenerMensajeValidacion(codigo);
    }
}
