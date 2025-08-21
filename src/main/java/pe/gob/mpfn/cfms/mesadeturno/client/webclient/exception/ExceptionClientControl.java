package pe.gob.mpfn.cfms.mesadeturno.client.webclient.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import pe.gob.mpfn.cfms.generales.libreria.core.exception.UnprocessableEntityException;
import pe.gob.mpfn.cfms.mesadeturno.dto.client.ExceptionClientDto;
import pe.gob.mpfn.cfms.mesadeturno.util.Constantes;

@Slf4j
public class ExceptionClientControl {

    public static void exceptionClientControl( WebClientResponseException ex ) throws Exception {

        ObjectMapper mapper = new ObjectMapper();
        log.info("ererer {}", ex.getResponseBodyAsString());
        HttpStatusCode statusCode = ex.getStatusCode();
log.info("statyus:    {}", statusCode);
        //ExceptionClientDto exceptionClientDTO = mapper.readValue(ex.getResponseBodyAsString(), ExceptionClientDto.class);

        log.error( ex.getResponseBodyAsString() );

        if (statusCode.is4xxClientError()) {
            throw new UnprocessableEntityException( Constantes.UNPROCESASABLE_ENTITY , ex.getMessage());
        } else if (statusCode.is5xxServerError()) {
            throw new Exception( ex.getResponseBodyAsString() );
        }
    }

}
