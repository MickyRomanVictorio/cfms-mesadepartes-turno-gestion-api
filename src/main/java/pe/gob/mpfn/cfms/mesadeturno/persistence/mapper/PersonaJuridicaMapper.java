package pe.gob.mpfn.cfms.mesadeturno.persistence.mapper;

import org.springframework.jdbc.core.RowMapper;
import pe.gob.mpfn.cfms.mesadeturno.dto.PersonaJuridicaDto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonaJuridicaMapper implements RowMapper<PersonaJuridicaDto> {
    @Override
    public PersonaJuridicaDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        PersonaJuridicaDto personaJuridica = new PersonaJuridicaDto();

        personaJuridica.setTipoParte(rs.getString("NO_V_TIPO_PARTE_SUJETO"));
        personaJuridica.setTipoDocumento(rs.getString("NO_V_TIPO_DOC_IDENT"));
        personaJuridica.setNumeroDocumento(rs.getString("NU_V_DOCUMENTO"));
        personaJuridica.setRazonSocial(rs.getString("DE_V_RAZON_SOCIAL"));
        personaJuridica.setCorreo(rs.getString("NO_V_CORREO"));
        personaJuridica.setCelular(rs.getString("NO_V_CELULAR"));

        return personaJuridica;
    }
}
