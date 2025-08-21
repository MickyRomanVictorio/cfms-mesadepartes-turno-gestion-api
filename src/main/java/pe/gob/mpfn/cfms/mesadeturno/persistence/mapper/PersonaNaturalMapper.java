package pe.gob.mpfn.cfms.mesadeturno.persistence.mapper;

import org.springframework.jdbc.core.RowMapper;
import pe.gob.mpfn.cfms.mesadeturno.dto.PersonaNaturalDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonaNaturalMapper implements RowMapper<PersonaNaturalDto> {

    @Override
    public PersonaNaturalDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        PersonaNaturalDto personaNatural = new PersonaNaturalDto();

        personaNatural.setTipoParte(rs.getString("NO_V_TIPO_PARTE_SUJETO"));
        personaNatural.setTipoDocumento(rs.getString("NO_V_TIPO_DOC_IDENT"));
        personaNatural.setNumeroDocumento(rs.getString("NU_V_DOCUMENTO"));
        personaNatural.setApellidoPaterno(rs.getString("AP_V_PATERNO"));
        personaNatural.setApellidoMaterno(rs.getString("AP_V_MATERNO"));
        personaNatural.setNombres(rs.getString("NO_V_CIUDADANO"));
        personaNatural.setSexo(rs.getString("TI_C_SEXO"));
        personaNatural.setEdad(rs.getInt("NU_N_EDAD"));
        personaNatural.setCorreo(rs.getString("NO_V_CORREO"));
        personaNatural.setCelular(rs.getString("NO_V_CELULAR"));

        return personaNatural;
    }

}
