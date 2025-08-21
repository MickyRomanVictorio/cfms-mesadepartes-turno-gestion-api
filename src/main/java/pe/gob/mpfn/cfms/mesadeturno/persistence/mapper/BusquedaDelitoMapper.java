package pe.gob.mpfn.cfms.mesadeturno.persistence.mapper;

import org.springframework.jdbc.core.RowMapper;
import pe.gob.mpfn.cfms.mesadeturno.dto.DelitosDto;

import java.sql.ResultSet;
import java.sql.SQLException;


public class BusquedaDelitoMapper implements RowMapper<DelitosDto> {

    @Override
    public DelitosDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        DelitosDto delitosDto = new DelitosDto();
        delitosDto.setNro(rs.getString("ID"));
        delitosDto.setArticulo(rs.getString("ARTICULO"));
        delitosDto.setDelitoGenerico(rs.getString("DELITO_GENERICO"));
        delitosDto.setDelitoSubgenerico(rs.getString("DELITO_SUBGENERICO"));
        delitosDto.setDelitoEspecifico(rs.getString("DELITO_ESPECIFICO"));
        delitosDto.setDelitoGenericoId(rs.getString("DELITO_GENERICO_ID"));
        delitosDto.setDelitoSubgenericoId(rs.getString("DELITO_SUBGENERICO_ID"));
        delitosDto.setDelitoEspecificoId(rs.getString("DELITO_ESPECIFICO_ID"));

        return delitosDto;
    }
}
