package pe.gob.mpfn.cfms.mesadeturno.client.almacenamiento;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Component
@Slf4j
public class GeneralesAlmacenamientoClient {

    private final WebClient webClient;

    @Value("${clients.generalesAlmacenamiento}")
    private String generalesAlmacenamiento;

    public GeneralesAlmacenamientoClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public RepositorioResponseDTO guardarDocumento(MultipartFile documento ) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", documento.getResource());

        String url = "%s/cargar".formatted(generalesAlmacenamiento);
        ResponseEntity<RepositorioResponseDTO> responseClient = webClient
                    .post()
                    .uri( url )
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .body(BodyInserters.fromMultipartData(builder.build()))
                    .retrieve()
                    .toEntity( RepositorioResponseDTO.class )
                    .timeout(Duration.ofMinutes(1))
                    .block();

        return responseClient.getBody();
    }

    public void guardarDocumentoRepositorio( String nodeId, MultipartFile documento ) throws Exception {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", documento.getResource());

        String url =  "%s/cargar?nodeId=%s".formatted(generalesAlmacenamiento, nodeId);
        webClient
                .post()
                .uri( url )
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .toEntity( String.class )
                .timeout(Duration.ofMinutes(1))
                .block();
    }

}
