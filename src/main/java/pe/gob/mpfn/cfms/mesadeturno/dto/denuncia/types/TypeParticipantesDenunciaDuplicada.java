package pe.gob.mpfn.cfms.mesadeturno.dto.denuncia.types;

import lombok.Data;
@Data
public class TypeParticipantesDenunciaDuplicada {
    private Long    idTipoDocIdent;
    private String  nuDocumento;
    private Long    idTtipoParteSujeto;
    private Long    idDelito;
    private Long    idDelitoSubgenerico;
    private Long    idDelitoEspecifico;

}
