package pe.gob.mpfn.cfms.mesadeturno.persistence.mapper;

import org.springframework.jdbc.core.RowMapper;
import pe.gob.mpfn.cfms.mesadeturno.dto.DelitoDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DelitoMapper implements RowMapper<DelitoDto> {

    @Override
    public DelitoDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        DelitoDto delito = new DelitoDto();

        delito.setArticulo(rs.getString("NU_V_ARTICULO"));
        delito.setNombre(rs.getString("NO_V_DELITO"));

        return delito;
    }

}