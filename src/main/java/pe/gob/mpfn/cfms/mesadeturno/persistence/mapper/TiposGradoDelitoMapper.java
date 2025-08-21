package pe.gob.mpfn.cfms.mesadeturno.persistence.mapper;

import org.springframework.jdbc.core.RowMapper;
import pe.gob.mpfn.cfms.mesadeturno.dto.TiposGradoDelitoDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TiposGradoDelitoMapper implements RowMapper<TiposGradoDelitoDto> {
    @Override
    public TiposGradoDelitoDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        TiposGradoDelitoDto tiposGradoDelitoDto = new TiposGradoDelitoDto();
        tiposGradoDelitoDto.setId(rs.getString("ID"));
        tiposGradoDelitoDto.setValor(rs.getString("VALOR"));

        return tiposGradoDelitoDto;
    }
}
