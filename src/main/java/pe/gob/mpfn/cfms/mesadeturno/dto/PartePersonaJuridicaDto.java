package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PartePersonaJuridicaDto extends ParteInvolucradaDto{

    private String  razonSocial;
    private String  actividadEmpresarial;
    private String  nombreRepresentanteLegal;
    private String  apellidoPaternoRepresentanteLegal;
    private String  apellidoMaternoRepresentanteLegal;
    private Integer idTipoDocumentoRepresentanteLegal;
    private String  numeroDocumentoRepresentanteLegal;

}
