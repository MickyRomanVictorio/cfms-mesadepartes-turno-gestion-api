package pe.gob.mpfn.cfms.mesadeturno.persistence.mapper;

import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.RowMapper;
import pe.gob.mpfn.cfms.mesadeturno.dto.DatosGeneralesOficioDto;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter @Setter
public class DatosGeneralesOficioMapper implements RowMapper<DatosGeneralesOficioDto> {

    @Override
    public DatosGeneralesOficioDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        DatosGeneralesOficioDto datosGeneralesOficio = new DatosGeneralesOficioDto();

        datosGeneralesOficio.setNumeroCaso(rs.getString("CO_V_CASO"));
        datosGeneralesOficio.setDistritoFiscal(rs.getString("NO_V_DISTRITO_FISCAL"));
        datosGeneralesOficio.setEspecialidad(rs.getString("NO_V_ESPECIALIDAD"));
        datosGeneralesOficio.setFiscalia(rs.getString("NO_V_ENTIDAD"));
        datosGeneralesOficio.setDespacho(rs.getString("NO_V_DESPACHO"));
        datosGeneralesOficio.setFechaIngreso(rs.getString("FE_D_CREACION"));

        return datosGeneralesOficio;
    }

}