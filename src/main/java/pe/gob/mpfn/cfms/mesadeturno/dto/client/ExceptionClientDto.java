package pe.gob.mpfn.cfms.mesadeturno.dto.client;

import lombok.Data;

@Data
public class ExceptionClientDto {
    public String timestamp;
    public String code;
    public String message;
}
