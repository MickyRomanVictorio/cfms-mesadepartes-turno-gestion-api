package pe.gob.mpfn.cfms.mesadeturno.persistence.repository.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.OracleTypes;
import pe.gob.mpfn.cfms.generales.libreria.core.exception.UnprocessableEntityException;
import pe.gob.mpfn.cfms.generales.libreria.core.utils.ErrorUtil;
import pe.gob.mpfn.cfms.generales.libreria.core.utils.StringUtil;
import pe.gob.mpfn.cfms.mesadeturno.dto.*;
import pe.gob.mpfn.cfms.mesadeturno.dto.informaciongeneral.DireccionInfoDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.renadespple.OperacionFichaRequest;
import pe.gob.mpfn.cfms.mesadeturno.enums.ObjectosOracle;
import pe.gob.mpfn.cfms.mesadeturno.persistence.mapper.*;
import pe.gob.mpfn.cfms.mesadeturno.persistence.repository.RenadesppleRepository;

@Repository
@Slf4j
public class RenadesppleRepositoryImpl implements RenadesppleRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private final StringUtil stringUtil = new StringUtil();
    private final ErrorUtil errorUtil = new ErrorUtil();

    @Override
    public List<FichaRenadesppleDto> obtenerFichaRenadespple(String idCaso, String idCasoSujeto) throws Exception {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(ObjectosOracle.SISMPA.getValue())
                .withCatalogName(ObjectosOracle.MPPK_CASO_TURNO.getValue())
                .withProcedureName(ObjectosOracle.MPSP_OBTENER_FICHA_RENADESPPLE.getValue())
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PI_ID_V_CASO", OracleTypes.VARCHAR),
                        new SqlParameter("PI_ID_V_SUJETO_CASO", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_CR_FICHAS_RENADESPPLE", OracleTypes.CURSOR, new FichaRenadesppleMapper()),
                        new SqlOutParameter("PO_V_ERR_COD", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ERR_MSG", OracleTypes.VARCHAR)
                );

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("PI_ID_V_CASO", idCaso)
                .addValue("PI_ID_V_SUJETO_CASO", idCasoSujeto);

        Map<String, Object> out = simpleJdbcCall.execute(inputParameters);
        String codigoError = stringUtil.toStr(out.get("PO_V_ERR_COD"));
        String descripcionError = stringUtil.toStr(out.get("PO_V_ERR_MSG"));

        if (errorUtil.esErrorGenerico(codigoError)) {
            throw new Exception(descripcionError);
        }

        if (errorUtil.esErrorValidacionNegocio(codigoError)) {
            throw new UnprocessableEntityException(codigoError, descripcionError);
        }

        return (List<FichaRenadesppleDto>) out.get("PO_CR_FICHAS_RENADESPPLE");
    }

    @Override
    public List<TiposGradoDelitoDto> listarOficinasRenadespple() throws Exception {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(ObjectosOracle.SISMPA.getValue())
                .withCatalogName(ObjectosOracle.MPPK_CASO_TURNO.getValue())
                .withProcedureName(ObjectosOracle.MPSP_LISTAR_OFICINAS_RENADESPPLE.getValue())
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("PO_CR_OFICINAS_RENADESPPLE", OracleTypes.CURSOR, new TiposGradoDelitoMapper()),
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

        return (List<TiposGradoDelitoDto>) out.get("PO_CR_OFICINAS_RENADESPPLE");
    }

    @Override
    public List<ItemLista> listarMotivosDetencion() throws Exception {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(ObjectosOracle.SISMPA.getValue())
                .withCatalogName(ObjectosOracle.MPPK_CASO_TURNO.getValue())
                .withProcedureName(ObjectosOracle.MPSP_LISTAR_MOTIVOS_DETENCION.getValue())
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("PO_CR_MOTIVOS_DETENCION", OracleTypes.CURSOR, (RowMapper<ItemLista>) (rs, rowNum) -> new ItemLista(
                                rs.getInt("ID_N_CATALOGO"), rs.getString("NO_V_DESCRIPCION")
                        )),
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

        return (List<ItemLista>) out.get("PO_CR_MOTIVOS_DETENCION");
    }

    @Override
    public List<ItemLista> listarSituacionesJuridicas() throws Exception {
        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(ObjectosOracle.SISMPA.getValue())
                .withCatalogName(ObjectosOracle.MPPK_CASO_TURNO.getValue())
                .withProcedureName(ObjectosOracle.MPSP_LISTAR_SITUACIONES_JURIDICAS.getValue())
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlOutParameter("PO_CR_SITUACIONES_JURIDICAS", OracleTypes.CURSOR, (RowMapper<ItemLista>) (rs, rowNum) -> new ItemLista(
                                rs.getInt("ID_N_CATALOGO"), rs.getString("NO_V_DESCRIPCION")
                        )),
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

        return (List<ItemLista>) out.get("PO_CR_SITUACIONES_JURIDICAS");
    }

    @Override
    public String eliminarDireccionInfo(String idDireccion) throws Exception {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(ObjectosOracle.SISMPA.getValue())
                .withCatalogName(ObjectosOracle.MPPK_CASO_TURNO.getValue())
                .withProcedureName(ObjectosOracle.MPSP_ELIMINAR_DIRECCION_INFO.getValue())
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PI_ID_V_DIRECCION", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ERR_COD", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ERR_MSG", OracleTypes.VARCHAR)
                );

        Map<String, Object> out = simpleJdbcCall.execute(Map.of("PI_ID_V_DIRECCION", idDireccion));
        String codigoError = stringUtil.toStr(out.get("PO_V_ERR_COD"));
        String descripcionError = stringUtil.toStr(out.get("PO_V_ERR_MSG"));

        if (errorUtil.esErrorGenerico(codigoError)) {
            throw new Exception(descripcionError);
        }

        if (errorUtil.esErrorValidacionNegocio(codigoError)) {
            throw new UnprocessableEntityException(codigoError, descripcionError);
        }

        return "Eliminado correctamente";
    }

    @Override
    public String actualizarFichaRenadespple(OperacionFichaRequest operacionFichaRequest) throws Exception {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(ObjectosOracle.SISMPA.getValue())
                .withCatalogName(ObjectosOracle.MPPK_CASO_TURNO.getValue())
                .withProcedureName(ObjectosOracle.MPSP_REGISTRAR_ACTUALIZAR_FICHA_RENADESPPLE.getValue())
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PI_ID_V_FICHA_RENADESPPLE", OracleTypes.VARCHAR),
                        new SqlParameter("PI_ID_V_CASO", OracleTypes.VARCHAR),
                        new SqlParameter("PI_ID_V_SUJETO_CASO", OracleTypes.VARCHAR),
                        new SqlParameter("PI_ID_N_OFICINA", OracleTypes.VARCHAR),
                        new SqlParameter("PI_FL_C_AFROPERUANO", OracleTypes.CHAR),
                        new SqlParameter("PI_FL_C_DISCAPACIDAD", OracleTypes.CHAR),
                        new SqlParameter("PI_FL_C_PRIV_LIBERTAD", OracleTypes.CHAR),
                        new SqlParameter("PI_FL_C_VIH_TBC", OracleTypes.CHAR),
                        new SqlParameter("PI_FL_C_TRAB_HOGAR", OracleTypes.CHAR),
                        new SqlParameter("PI_FL_C_LGTBI", OracleTypes.CHAR),
                        new SqlParameter("PI_FL_C_MIGRANTE", OracleTypes.CHAR),
                        new SqlParameter("PI_FL_C_VICTIM_VIOLE_8020", OracleTypes.CHAR),
                        new SqlParameter("PI_FL_C_DEFENSOR", OracleTypes.CHAR),
                        new SqlParameter("PI_FL_C_FUNC_PUBLICO", OracleTypes.CHAR),
                        new SqlParameter("PI_ID_N_TIPO_LENGUA", OracleTypes.NUMBER),
                        new SqlParameter("PI_ID_N_TIPO_PUEBLO", OracleTypes.NUMBER),
                        new SqlParameter("PI_FL_C_AVANCE", OracleTypes.CHAR),
                        new SqlParameter("PI_N_STEP", OracleTypes.NUMBER),
                        new SqlParameter("PI_N_OPERACION", OracleTypes.NUMBER),
                        new SqlParameter("PI_FL_C_TENTATIVA", OracleTypes.CHAR),
                        new SqlParameter("PI_ID_N_MOTIVO_DETENCION", OracleTypes.NUMBER),
                        new SqlParameter("PI_NU_V_INFORME_POL", OracleTypes.VARCHAR),
                        new SqlParameter("PI_FE_D_DENUNCIA_POLI", OracleTypes.VARCHAR),
                        new SqlParameter("PI_ID_N_SITUACION_JURIDICA", OracleTypes.NUMBER),
                        new SqlParameter("PI_FE_D_LIBERTAD", OracleTypes.VARCHAR),
                        new SqlParameter("PI_V_ALIAS", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ERR_COD", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ERR_MSG", OracleTypes.VARCHAR)
                );

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));


        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");

        String outputFechaLibertad = null;
        if (operacionFichaRequest.getFechaLibertad() != null && !operacionFichaRequest.getFechaLibertad().isEmpty()) {
            Date date = isoFormat.parse(operacionFichaRequest.getFechaLibertad());
            outputFechaLibertad = outputFormat.format(date);
        }

        String outputFechaEmision = null;
        if (operacionFichaRequest.getFechaEmisionDocumento() != null && !operacionFichaRequest.getFechaEmisionDocumento().isEmpty()) {
            Date date = isoFormat.parse(operacionFichaRequest.getFechaEmisionDocumento());
            outputFechaEmision = outputFormat.format(date);
        }

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("PI_ID_V_FICHA_RENADESPPLE", operacionFichaRequest.getIdFicha())
                .addValue("PI_ID_V_CASO", operacionFichaRequest.getIdCaso())
                .addValue("PI_ID_V_SUJETO_CASO", operacionFichaRequest.getIdSujetoCaso())
                .addValue("PI_ID_N_OFICINA", operacionFichaRequest.getIdOficina())
                .addValue("PI_FL_C_AFROPERUANO", operacionFichaRequest.getFlagAfroperuana())
                .addValue("PI_FL_C_DISCAPACIDAD", operacionFichaRequest.getFlagDiscapacidad())
                .addValue("PI_FL_C_PRIV_LIBERTAD", operacionFichaRequest.getFlagPrivLibertad())
                .addValue("PI_FL_C_VIH_TBC", operacionFichaRequest.getFlagVIHTBC())
                .addValue("PI_FL_C_TRAB_HOGAR", operacionFichaRequest.getFlagTrabHogar())
                .addValue("PI_FL_C_LGTBI", operacionFichaRequest.getFlagLGTBIQ())
                .addValue("PI_FL_C_MIGRANTE", operacionFichaRequest.getFlagMigrante())
                .addValue("PI_FL_C_VICTIM_VIOLE_8020", operacionFichaRequest.getFlagVictimaViolencia())
                .addValue("PI_FL_C_DEFENSOR", operacionFichaRequest.getFlagDefensor())
                .addValue("PI_FL_C_FUNC_PUBLICO", operacionFichaRequest.getFlagServPublico())
                .addValue("PI_ID_N_TIPO_LENGUA", operacionFichaRequest.getTipoLengua())
                .addValue("PI_ID_N_TIPO_PUEBLO", operacionFichaRequest.getTipoPueblo())
                .addValue("PI_FL_C_AVANCE", operacionFichaRequest.getFlagAvance())
                .addValue("PI_N_STEP", operacionFichaRequest.getNumeroStep())
                .addValue("PI_N_OPERACION", operacionFichaRequest.getOperacion())
                .addValue("PI_FL_C_TENTATIVA", operacionFichaRequest.getFlagTentativa())
                .addValue("PI_ID_N_MOTIVO_DETENCION", operacionFichaRequest.getIdMotivoDetencion())
                .addValue("PI_NU_V_INFORME_POL", operacionFichaRequest.getInformePolicial())
                .addValue("PI_ID_N_SITUACION_JURIDICA", operacionFichaRequest.getIdSituacionJuridica())
                .addValue("PI_FE_D_LIBERTAD", outputFechaLibertad)
                .addValue("PI_V_ALIAS", operacionFichaRequest.getAlias())
                .addValue("PI_FE_D_DENUNCIA_POLI", outputFechaEmision);

        Map<String, Object> out = simpleJdbcCall.execute(inputParameters);
        String codigoError = stringUtil.toStr(out.get("PO_V_ERR_COD"));
        String descripcionError = stringUtil.toStr(out.get("PO_V_ERR_MSG"));

        if (errorUtil.esErrorGenerico(codigoError)) {
            throw new Exception(descripcionError);
        }

        if (errorUtil.esErrorValidacionNegocio(codigoError)) {
            throw new UnprocessableEntityException(codigoError, descripcionError);
        }

        return "Registrado/actualizado correctamente";
    }

    @Override
    public List<DireccionInfoDto> listarDireccionesInformacionGeneral(String idSujetoCaso) throws Exception {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(ObjectosOracle.SISMPA.getValue())
                .withCatalogName(ObjectosOracle.MPPK_CASO_TURNO.getValue())
                .withProcedureName(ObjectosOracle.MPSP_LISTAR_DIRECCIONES_INFO.getValue())
                .declareParameters(
                        new SqlParameter("PI_ID_V_SUJETO_CASO", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_CR_DIRECCIONES", OracleTypes.CURSOR, new DireccionInfoMapper()),
                        new SqlOutParameter("PO_V_ERR_COD", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ERR_MSG", OracleTypes.VARCHAR)
                );


        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("PI_ID_V_SUJETO_CASO", idSujetoCaso);

        Map<String, Object> out = simpleJdbcCall.execute(inputParameters);
        String codigoError = stringUtil.toStr(out.get("PO_V_ERR_COD"));
        String descripcionError = stringUtil.toStr(out.get("PO_V_ERR_MSG"));

        if (errorUtil.esErrorGenerico(codigoError)) {
            throw new RuntimeException(descripcionError);
        }

        if (errorUtil.esErrorValidacionNegocio(codigoError)) {
            throw new UnprocessableEntityException(codigoError, descripcionError);
        }

        return (List<DireccionInfoDto>) out.get("PO_CR_DIRECCIONES");
    }

    @Override
    public String operacionDireccionInfo(DireccionInfoDto direccionInfoDto) throws Exception {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(ObjectosOracle.SISMPA.getValue())
                .withCatalogName(ObjectosOracle.MPPK_CASO_TURNO.getValue())
                .withProcedureName(direccionInfoDto.getIdDireccion().isEmpty() ?
                        ObjectosOracle.MPSP_REGISTRAR_DIRECCION_INFO.getValue() :
                        ObjectosOracle.MPSP_ACTUALIZAR_DIRECCION_INFO.getValue())
                .declareParameters(
                        new SqlParameter("PI_ID_V_DIRECCION", OracleTypes.VARCHAR),
                        new SqlParameter("PI_ID_V_SUJETO_CASO", OracleTypes.VARCHAR),
                        new SqlParameter("PI_ID_N_TIPO_DOMICILIO", OracleTypes.NUMBER),
                        new SqlParameter("PI_ID_N_PAIS", OracleTypes.NUMBER),
                        new SqlParameter("PI_ID_V_UBIGEO", OracleTypes.VARCHAR),
                        new SqlParameter("PI_ID_N_TIPO_VIA", OracleTypes.NUMBER),
                        new SqlParameter("PI_DI_V_RESIDENCIA", OracleTypes.VARCHAR),
                        new SqlParameter("PI_NU_N_RESIDENCIA", OracleTypes.NUMBER),
                        new SqlParameter("PI_ID_N_TIPO_PREF_URB", OracleTypes.NUMBER),
                        new SqlParameter("PI_DE_V_URBANIZACION", OracleTypes.VARCHAR),
                        new SqlParameter("PI_DE_V_BLOCK_DIRECCION", OracleTypes.VARCHAR),
                        new SqlParameter("PI_DE_V_INTERIOR", OracleTypes.VARCHAR),
                        new SqlParameter("PI_DE_V_ETAPA", OracleTypes.VARCHAR),
                        new SqlParameter("PI_DE_V_MANZANA", OracleTypes.VARCHAR),
                        new SqlParameter("PI_DE_V_LOTE", OracleTypes.VARCHAR),
                        new SqlParameter("PI_DE_V_REFERENCIA", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ERR_COD", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ERR_MSG", OracleTypes.VARCHAR)
                );

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("PI_ID_V_DIRECCION", direccionInfoDto.getIdDireccion())
                .addValue("PI_ID_V_SUJETO_CASO", direccionInfoDto.getIdSujetoCaso())
                .addValue("PI_ID_N_TIPO_DOMICILIO", direccionInfoDto.getIdTipoDireccion())
                .addValue("PI_ID_N_PAIS", direccionInfoDto.getIdPais())
                .addValue("PI_ID_V_UBIGEO", direccionInfoDto.getUbigeo())
                .addValue("PI_ID_N_TIPO_VIA", direccionInfoDto.getIdTipoVia())
                .addValue("PI_DI_V_RESIDENCIA", direccionInfoDto.getDesResidencia())
                .addValue("PI_NU_N_RESIDENCIA", direccionInfoDto.getNroResidencia())
                .addValue("PI_ID_N_TIPO_PREF_URB", direccionInfoDto.getTipoUrbanizacion())
                .addValue("PI_DE_V_URBANIZACION", direccionInfoDto.getDesUrbanizacion())
                .addValue("PI_DE_V_BLOCK_DIRECCION", direccionInfoDto.getBlock())
                .addValue("PI_DE_V_INTERIOR", direccionInfoDto.getInterior())
                .addValue("PI_DE_V_ETAPA", direccionInfoDto.getEtapa())
                .addValue("PI_DE_V_MANZANA", direccionInfoDto.getManzana())
                .addValue("PI_DE_V_LOTE", direccionInfoDto.getLote())
                .addValue("PI_DE_V_REFERENCIA", direccionInfoDto.getReferencia());

        Map<String, Object> outParameters = simpleJdbcCall.execute(inputParameters);
        String codigoError = stringUtil.toStr(outParameters.get("PO_V_ERR_COD"));
        String descripcionError = stringUtil.toStr(outParameters.get("PO_V_ERR_MSG"));

        if (errorUtil.esErrorGenerico(codigoError)) {
            throw new Exception(descripcionError);
        }

        if (errorUtil.esErrorValidacionNegocio(codigoError)) {
            throw new UnprocessableEntityException(codigoError, descripcionError);
        }

        return "Dirección registrada/actualizada correctamente";
    }

    @Override
    public MomentoDetencionDto obtenerMomentoDetencion(String idCaso) throws Exception {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(ObjectosOracle.SISMPA.getValue())
                .withCatalogName(ObjectosOracle.MPPK_CASO_TURNO.getValue())
                .withProcedureName(ObjectosOracle.MPSP_OBTENER_MOMENTO_DETENCION.getValue())
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PI_ID_V_CASO", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_CR_MOMENTO_DETENCION", OracleTypes.CURSOR, new MomentoDetencionMapper()),
                        new SqlOutParameter("PO_V_ERR_COD", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ERR_MSG", OracleTypes.VARCHAR)
                );

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("PI_ID_V_CASO", idCaso);

        Map<String, Object> out = simpleJdbcCall.execute(inputParameters);

        String codigoError = stringUtil.toStr(out.get("PO_V_ERR_COD"));
        String descripcionError = stringUtil.toStr(out.get("PO_V_ERR_MSG"));


        if (errorUtil.esErrorGenerico(codigoError)) {
            throw new Exception(descripcionError);
        }

        if (errorUtil.esErrorValidacionNegocio(codigoError)) {
            throw new UnprocessableEntityException(codigoError, descripcionError);
        }
        List<MomentoDetencionDto> momento = (List<MomentoDetencionDto>) out.get("PO_CR_MOMENTO_DETENCION");
        return momento.size() == 0 ? new MomentoDetencionDto(idCaso) : momento.get(0);
    }

    @Override
    public InvestigacionPolicialDto obtenerInvestigacionPolicial(String idSujetoCaso) throws Exception {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(ObjectosOracle.SISMPA.getValue())
                .withCatalogName(ObjectosOracle.MPPK_CASO_TURNO.getValue())
                .withProcedureName(ObjectosOracle.MPSP_OBTENER_INVESTIGACION_POLICIAL.getValue())
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PI_ID_V_SUJETO_CASO", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_CR_INVESTIGACION_POLICIAL", OracleTypes.CURSOR, new InvestigacionPolicialMapper()),
                        new SqlOutParameter("PO_V_ERR_COD", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ERR_MSG", OracleTypes.VARCHAR)
                );

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("PI_ID_V_SUJETO_CASO", idSujetoCaso);

        Map<String, Object> out = simpleJdbcCall.execute(inputParameters);

        String codigoError = stringUtil.toStr(out.get("PO_V_ERR_COD"));
        String descripcionError = stringUtil.toStr(out.get("PO_V_ERR_MSG"));


        if (errorUtil.esErrorGenerico(codigoError)) {
            throw new Exception(descripcionError);
        }

        if (errorUtil.esErrorValidacionNegocio(codigoError)) {
            throw new UnprocessableEntityException(codigoError, descripcionError);
        }

        List<InvestigacionPolicialDto> investigacionPolicialDtos = (List<InvestigacionPolicialDto>) out.get("PO_CR_INVESTIGACION_POLICIAL");
        return investigacionPolicialDtos.size() == 0 ? new InvestigacionPolicialDto() : investigacionPolicialDtos.get(0);
    }

    @Override
    public DatosReniecDto obtenerDatosReniec(String idSujetoCaso) throws Exception {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(ObjectosOracle.SISMPA.getValue())
                .withCatalogName(ObjectosOracle.MPPK_CASO_TURNO.getValue())
                .withProcedureName(ObjectosOracle.MPSP_CONSULTAR_PERSONA.getValue())
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PI_ID_V_SUJETO_CASO", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_CR_PERSONA", OracleTypes.CURSOR, new DatosReniecMapper()),
                        new SqlOutParameter("PO_V_ERR_COD", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ERR_MSG", OracleTypes.VARCHAR)
                );

        SqlParameterSource inputParameters = new MapSqlParameterSource()
                .addValue("PI_ID_V_SUJETO_CASO", idSujetoCaso);

        Map<String, Object> out = simpleJdbcCall.execute(inputParameters);

        String codigoError = stringUtil.toStr(out.get("PO_V_ERR_COD"));
        String descripcionError = stringUtil.toStr(out.get("PO_V_ERR_MSG"));

        if (errorUtil.esErrorGenerico(codigoError)) {
            throw new Exception(descripcionError);
        }

        if (errorUtil.esErrorValidacionNegocio(codigoError)) {
            throw new UnprocessableEntityException(codigoError, descripcionError);
        }

        List<DatosReniecDto> datosReniecDtoList = (List<DatosReniecDto>) out.get("PO_CR_PERSONA");
        return !datosReniecDtoList.isEmpty() ? getItemValue(datosReniecDtoList) : null;
    }

    DatosReniecDto getItemValue(List<DatosReniecDto> lista){
      return lista.stream()
        .reduce((d, v) -> {
          d.setCorreoElecPrincipal(v.getCorreoElecPrincipal() != null ? v.getCorreoElecPrincipal() : d.getCorreoElecPrincipal());
          d.setCorreoElecSecundario(v.getCorreoElecSecundario() != null ? v.getCorreoElecSecundario() : d.getCorreoElecSecundario());
          d.setTelefonoPrincipal(v.getTelefonoPrincipal() != null ? v.getTelefonoPrincipal() : d.getTelefonoPrincipal());
          d.setTelefonoSecundario(v.getTelefonoSecundario() != null ? v.getTelefonoSecundario() : d.getTelefonoSecundario());
          return d;
        })
        .orElseGet(DatosReniecDto::new);
    }
}
