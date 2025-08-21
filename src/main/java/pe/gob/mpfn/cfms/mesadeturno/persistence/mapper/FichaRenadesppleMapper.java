package pe.gob.mpfn.cfms.mesadeturno.persistence.mapper;

import org.springframework.jdbc.core.RowMapper;
import pe.gob.mpfn.cfms.mesadeturno.dto.FichaRenadesppleDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FichaRenadesppleMapper implements RowMapper<FichaRenadesppleDto> {
    @Override
    public FichaRenadesppleDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        FichaRenadesppleDto fichaRenadesppleDto = new FichaRenadesppleDto();
        fichaRenadesppleDto.setIdCaso(rs.getString("ID_V_CASO"));
        fichaRenadesppleDto.setIdSujetoCaso(rs.getString("ID_V_SUJETO_CASO"));
        fichaRenadesppleDto.setIdOficina(rs.getInt("ID_N_OFICINA"));
        fichaRenadesppleDto.setIdFicha(rs.getString("ID_V_FICHA_RENADESPPLE"));
        fichaRenadesppleDto.setFlagPorcentaje(rs.getString("FL_C_AVANCE"));
        return fichaRenadesppleDto;
    }
}
