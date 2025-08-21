package pe.gob.mpfn.cfms.mesadeturno.persistence.repository.impl;

import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import pe.gob.mpfn.cfms.generales.libreria.core.exception.UnprocessableEntityException;
import pe.gob.mpfn.cfms.generales.libreria.core.utils.ErrorUtil;
import pe.gob.mpfn.cfms.generales.libreria.core.utils.StringUtil;
import pe.gob.mpfn.cfms.mesadeturno.dto.DelitoDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.ImputadoRenadesppleDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.PersonaJuridicaDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.PersonaNaturalDto;
import pe.gob.mpfn.cfms.mesadeturno.enums.ObjectosOracle;
import pe.gob.mpfn.cfms.mesadeturno.persistence.mapper.*;
import pe.gob.mpfn.cfms.mesadeturno.persistence.repository.DelitoPartesRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class DelitoPartesRepositoryImpl implements DelitoPartesRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StringUtil stringUtil;

    @Autowired
    private ErrorUtil errorUtil;

    @Override
    public List<DelitoDto> listarDelitos(String idCaso) throws Exception {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(ObjectosOracle.SISMPA.getValue())
                .withCatalogName(ObjectosOracle.MPPK_DELITOS_PARTES_TURNO.getValue())
                .withProcedureName(ObjectosOracle.MPSP_LISTAR_DELITOS.getValue())
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PI_V_ID_CASO", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_CR_DELITOS", OracleTypes.CURSOR, new DelitoMapper()),
                        new SqlOutParameter("PO_V_ERR_COD", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ERR_MSG", OracleTypes.VARCHAR)
                );

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("PI_V_ID_CASO", idCaso);

        Map<String, Object> out = simpleJdbcCall.execute(inParams);
        String codigoError = stringUtil.toStr(out.get("PO_V_ERR_COD"));
        String descripcionError = stringUtil.toStr(out.get("PO_V_ERR_MSG"));

        if (errorUtil.esErrorGenerico(codigoError)) {
            throw new Exception(descripcionError);
        }

        if (errorUtil.esErrorValidacionNegocio(codigoError)) {
            throw new UnprocessableEntityException(codigoError, descripcionError);
        }

        return (List<DelitoDto>) out.get("PO_CR_DELITOS");
    }

    @Override
    public List<PersonaNaturalDto> listarPersonasNaturales(String idCaso) throws Exception {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(ObjectosOracle.SISMPA.getValue())
                .withCatalogName(ObjectosOracle.MPPK_DELITOS_PARTES_TURNO.getValue())
                .withProcedureName(ObjectosOracle.MPSP_LISTAR_PERSONAS_NATURALES.getValue())
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PI_V_ID_CASO", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_CR_PERSONAS_NATURALES", OracleTypes.CURSOR, new PersonaNaturalMapper()),
                        new SqlOutParameter("PO_V_ERR_COD", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ERR_MSG", OracleTypes.VARCHAR)
                );

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("PI_V_ID_CASO", idCaso);

        Map<String, Object> out = simpleJdbcCall.execute(inParams);
        String codigoError = stringUtil.toStr(out.get("PO_V_ERR_COD"));
        String descripcionError = stringUtil.toStr(out.get("PO_V_ERR_MSG"));

        if (errorUtil.esErrorGenerico(codigoError)) {
            throw new Exception(descripcionError);
        }

        if (errorUtil.esErrorValidacionNegocio(codigoError)) {
            throw new UnprocessableEntityException(codigoError, descripcionError);
        }

        return (List<PersonaNaturalDto>) out.get("PO_CR_PERSONAS_NATURALES");
    }

    @Override
    public List<PersonaJuridicaDto> listarPersonasJuridicas(String idCaso) throws Exception {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(ObjectosOracle.SISMPA.getValue())
                .withCatalogName(ObjectosOracle.MPPK_DELITOS_PARTES_TURNO.getValue())
                .withProcedureName(ObjectosOracle.MPSP_LISTAR_PERSONAS_JURIDICAS.getValue())
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PI_V_ID_CASO", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_CR_PERSONAS_JURIDICAS", OracleTypes.CURSOR, new PersonaJuridicaMapper()),
                        new SqlOutParameter("PO_V_ERR_COD", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ERR_MSG", OracleTypes.VARCHAR)
                );

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("PI_V_ID_CASO", idCaso);

        Map<String, Object> out = simpleJdbcCall.execute(inParams);
        String codigoError = stringUtil.toStr(out.get("PO_V_ERR_COD"));
        String descripcionError = stringUtil.toStr(out.get("PO_V_ERR_MSG"));

        if (errorUtil.esErrorGenerico(codigoError)) {
            throw new Exception(descripcionError);
        }

        if (errorUtil.esErrorValidacionNegocio(codigoError)) {
            throw new UnprocessableEntityException(codigoError, descripcionError);
        }

        return (List<PersonaJuridicaDto>) out.get("PO_CR_PERSONAS_JURIDICAS");
    }

    @Override
    public List<ImputadoRenadesppleDto> listarImputadosRenadespple(String idCaso) throws Exception {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(ObjectosOracle.SISMPA.getValue())
                .withCatalogName(ObjectosOracle.MPPK_DELITOS_PARTES_TURNO.getValue())
                .withProcedureName(ObjectosOracle.MPSP_IMPUTADOS_RENADESPPLE.getValue())
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PI_V_ID_CASO", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_CR_IMPUTADOS", OracleTypes.CURSOR, new ImputadoRenadesppleMapper()),
                        new SqlOutParameter("PO_V_ERR_COD", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ERR_MSG", OracleTypes.VARCHAR)
                );

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("PI_V_ID_CASO", idCaso);

        Map<String, Object> out = simpleJdbcCall.execute(inParams);
        String codigoError = stringUtil.toStr(out.get("PO_V_ERR_COD"));
        String descripcionError = stringUtil.toStr(out.get("PO_V_ERR_MSG"));

        if (errorUtil.esErrorGenerico(codigoError)) {
            throw new Exception(descripcionError);
        }

        if (errorUtil.esErrorValidacionNegocio(codigoError)) {
            throw new UnprocessableEntityException(codigoError, descripcionError);
        }

        return (List<ImputadoRenadesppleDto>) out.get("PO_CR_IMPUTADOS");
    }
}
