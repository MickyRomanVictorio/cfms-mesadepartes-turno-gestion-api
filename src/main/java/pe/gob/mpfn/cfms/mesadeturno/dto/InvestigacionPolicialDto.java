package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestigacionPolicialDto {

    private String informePolicial;
    private String fechaEmision;
    private Integer situacionJuridica;
    private String fechaLibertad;

}
