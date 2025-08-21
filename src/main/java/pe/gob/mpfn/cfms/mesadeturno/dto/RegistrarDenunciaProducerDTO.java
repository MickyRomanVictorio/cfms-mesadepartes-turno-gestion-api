package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pe.gob.mpfn.cfms.mesadeturno.dto.kafka.TramaDenuncia;

@Getter
@Setter
@ToString
public class RegistrarDenunciaProducerDTO {

    private TramaDenuncia denuncia;
    private CorreoDto correo;
    private String[] procesos;

}
