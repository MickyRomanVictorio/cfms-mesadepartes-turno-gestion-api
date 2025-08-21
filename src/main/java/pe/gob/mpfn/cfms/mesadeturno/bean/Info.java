package pe.gob.mpfn.cfms.mesadeturno.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Info {
    private String apellidoPaterno;
    private String esPrimerLogin;
    private String dni;
    private String nombres;
    private String apellidoMaterno;
}