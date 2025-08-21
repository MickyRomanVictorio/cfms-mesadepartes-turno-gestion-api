package pe.gob.mpfn.cfms.mesadeturno.persistence.mapper;

import org.springframework.jdbc.core.RowMapper;
import pe.gob.mpfn.cfms.mesadeturno.dto.DenunciaRegistradaDto;

import java.sql.ResultSet;
import java.sql.SQLException;


public class DenunciaRegistradaMapper implements RowMapper<DenunciaRegistradaDto> {

    @Override
    public DenunciaRegistradaDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        DenunciaRegistradaDto denunciaRegistrada = new DenunciaRegistradaDto();

        denunciaRegistrada.setIdCaso(rs.getString("ID_V_CASO"));
        denunciaRegistrada.setNumeroCaso(rs.getString("CO_V_CASO"));
        denunciaRegistrada.setFechaDetencionInicio(rs.getString("FE_D_INICIO_DETENCION"));
        denunciaRegistrada.setFechaDetencionFin(rs.getString("FE_D_FIN_DETENCION"));
        denunciaRegistrada.setEstadoRegistro(rs.getString("DE_V_ESTADO_REGISTRO"));
        denunciaRegistrada.setCodigoTipoDenuncia(rs.getInt("ID_N_TIPO_DENUNCIA"));
        denunciaRegistrada.setTipoDenuncia(rs.getString("NO_V_TIPO_DENUNCIA"));
        denunciaRegistrada.setFechaDenuncia(rs.getString("FE_V_FECHA_HECHO"));
        denunciaRegistrada.setHoraDenuncia(rs.getString("FE_V_HORA_HECHO"));
        denunciaRegistrada.setDependenciaPolicial(rs.getString("NO_V_DPND_POLICIAL"));
        denunciaRegistrada.setNombreDenunciante(rs.getString("NO_V_REMITENTE"));
        denunciaRegistrada.setCelularDenunciante(rs.getString("NU_V_CELULAR_REMITENTE"));
        denunciaRegistrada.setFiscalAsignado(rs.getString("NO_V_FISCAL_ASIGNADO"));
        denunciaRegistrada.setNumeroActas(rs.getInt("NU_N_ACTAS"));
        denunciaRegistrada.setFichaRenadespple(rs.getString("FL_V_FICHA_RENADESSPLE"));
        denunciaRegistrada.setNumeroSujetosPersonaNatural(rs.getInt("NU_N_PERSONAS_NATURALES"));
        denunciaRegistrada.setNumeroSujetosPersonaJuridica(rs.getInt("NU_N_PERSONAS_JURIDICAS"));

        return denunciaRegistrada;
    }

}
