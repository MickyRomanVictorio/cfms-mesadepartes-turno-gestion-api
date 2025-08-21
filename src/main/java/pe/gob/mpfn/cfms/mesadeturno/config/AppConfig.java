package pe.gob.mpfn.cfms.mesadeturno.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import pe.gob.mpfn.cfms.mesadeturno.common.handler.RestAuthExceptionHandler;

@Configuration
public class AppConfig {

    private final ObjectMapper objectMapper;

    public AppConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {

        Contact contact = new Contact();
        contact.setEmail("dacunac@email.mpfn.gob.pe");
        contact.setName("OGTI");
        contact.setUrl("http://www.mpfn.gob.pe");

        return new OpenAPI()
                .info(new Info()
                        .title("Cfms Mesa de Turno Gestión Api")
                        .description("Servicio que permite el registro de denuncias")
                        .version("1.0")
                        .contact(contact)
                );
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    RestAuthExceptionHandler restAuthExceptionHandler() {
        return new RestAuthExceptionHandler(objectMapper);
    }

}
