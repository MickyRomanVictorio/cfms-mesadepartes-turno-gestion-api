package pe.gob.mpfn.cfms.mesadeturno.persistence.mapper;

import org.springframework.jdbc.core.RowMapper;
import pe.gob.mpfn.cfms.mesadeturno.dto.InvestigacionPolicialDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InvestigacionPolicialMapper implements RowMapper<InvestigacionPolicialDto> {
    @Override
    public InvestigacionPolicialDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        InvestigacionPolicialDto investigacionPolicialDto = new InvestigacionPolicialDto();
        investigacionPolicialDto.setInformePolicial(rs.getString("NU_V_INFORME_POLICIAL"));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        Timestamp fechaInforme = rs.getTimestamp("FE_D_INFORME_POL");

        if (fechaInforme == null) {
            investigacionPolicialDto.setFechaEmision("");
        } else {
            LocalDateTime ldt = fechaInforme.toLocalDateTime();
            String fechaEmision = ldt.format(dateFormatter);
            investigacionPolicialDto.setFechaEmision(fechaEmision);
        }

        investigacionPolicialDto.setSituacionJuridica(rs.getInt("ID_N_SITUACION_JURIDICA"));

        Timestamp fechaLibertad = rs.getTimestamp("FE_D_LIBERTAD");

        if (fechaLibertad == null) {
            investigacionPolicialDto.setFechaLibertad("");
        } else {
            LocalDateTime ldtLibertad = fechaLibertad.toLocalDateTime();
            String fechaLibertadString = ldtLibertad.format(dateFormatter);
            investigacionPolicialDto.setFechaLibertad(fechaLibertadString);
        }

        return investigacionPolicialDto;
    }
}
