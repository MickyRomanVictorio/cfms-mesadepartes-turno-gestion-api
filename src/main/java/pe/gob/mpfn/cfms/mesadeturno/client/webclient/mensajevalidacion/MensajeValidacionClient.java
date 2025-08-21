package pe.gob.mpfn.cfms.mesadeturno.client.webclient.mensajevalidacion;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
@Slf4j
public class MensajeValidacionClient {

    private final WebClient webClient;
    private final String mensajeValidacionUrl;

    public MensajeValidacionClient(
            WebClient webClient,
            @Value("${clients.mensajeValidacionUrl}") String mensajeValidacionUrl) {
        this.webClient = webClient;
        this.mensajeValidacionUrl = mensajeValidacionUrl;
    }

    public String obtenerMensajeValidacion(String codigo) {

        return webClient
                .get()
                .uri( mensajeValidacionUrl , codigo)
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)))
                .block();
    }

}