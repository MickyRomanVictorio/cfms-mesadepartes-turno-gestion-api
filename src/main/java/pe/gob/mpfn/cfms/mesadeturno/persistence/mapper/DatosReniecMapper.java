package pe.gob.mpfn.cfms.mesadeturno.persistence.mapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import lombok.extern.log4j.Log4j2;
import oracle.sql.BFILE;
import pe.gob.mpfn.cfms.mesadeturno.dto.DatosReniecDto;

@Log4j2
public class DatosReniecMapper implements RowMapper<DatosReniecDto> {
	@Override
	public DatosReniecDto mapRow(ResultSet rs, int rowNum) throws SQLException {

		DatosReniecDto datosReniecDto = new DatosReniecDto();
		datosReniecDto.setFlagOrigen(rs.getString("FL_C_EXTRANJERO"));
		datosReniecDto.setIdTipoDocIdent(rs.getString("ID_N_TIPO_DOC_IDENT"));
		datosReniecDto.setDesTipoDocIdent(rs.getString("NO_V_TIPO_DOC_IDENT"));
		datosReniecDto.setNumeroDocumento(rs.getString("NU_V_DOCUMENTO"));
		datosReniecDto.setFlagVerificado(rs.getString("FL_C_VERIFICADO"));
		datosReniecDto.setApellidoPaterno(rs.getString("AP_V_PATERNO"));
		datosReniecDto.setApellidoMaterno(rs.getString("AP_V_MATERNO"));
		datosReniecDto.setNombres(rs.getString("NO_V_CIUDADANO"));
		datosReniecDto.setFechaNacimiento(rs.getString("FE_D_NACIMIENTO"));
		datosReniecDto.setEdad(rs.getLong("NU_N_EDAD"));
		datosReniecDto.setCodigoGenero(rs.getString("TI_C_SEXO"));
		datosReniecDto.setAlias(rs.getString("NO_V_ALIAS_SUJETO"));
		datosReniecDto.setTipoEstadoCivil(rs.getInt("ID_N_EST_CIV"));
		datosReniecDto.setCodigoGradoInstruccion(rs.getString("ID_N_GRAD_INST"));
		datosReniecDto.setActLaboral(rs.getInt("ID_N_TIPO_ACT_LABORAL"));
		datosReniecDto.setOtraActvLaboral(rs.getString("NO_V_OTRA_ACT_LABORAL"));
		datosReniecDto.setNombrePadre(rs.getString("NO_V_PADRE"));
		datosReniecDto.setNombreMadre(rs.getString("NO_V_MADRE"));
		datosReniecDto.setCorreoElecPrincipal(
				rs.getString("PRIORIDAD_CONTACTO") != null && rs.getString("PRIORIDAD_CONTACTO").equals("PRINCIPAL")
						&& rs.getString("NO_V_TIPO_CONTACTO").equals("Correo") ? rs.getString("NO_V_DATOS_CONTACTO")
								: null);
		datosReniecDto.setCorreoElecSecundario(
				rs.getString("PRIORIDAD_CONTACTO") != null && rs.getString("PRIORIDAD_CONTACTO").equals("SECUNDARIO")
						&& rs.getString("NO_V_TIPO_CONTACTO").equals("Correo") ? rs.getString("NO_V_DATOS_CONTACTO")
								: null);
		datosReniecDto.setTelefonoPrincipal(
				rs.getString("PRIORIDAD_CONTACTO") != null && rs.getString("PRIORIDAD_CONTACTO").equals("PRINCIPAL")
						&& rs.getString("NO_V_TIPO_CONTACTO").equals("Teléfono") ? rs.getString("NO_V_DATOS_CONTACTO")
								: null);
		datosReniecDto.setTelefonoSecundario(
				rs.getString("PRIORIDAD_CONTACTO") != null && rs.getString("PRIORIDAD_CONTACTO").equals("SECUNDARIO")
						&& rs.getString("NO_V_TIPO_CONTACTO").equals("Teléfono") ? rs.getString("NO_V_DATOS_CONTACTO")
								: null);
		datosReniecDto.setPuebloIndigena(rs.getInt("ID_N_TIPO_PUEBLO"));
		datosReniecDto.setLenguaMaterna(rs.getInt("ID_N_TIPO_LENGUA"));
		datosReniecDto.setFlagAfroperuano(rs.getString("FL_C_AFROPERUANO"));
		datosReniecDto.setFlagConDiscapacidad(rs.getString("FL_C_DISCAPACIDAD"));
		datosReniecDto.setFlagPrivadaLibertad(rs.getString("FL_C_PRIV_LIBERTAD"));
		datosReniecDto.setFlagVIHTBC(rs.getString("FL_C_VIH_TBC"));
		datosReniecDto.setFlagTrabHogar(rs.getString("FL_C_TRAB_HOGAR"));
		datosReniecDto.setFlagLGTBI(rs.getString("FL_C_LGTBI"));
		datosReniecDto.setFlagDefensor(rs.getString("FL_C_DEFENSOR"));
		datosReniecDto.setFlagMigrante(rs.getString("FL_C_MIGRANTE"));
		datosReniecDto.setFlagVictimViole(rs.getString("FL_C_VICTIM_VIOLE_8020"));
		datosReniecDto.setFlagFuncPublico(rs.getString("FL_C_FUNC_PUBLICO"));

		BFILE bfile = (BFILE) rs.getObject("FOTO");
		if (bfile != null) {
			try {
				// Abrir el BFILE
				bfile.openFile();
				// Leer el BFILE y convertirlo a base64
				try (InputStream in = bfile.getBinaryStream(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
					byte[] buffer = new byte[4096];
					int bytesRead;
					while ((bytesRead = in.read(buffer)) != -1) {
						out.write(buffer, 0, bytesRead);
					}
					// byte[] imageBytes = out.toByteArray();
					// String base64Image = java.util.Base64.getEncoder().encodeToString(imageBytes);
					// datosReniecDto.setFoto(base64Image);
                    datosReniecDto.setFoto(out.toString());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}

				// Cerrar el BFILE después de usarlo
				bfile.closeFile();

			} catch (Exception e) {
				log.info("Error abriri foto : {} ", e);
			}
		}
		return datosReniecDto;
	}
}
