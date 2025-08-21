package pe.gob.mpfn.cfms.mesadeturno.persistence.repository;

import org.springframework.stereotype.Repository;
import pe.gob.mpfn.cfms.mesadeturno.dto.CompletarRegistrarDenunciaDto;

import java.sql.Array;

@Repository
public interface DenunciaDuplicadaRepository {
    Array prepararDatosParticipantesDenunciaDuplicada(CompletarRegistrarDenunciaDto denunciaDto);
    Array prepararDatosDelitos(CompletarRegistrarDenunciaDto denunciaDto);
}
