package pe.gob.mpfn.cfms.mesadeturno.persistence.mapper;

import org.springframework.jdbc.core.RowMapper;
import pe.gob.mpfn.cfms.mesadeturno.bean.Usuario;
import pe.gob.mpfn.cfms.mesadeturno.dto.partesinvolucradas.DatosGeneralesDenunciaRecord;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DatosGeneralesDenunciaMapper implements RowMapper<DatosGeneralesDenunciaRecord> {

    private Usuario usuario;

    public DatosGeneralesDenunciaMapper(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public DatosGeneralesDenunciaRecord mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DatosGeneralesDenunciaRecord(
                rs.getString("ID_CASO"),
                rs.getString("ID_DENUNCIA"),
                rs.getString("NUMERO_CASO"),
                rs.getString("CORREO"),
                rs.getString("TELEFONO"),
                rs.getInt("ID_DEPENDENCIA_POLICIAL"),
                rs.getString("NOMBRE_DEPENDENCIA_POLICIAL"),
                rs.getObject("FECHA_LLAMADA", LocalDateTime.class),
                rs.getObject("FECHA_LLAMADA", LocalDateTime.class),
                rs.getInt("ID_TIPO_DOCUMENTO_IDENTIDAD"),
                rs.getString("NOMBRE_TIPO_DOCUMENTO_IDENTIDAD"),
                rs.getString("NUMERO_DOCUMENTO"),
                rs.getString("NOMBRE_CIUDADANO"),
                rs.getString("APELLIDO_PATERNO"),
                rs.getString("APELLIDO_MATERNO"),
                rs.getBoolean("ES_VERIFICADO"),
                usuario.getCodDistritoFiscal(),
                usuario.getDistritoFiscal(),
                rs.getString("ID_ESPECIALIDAD"),
                rs.getString("NOMBRE_ESPECIALIDAD"),
                usuario.getCodDependencia(),
                usuario.getDependencia(),
                usuario.getCodDespacho(),
                usuario.getDespacho(),
                rs.getInt("ID_N_TIPO_DENUNCIA"),
                rs.getString("FE_D_CREACION")
        );
    }
}

