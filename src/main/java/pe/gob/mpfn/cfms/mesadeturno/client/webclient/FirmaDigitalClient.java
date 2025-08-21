package pe.gob.mpfn.cfms.mesadeturno.client.webclient;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import pe.gob.mpfn.cfms.generales.libreria.core.exception.NoControladoException;
import pe.gob.mpfn.cfms.mesadeturno.dto.client.FirmaDigitalDto;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Objects;

@Component
@Slf4j

public class FirmaDigitalClient {

    private final WebClient webClient;
    private final String generalesFirmaDigitalUrl;

    public FirmaDigitalClient(
            WebClient webClient,
            @Value("${clients.generalesFirmaDigitalUrl}") String generalesFirmaDigitalUrl) {
        this.webClient = webClient;
        this.generalesFirmaDigitalUrl = generalesFirmaDigitalUrl;
    }

    public String obtenerPdfFirmado(String pdfStr) {

        log.info("generalesFirmaDigitalUrl ===> " + generalesFirmaDigitalUrl);

        FirmaDigitalDto firmaDigitalDTO = new FirmaDigitalDto();
        firmaDigitalDTO.setDocumento(pdfStr);

        ResponseEntity<String> responseAgente = webClient
                .post()
                .uri(generalesFirmaDigitalUrl + "/agente/b64")
                .body(BodyInserters.fromValue(firmaDigitalDTO))
                .retrieve()
                .toEntity(String.class)
                .block();

        return Objects.requireNonNull(responseAgente).getBody();
    }

    public String generarCodigoVerificacion() {

        log.info("generarCodigoVerificacion ===> " + generalesFirmaDigitalUrl);

        ResponseEntity<String> responseClient = webClient
                .get()
                .uri(generalesFirmaDigitalUrl + "/codigoverificacion")
                .retrieve()
                .toEntity(String.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)))
                .blockOptional().orElseThrow(() -> new NoControladoException("Error en llamada al servicio de codigoverificacion"));

        return Objects.requireNonNull(responseClient).getBody();

    }

}
