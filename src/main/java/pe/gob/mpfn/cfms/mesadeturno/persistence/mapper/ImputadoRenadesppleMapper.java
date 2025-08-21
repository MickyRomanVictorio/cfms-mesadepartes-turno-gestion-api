package pe.gob.mpfn.cfms.mesadeturno.persistence.mapper;

import org.springframework.jdbc.core.RowMapper;
import pe.gob.mpfn.cfms.mesadeturno.dto.ImputadoRenadesppleDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ImputadoRenadesppleMapper implements RowMapper<ImputadoRenadesppleDto> {

    @Override
    public ImputadoRenadesppleDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        ImputadoRenadesppleDto imputadoRenadespple = new ImputadoRenadesppleDto();

        imputadoRenadespple.setIdSujetoCaso(rs.getString("ID_V_SUJETO_CASO"));
        imputadoRenadespple.setTipoParte(rs.getString("NO_V_TIPO_PARTE_SUJETO"));
        imputadoRenadespple.setCondicion(rs.getString("NO_N_TIPO_COND_SUJETO"));
        imputadoRenadespple.setTipoDocumento(rs.getString("NO_V_TIPO_DOC_IDENT"));
        imputadoRenadespple.setNumeroDocumento(rs.getString("NU_V_DOCUMENTO"));
        imputadoRenadespple.setNombreCompleto(rs.getString("NO_V_IMPUTADO"));
        imputadoRenadespple.setNacionalidad(rs.getString("NO_V_NACIONALIDAD"));
        imputadoRenadespple.setPorcentajeFichaRenadespple(rs.getString("FL_C_AVANCE"));

        return imputadoRenadespple;
    }

}
