package pe.gob.mpfn.cfms.mesadeturno.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class JwtToken {
    private String sub;
    private String iss;
    private String ip;
    private Usuario usuario;
}