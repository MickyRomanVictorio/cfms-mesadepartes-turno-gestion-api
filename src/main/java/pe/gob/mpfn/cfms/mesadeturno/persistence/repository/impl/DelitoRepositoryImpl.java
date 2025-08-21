package pe.gob.mpfn.cfms.mesadeturno.persistence.repository.impl;

import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import pe.gob.mpfn.cfms.generales.libreria.core.dto.PaginacionWrapperDTO;
import pe.gob.mpfn.cfms.generales.libreria.core.exception.UnprocessableEntityException;
import pe.gob.mpfn.cfms.generales.libreria.core.utils.ErrorUtil;
import pe.gob.mpfn.cfms.generales.libreria.core.utils.StringUtil;
import pe.gob.mpfn.cfms.mesadeturno.dto.*;
import pe.gob.mpfn.cfms.mesadeturno.enums.ObjectosOracle;
import pe.gob.mpfn.cfms.mesadeturno.persistence.mapper.BusquedaDelitoMapper;
import pe.gob.mpfn.cfms.mesadeturno.persistence.mapper.TiposGradoDelitoMapper;
import pe.gob.mpfn.cfms.mesadeturno.persistence.repository.DelitoRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class DelitoRepositoryImpl implements DelitoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final StringUtil stringUtil = new StringUtil();

    private final ErrorUtil errorUtil = new ErrorUtil();

    @Override
    public PaginacionWrapperDTO<DelitosDto> buscarDelitos(BuscarDelitosDto buscarDelitosDto)
            throws Exception {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(ObjectosOracle.SISMPA.getValue())
                .withCatalogName(ObjectosOracle.MPPK_CASO_TURNO.getValue())
                .withProcedureName(ObjectosOracle.MPSP_BUSCAR_DELITOS.getValue())
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PI_DELITO", OracleTypes.VARCHAR),
                        new SqlParameter("PI_N_PAGINA", OracleTypes.NUMBER),
                        new SqlParameter("PI_N_POR_PAGINA", OracleTypes.NUMBER),
                        new SqlOutParameter("PO_N_TOTAL_PAGINAS", OracleTypes.NUMBER),
                        new SqlOutParameter("PO_N_TOTAL_REGISTROS", OracleTypes.NUMBER),
                        new SqlOutParameter("PO_CR_DELITOS", OracleTypes.CURSOR, new BusquedaDelitoMapper()),
                        new SqlOutParameter("PO_V_ERR_COD", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ERR_MSG", OracleTypes.VARCHAR)
                );

		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("PI_DELITO", buscarDelitosDto.getTexto() == null ? org.apache.commons.lang3.StringUtils.EMPTY
				: buscarDelitosDto.getTexto().toUpperCase());
		inParams.put("PI_N_PAGINA", buscarDelitosDto.getPagina());
		inParams.put("PI_N_POR_PAGINA", buscarDelitosDto.getLimite());

        Map<String, Object> out = simpleJdbcCall.execute(inParams);
        String codigoError = stringUtil.toStr(out.get("PO_V_ERR_COD"));
        String descripcionError = stringUtil.toStr(out.get("PO_V_ERR_MSG"));

        if (errorUtil.esErrorGenerico(codigoError)) {
            throw new Exception(descripcionError);
        }

        if (errorUtil.esErrorValidacionNegocio(codigoError)) {
            throw new UnprocessableEntityException(codigoError, descripcionError);
        }

        List<DelitosDto> delitos = (List<DelitosDto>) out.get("PO_CR_DELITOS");
        Integer totalElementos = stringUtil.toInteger(out.get("PO_N_TOTAL_REGISTROS"));
        Integer totalPaginas = stringUtil.toInteger(out.get("PO_N_TOTAL_PAGINAS"));

        PaginacionWrapperDTO<DelitosDto> delitosPaginados = new PaginacionWrapperDTO<>();
        delitosPaginados.setRegistros(delitos);
        delitosPaginados.setTotalElementos(totalElementos);
        delitosPaginados.setTotalPaginas(totalPaginas);

        return delitosPaginados;
    }

    @Override
    public List<TiposGradoDelitoDto> listarTiposGradoDelitos() throws Exception {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(ObjectosOracle.SISMPA.getValue())
                .withCatalogName(ObjectosOracle.MPPK_CASO_TURNO.getValue())
                .withProcedureName(ObjectosOracle.MPSP_LISTAR_TIPOS_GRADO_DELITO.getValue())
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("PO_CR_TIPOS_GRADO_DELITO", OracleTypes.CURSOR, new TiposGradoDelitoMapper()),
                        new SqlOutParameter("PO_V_ERR_COD", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ERR_MSG", OracleTypes.VARCHAR)
                );

        Map<String, Object> out = simpleJdbcCall.execute();
        String codigoError = stringUtil.toStr(out.get("PO_V_ERR_COD"));
        String descripcionError = stringUtil.toStr(out.get("PO_V_ERR_MSG"));

        if (errorUtil.esErrorGenerico(codigoError)) {
            throw new Exception(descripcionError);
        }

        if (errorUtil.esErrorValidacionNegocio(codigoError)) {
            throw new UnprocessableEntityException(codigoError, descripcionError);
        }

        return (List<TiposGradoDelitoDto>) out.get("PO_CR_TIPOS_GRADO_DELITO");
    }

    @Override
    public String registrarDelitos(RegistrarDelitosDetenidosDto registrarDelitosDetenidosDto) throws Exception {

        for (AgregarDelitosDto agregarDelitosDto :
                registrarDelitosDetenidosDto.getDelitos()) {

            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                    .withSchemaName(ObjectosOracle.SISMPA.getValue())
                    .withCatalogName(ObjectosOracle.MPPK_CASO_TURNO.getValue())
                    .withProcedureName(ObjectosOracle.MPSP_REGISTRAR_DELITO.getValue())
                    .declareParameters(
                            new SqlParameter("PI_ID_V_DENUNCIA", OracleTypes.VARCHAR),
                            new SqlParameter("PI_ID_N_DELITO", OracleTypes.NUMBER),
                            new SqlParameter("PI_ID_N_SUBGENERICO", OracleTypes.NUMBER),
                            new SqlParameter("PI_ID_N_ESPECIFICO", OracleTypes.NUMBER),
                            new SqlParameter("PI_ID_N_TIPO_GRADO_DELITO", OracleTypes.NUMBER),
                            new SqlParameter("PI_CO_V_US_CREACION", OracleTypes.NUMBER),
                            new SqlParameter("PI_ID_N_ORIGEN_TURNO", OracleTypes.NUMBER),
                            new SqlOutParameter("PO_V_ERR_COD", OracleTypes.VARCHAR),
                            new SqlOutParameter("PO_V_ERR_MSG", OracleTypes.VARCHAR)
                    );

            SqlParameterSource inputParameters = new MapSqlParameterSource()
                    .addValue("PI_ID_V_DENUNCIA", registrarDelitosDetenidosDto.getIdDenuncia())
                    .addValue("PI_ID_N_DELITO", agregarDelitosDto.getIdDelitoGenerico())
                    .addValue("PI_ID_N_SUBGENERICO", agregarDelitosDto.getIdDelitoSubGenerico())
                    .addValue("PI_ID_N_ESPECIFICO", agregarDelitosDto.getIdDelitoEspecifico())
                    .addValue("PI_ID_N_TIPO_GRADO_DELITO", agregarDelitosDto.getIdTipoGradoDelito())
                    .addValue("PI_ID_N_ORIGEN_TURNO", agregarDelitosDto.getIdOrigenTurno())
                    .addValue("PI_CO_V_US_CREACION", "ADMIN");

            Map<String, Object> outParameters = simpleJdbcCall.execute(inputParameters);
            String codigoError = stringUtil.toStr(outParameters.get("PO_V_ERR_COD"));
            String descripcionError = stringUtil.toStr(outParameters.get("PO_V_ERR_MSG"));

            if (errorUtil.esErrorGenerico(codigoError)) {
                throw new Exception(descripcionError);
            }

            if (errorUtil.esErrorValidacionNegocio(codigoError)) {
                throw new UnprocessableEntityException(codigoError, descripcionError);
            }
        }

        return "Delitos registrados con éxito";
    }

    @Override
    public String registrarDetenidosDelitos(RegistrarDelitosDetenidosDto registrarDelitosDetenidosDto) throws Exception {

        for (AgregarDetenidosDto agregarDetenidosDto :
                registrarDelitosDetenidosDto.getDetenidos()) {

            for (AgregarDelitosDto agregarDelitosDto :
                    agregarDetenidosDto.getDelitos()) {

                SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                        .withSchemaName(ObjectosOracle.SISMPA.getValue())
                        .withCatalogName(ObjectosOracle.MPPK_CASO_TURNO.getValue())
                        .withProcedureName(ObjectosOracle.MPSP_REGISTRAR_DETENIDO_DELITO.getValue())
                        .declareParameters(
                                new SqlParameter("PI_ID_V_SUJETO_CASO", OracleTypes.VARCHAR),
                                new SqlParameter("PI_ID_N_DELITO", OracleTypes.NUMBER),
                                new SqlParameter("PI_ID_N_SUBGENERICO", OracleTypes.NUMBER),
                                new SqlParameter("PI_ID_N_ESPECIFICO", OracleTypes.NUMBER),
                                new SqlParameter("PI_ID_N_TIPO_GRADO_DELITO", OracleTypes.NUMBER),
                                new SqlParameter("PI_ID_N_ORIGEN_TURNO", OracleTypes.NUMBER),
                                new SqlParameter("PI_CO_V_US_CREACION", OracleTypes.NUMBER),
                                new SqlOutParameter("PO_V_ERR_COD", OracleTypes.VARCHAR),
                                new SqlOutParameter("PO_V_ERR_MSG", OracleTypes.VARCHAR)
                        );

                SqlParameterSource inputParameters = new MapSqlParameterSource()
                        .addValue("PI_ID_V_SUJETO_CASO", registrarDelitosDetenidosDto.getIdDenuncia())
                        .addValue("PI_ID_N_DELITO", agregarDelitosDto.getIdDelitoGenerico())
                        .addValue("PI_ID_N_SUBGENERICO", agregarDelitosDto.getIdDelitoSubGenerico())
                        .addValue("PI_ID_N_ESPECIFICO", agregarDelitosDto.getIdDelitoEspecifico())
                        .addValue("PI_ID_N_TIPO_GRADO_DELITO", agregarDelitosDto.getIdTipoGradoDelito())
                        .addValue("PI_ID_N_ORIGEN_TURNO", agregarDelitosDto.getIdOrigenTurno())
                        .addValue("PI_CO_V_US_CREACION", "ADMIN");

                Map<String, Object> outParameters = simpleJdbcCall.execute(inputParameters);
                String codigoError = stringUtil.toStr(outParameters.get("PO_V_ERR_COD"));
                String descripcionError = stringUtil.toStr(outParameters.get("PO_V_ERR_MSG"));

                if (errorUtil.esErrorGenerico(codigoError)) {
                    throw new Exception(descripcionError);
                }

                if (errorUtil.esErrorValidacionNegocio(codigoError)) {
                    throw new UnprocessableEntityException(codigoError, descripcionError);
                }
            }
        }

        return "Detenidos y delitos registrados con éxito";
    }
}
