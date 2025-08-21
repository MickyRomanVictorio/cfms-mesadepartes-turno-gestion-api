package pe.gob.mpfn.cfms.mesadeturno.client.almacenamiento;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import pe.gob.mpfn.cfms.mesadeturno.util.ByteArrayMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GeneralesAlmacenamientoClientTest {

    @Autowired private GeneralesAlmacenamientoClient generalesAlmacenamientoClient;

    @Test
    void guardarDocumento() throws IOException {

        String file = "C:\\Users\\FN\\Downloads\\CARTA S_N.pdf";

        MultipartFile multipartFile = new ByteArrayMultipartFile(Files.readAllBytes(new File(file).toPath()), "SSS.pdf", MediaType.APPLICATION_PDF_VALUE);

        var response = generalesAlmacenamientoClient.guardarDocumento(multipartFile);
        Assertions.assertThat(response).isNotNull();

    }
}