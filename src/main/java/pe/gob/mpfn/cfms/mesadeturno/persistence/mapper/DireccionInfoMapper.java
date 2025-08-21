package pe.gob.mpfn.cfms.mesadeturno.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pe.gob.mpfn.cfms.mesadeturno.dto.informaciongeneral.DireccionInfoDto;

public class DireccionInfoMapper implements RowMapper<DireccionInfoDto> {
    @Override
    public DireccionInfoDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new DireccionInfoDto(
                rs.getString("ID_V_DIRECCION"),
                rs.getInt("ID_N_TIPO_DOMICILIO"),
                rs.getString("NO_V_TIPO_DOMICILIO"),
                rs.getInt("ID_N_PAIS"),
                rs.getString("ID_N_UBIGEO"),
                rs.getString("CO_V_DPTO_GEOGRAFICA"),
                rs.getString("NO_V_DPTO_GEOGRAFICA"),
                rs.getString("CO_V_PROV_GEOGRAFICA"),
                rs.getString("NO_V_PROV_GEOGRAFICA"),
                rs.getString("CO_V_DIST_GEOGRAFICA"),
                rs.getString("NO_V_DIST_GEOGRAFICA"),
                rs.getInt("ID_N_TIPO_VIA"),
                rs.getString("NO_V_TIPO_VIA"),
                rs.getString("DI_V_RESIDENCIA"),
                rs.getInt("NU_N_RESIDENCIA"),
                rs.getInt("ID_N_TIPO_PREF_URB"),
                rs.getString("DE_V_URBANIZACION"),
                rs.getString("DE_V_BLOCK_DIRECCION"),
                rs.getString("DE_V_INTERIOR"),
                rs.getString("DE_V_ETAPA"),
                rs.getString("DE_V_MANZANA"),
                rs.getString("DE_V_LOTE"),
                rs.getString("DE_V_REFERENCIA"),
                rs.getString("CD_V_LATITUD"),
                rs.getString("CD_V_LONGITUD"),
                null
        );
    }
}
