package pe.gob.mpfn.cfms.mesadeturno.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.jdbc.core.RowMapper;

import pe.gob.mpfn.cfms.mesadeturno.dto.MomentoDetencionDto;

public class MomentoDetencionMapper implements RowMapper<MomentoDetencionDto> {

    @Override
    public MomentoDetencionDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        MomentoDetencionDto momentoDetencionDto = new MomentoDetencionDto();

        momentoDetencionDto.setIdDenuncia(rs.getString("ID_V_DENUNCIA"));
        momentoDetencionDto.setIdCaso(rs.getString("ID_V_CASO"));
        momentoDetencionDto.setUbigeo(rs.getString("ID_V_UBIGEO"));
        Timestamp fechaHora = rs.getTimestamp("FE_D_DETENCION");
        // Convertir java.sql.Timestamp a LocalDateTime
        LocalDateTime ldt = fechaHora.toLocalDateTime();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String fecha = ldt.format(dateFormatter);
        String hora = ldt.format(timeFormatter);
        momentoDetencionDto.setFechaDetencion(fecha);
        momentoDetencionDto.setHoraDetencion(hora);

        momentoDetencionDto.setIdDependenciaPolicial(rs.getInt("ID_N_DPND_POLICIAL"));
        momentoDetencionDto.setNombreDependenciaPolicial(rs.getString("NO_V_DPND_POLICIAL"));
        momentoDetencionDto.setIdMotivoDetencion(rs.getInt("ID_N_MOTIVO_DETENCION"));
        momentoDetencionDto.setFlgTentativa(rs.getInt("FL_C_TENTATIVA"));

        momentoDetencionDto.setDepartamento(rs.getString("NO_V_DPTO_GEOGRAFICA"));
        momentoDetencionDto.setProvincia(rs.getString("NO_V_PROV_GEOGRAFICA"));
        momentoDetencionDto.setDistrito(rs.getString("NO_V_DIST_GEOGRAFICA"));

        return momentoDetencionDto;
    }

}