package pe.gob.mpfn.cfms.mesadeturno.persistence.mapper;

import org.springframework.jdbc.core.RowMapper;
import pe.gob.mpfn.cfms.mesadeturno.dto.ActaDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActaMapper implements RowMapper<ActaDto> {

    @Override
    public ActaDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        ActaDto acta = new ActaDto();

        acta.setIdDocumento(rs.getString("ID_V_DOCUMENTO"));
        acta.setNombreDocumento(rs.getString("CO_V_DOCUMENTO"));
        acta.setExtensionDocumento(rs.getString("NO_V_EXTENSION_ARCHIVO"));
        acta.setTipoDeCopia(rs.getString("NO_V_TIPO_COPIA"));

        return acta;
    }

}
