package pe.gob.mpfn.cfms.mesadeturno.persistence.repository.impl;

import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import pe.gob.mpfn.cfms.generales.libreria.core.data.repository.AbstractGenericRepository;
import pe.gob.mpfn.cfms.generales.libreria.core.data.repository.ProcedureParameter;
import pe.gob.mpfn.cfms.generales.libreria.core.dto.PaginacionWrapperDTO;
import pe.gob.mpfn.cfms.generales.libreria.core.exception.UnprocessableEntityException;
import pe.gob.mpfn.cfms.generales.libreria.core.utils.ErrorUtil;
import pe.gob.mpfn.cfms.generales.libreria.core.utils.StringUtil;
import pe.gob.mpfn.cfms.mesadeturno.bean.Usuario;
import pe.gob.mpfn.cfms.mesadeturno.common.util.JwtTokenUtility;
import pe.gob.mpfn.cfms.mesadeturno.dto.*;
import pe.gob.mpfn.cfms.mesadeturno.dto.partesinvolucradas.DatosGeneralesDenunciaRecord;
import pe.gob.mpfn.cfms.mesadeturno.dto.response.DatosGeneralesResponse;
import pe.gob.mpfn.cfms.mesadeturno.enums.ObjectosOracle;
import pe.gob.mpfn.cfms.mesadeturno.enums.TipoDenuncia;
import pe.gob.mpfn.cfms.mesadeturno.enums.TipoSujeto;
import pe.gob.mpfn.cfms.mesadeturno.persistence.mapper.DatosGeneralesDenunciaMapper;
import pe.gob.mpfn.cfms.mesadeturno.persistence.mapper.DatosGeneralesOficioMapper;
import pe.gob.mpfn.cfms.mesadeturno.persistence.mapper.DenunciaRegistradaMapper;
import pe.gob.mpfn.cfms.mesadeturno.persistence.repository.DenunciaDuplicadaRepository;
import pe.gob.mpfn.cfms.mesadeturno.persistence.repository.DenunciaRepository;
import pe.gob.mpfn.cfms.mesadeturno.util.Constantes;
import pe.gob.mpfn.cfms.mesadeturno.util.Fechas;
import pe.gob.mpfn.cfms.mesadeturno.util.UbigeoExtranjero;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Date;
import java.util.*;

@Repository
@Slf4j
public class DenunciaRepositoryImpl extends AbstractGenericRepository implements DenunciaRepository {

    private final JdbcTemplate jdbcTemplate;
    private final StringUtil stringUtil;
    private final ErrorUtil errorUtil;
    private final JwtTokenUtility jwtTokenUtility;
    private final DenunciaDuplicadaRepository denunciaDuplicadaRepository;

    public DenunciaRepositoryImpl(JdbcTemplate jdbcTemplate,
                                  StringUtil stringUtil,
                                  ErrorUtil errorUtil,
                                  JwtTokenUtility jwtTokenUtility,
                                  DenunciaDuplicadaRepository denunciaDuplicadaRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.stringUtil = stringUtil;
        this.errorUtil = errorUtil;
        this.jwtTokenUtility = jwtTokenUtility;
        this.denunciaDuplicadaRepository = denunciaDuplicadaRepository;
    }

    @Override
    public PaginacionWrapperDTO<DenunciaRegistradaDto> listarDenunciasRegistradas(
            ListarDenunciasDto listarDenunciasDto) throws Exception {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(ObjectosOracle.SISMPA.getValue())
                .withCatalogName(ObjectosOracle.MPPK_DENUNCIA_TURNO.getValue())
                .withProcedureName(ObjectosOracle.MPSP_LISTAR_DENUNCIAS_REGISTRADAS.getValue())
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PI_N_PAGINA", OracleTypes.NUMBER),
                        new SqlParameter("PI_N_POR_PAGINA", OracleTypes.NUMBER),
                        new SqlParameter("PI_V_CO_CASO", OracleTypes.VARCHAR),
                        new SqlParameter("PI_N_DPND_POLICIAL", OracleTypes.NUMBER),
                        new SqlParameter("PI_V_ID_USUARIO", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_N_TOTAL_PAGINAS", OracleTypes.NUMBER),
                        new SqlOutParameter("PO_N_TOTAL_REGISTROS", OracleTypes.NUMBER),
                        new SqlOutParameter("PO_CR_DENUNCIAS_REGISTRADAS", OracleTypes.CURSOR, new DenunciaRegistradaMapper()),
                        new SqlOutParameter("PO_V_ERR_COD", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ERR_MSG", OracleTypes.VARCHAR)
                );


        Map<String, Object> inParams = new HashMap<>();
        inParams.put("PI_N_PAGINA", listarDenunciasDto.getPagina());
        inParams.put("PI_N_POR_PAGINA", listarDenunciasDto.getLimite());
        inParams.put("PI_V_CO_CASO", listarDenunciasDto.getNumeroCaso());
        inParams.put("PI_N_DPND_POLICIAL", listarDenunciasDto.getDependenciaPolicial());
        inParams.put("PI_V_ID_USUARIO", listarDenunciasDto.getFiscal());

        Map<String, Object> out = simpleJdbcCall.execute(inParams);
        String codigoError = stringUtil.toStr(out.get("PO_V_ERR_COD"));
        String descripcionError = stringUtil.toStr(out.get("PO_V_ERR_MSG"));

        if (errorUtil.esErrorGenerico(codigoError)) {
            throw new Exception(descripcionError);
        }

        if (errorUtil.esErrorValidacionNegocio(codigoError)) {
            throw new UnprocessableEntityException(codigoError, descripcionError);
        }

        List<DenunciaRegistradaDto> denunciasRegistradas = (List<DenunciaRegistradaDto>) out.get("PO_CR_DENUNCIAS_REGISTRADAS");
        Integer totalElementos = stringUtil.toInteger(out.get("PO_N_TOTAL_REGISTROS"));
        Integer totalPaginas = stringUtil.toInteger(out.get("PO_N_TOTAL_PAGINAS"));

        PaginacionWrapperDTO<DenunciaRegistradaDto> denunciasRegistradasPaginadas = new PaginacionWrapperDTO<>();
        denunciasRegistradasPaginadas.setRegistros(denunciasRegistradas);
        denunciasRegistradasPaginadas.setTotalElementos(totalElementos);
        denunciasRegistradasPaginadas.setTotalPaginas(totalPaginas);

        return denunciasRegistradasPaginadas;
    }

    @Override
    public DatosGeneralesResponse registrarDatosGenerales(DatosGeneralesDto datosGeneralesDto, Usuario usuario) throws Exception {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(ObjectosOracle.SISMPA.getValue())
                .withCatalogName(ObjectosOracle.MPPK_DENUNCIA_TURNO.getValue())
                .withProcedureName(ObjectosOracle.MPSP_INSERTAR_DATOS_GENERALES.getValue())
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PI_V_TIPO_DOCUMENTO_IDENTIDAD", OracleTypes.NUMBER),
                        new SqlParameter("PI_V_NU_DOCUMENTO", OracleTypes.VARCHAR),
                        new SqlParameter("PI_V_NO_REMITENTE", OracleTypes.VARCHAR),
                        new SqlParameter("PI_V_AP_PATERNO", OracleTypes.VARCHAR),
                        new SqlParameter("PI_V_AP_MATERNO", OracleTypes.VARCHAR),
                        new SqlParameter("PI_V_NU_TELEFONO", OracleTypes.NVARCHAR),
                        new SqlParameter("PI_V_CORREO", OracleTypes.VARCHAR),
                        new SqlParameter("PI_V_USUARIO", OracleTypes.VARCHAR),
                        new SqlParameter("PI_N_ID_TIPO_SUJETO", OracleTypes.NUMBER),
                        new SqlParameter("PI_V_ID_DPND_FISCAL", OracleTypes.VARCHAR),
                        new SqlParameter("PI_V_CO_DESPACHO", OracleTypes.VARCHAR),
                        new SqlParameter("PI_V_ID_DPND_POLICIAL", OracleTypes.NUMBER),
                        new SqlParameter("PI_FE_D_LLAMADA", OracleTypes.DATE),
                        new SqlParameter("PI_N_ID_TIPO_DENUNCIA", OracleTypes.NUMBER),
                        new SqlParameter("PI_V_CO_DISTRITO_FISCAL", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ID_DENUNCIA", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ID_CASO", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_CO_CASO", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_CO_DESPACHO", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_NO_ESPECIALIDAD", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_N_ID_DISTRITO_FISCAL", OracleTypes.NUMBER),
                        new SqlOutParameter("PO_V_ID_ESPECIALIDAD", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_CO_ENTIDAD", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_N_ID_TIPO_ENTIDAD", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_FE_CREACION", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ERR_COD", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ERR_MSG", OracleTypes.VARCHAR)
                );


        var parameters = new HashMap<String, Object>();
        parameters.put("PI_V_TIPO_DOCUMENTO_IDENTIDAD", datosGeneralesDto.tipoDocumentoIdentidad());
        parameters.put("PI_V_NU_DOCUMENTO", datosGeneralesDto.numeroDocumentoIdentidad());
        parameters.put("PI_V_NO_REMITENTE", datosGeneralesDto.nombres());
        parameters.put("PI_V_AP_PATERNO", datosGeneralesDto.apellidoPaterno());
        parameters.put("PI_V_AP_MATERNO", datosGeneralesDto.apellidoMaterno());
        parameters.put("PI_V_NU_TELEFONO", datosGeneralesDto.nroContacto());
        parameters.put("PI_V_CORREO", datosGeneralesDto.correo());
        parameters.put("PI_V_USUARIO", usuario.getUsuario());

        String tipoSujeto = TipoSujeto.PNP.getValue();
        if (TipoDenuncia.DENUNCIA_VERBAL.getValue() == datosGeneralesDto.tipoDenuncia()) {
            tipoSujeto = TipoSujeto.CIUDADANO.getValue();
        }
        parameters.put("PI_N_ID_TIPO_SUJETO", tipoSujeto);
        parameters.put("PI_V_ID_DPND_FISCAL", usuario.getCodDependencia());
        parameters.put("PI_V_CO_DESPACHO", usuario.getCodDespacho());
        parameters.put("PI_V_ID_DPND_POLICIAL", datosGeneralesDto.idDependenciaPolicial());
        parameters.put("PI_FE_D_LLAMADA", datosGeneralesDto.fechaLlamada());
        parameters.put("PI_N_ID_TIPO_DENUNCIA", datosGeneralesDto.tipoDenuncia());
        parameters.put("PI_V_CO_DISTRITO_FISCAL", usuario.getCodDistritoFiscal());

        var result = simpleJdbcCall.execute(parameters);

        String codigoError = stringUtil.toStr(result.get("PO_V_ERR_COD"));
        String descripcionError = stringUtil.toStr(result.get("PO_V_ERR_MSG"));

        if (errorUtil.esErrorGenerico(codigoError)) {
            throw new Exception(descripcionError);
        }

        if (errorUtil.esErrorValidacionNegocio(codigoError)) {
            throw new UnprocessableEntityException(codigoError, descripcionError);
        }
        log.info("Datos insertados");
        log.info("Id denuncia: %s".formatted(String.valueOf(result.get("PO_V_ID_DENUNCIA"))));
        return new DatosGeneralesResponse(
                String.valueOf(result.get("PO_V_ID_DENUNCIA")),
                String.valueOf(result.get("PO_V_ID_CASO")),
                String.valueOf(result.get("PO_V_CO_CASO")),
                String.valueOf(result.get("PO_N_ID_DISTRITO_FISCAL")),
                usuario.getDistritoFiscal(),
                String.valueOf(result.get("PO_V_ID_ESPECIALIDAD")),
                String.valueOf(result.get("PO_V_NO_ESPECIALIDAD")),
                String.valueOf(result.get("PO_V_CO_ENTIDAD")),
                usuario.getDependencia(),
                String.valueOf(result.get("PO_V_CO_DESPACHO")),
                usuario.getDespacho(),
                Fechas.convertirFormatoFecha(String.valueOf(result.get("PO_V_FE_CREACION")))
        );
    }

    @Override
    public String validarSiMesaEstaDeTurno(String codigoDespacho) {

        List<ProcedureParameter> parameters = Arrays.asList(
                new ProcedureParameter("PIV_V_CO_DESPACHO", OracleTypes.VARCHAR, codigoDespacho)
        );

        Map<String, Object> outputParameters = executeStoreProcedure(
                ObjectosOracle.SISMPA.getValue(),
                ObjectosOracle.MPPK_DENUNCIA_TURNO.getValue(),
                ObjectosOracle.MPSP_VALIDAR_SI_MESA_ESTA_DE_TURNO.getValue(),
                parameters
        );

        String codigoError = stringUtil.toStr(outputParameters.get("PO_V_ERR_COD"));
        String descripcionError = stringUtil.toStr(outputParameters.get("PO_V_ERR_MSG"));

        if (errorUtil.esErrorValidacionNegocio(codigoError)) {
            return descripcionError;
        }

        return "VALIDADO";
    }

    @Override
    public ResponseDTO<String> identificarDenunciaDuplicada(CompletarRegistrarDenunciaDto denunciaDto) {

        Array datosParticipantesDenunciaDuplicada = denunciaDuplicadaRepository.prepararDatosParticipantesDenunciaDuplicada(denunciaDto);
        Array datosDelitosDenunciaDuplicada = denunciaDuplicadaRepository.prepararDatosDelitos(denunciaDto);

        List<ProcedureParameter> parameters = Arrays.asList(
                new ProcedureParameter("PI_TYPE_PARTICIPANTES_DENUNCIA_DUPLICADA", OracleTypes.ARRAY, datosParticipantesDenunciaDuplicada),
                new ProcedureParameter("PI_TYPE_DELITOS_DENUNCIA_DUPLICada", OracleTypes.ARRAY, datosDelitosDenunciaDuplicada),
                new ProcedureParameter("PO_V_CO_RESPUESTA", OracleTypes.VARCHAR),
                new ProcedureParameter("PO_V_CO_CASO", OracleTypes.VARCHAR)
        );
        Map<String, Object> result = executeStoreProcedure(
                ObjectosOracle.SISMPA.getValue(),
                ObjectosOracle.MPPK_REGISTRA_DENUNCIA_TURNO.getValue(),
                ObjectosOracle.MPSP_IDENTIFICAR_DENUNCIA_DUPLICADA.getValue(),
                parameters
        );

        ResponseDTO<String> responseDto = new ResponseDTO<>();
        String codRespuesta = stringUtil.toStr(result.get("PO_V_CO_RESPUESTA"));
        String nroCaso = stringUtil.toStr(result.get("PO_V_CO_CASO"));

        responseDto.setCodigo(Integer.valueOf(codRespuesta));
        responseDto.setData(nroCaso);

        log.info(responseDto.toString());
        return responseDto;
    }

    @Override
    public RespuestaRegistroDenuncia completarRegistrarDenuncia(Integer tipoDenuncia, CompletarRegistrarDenunciaDto datosDenuncia) throws Exception {

        boolean esDeOficio = tipoDenuncia.equals(Constantes.TIPO_DENUNCIA_INVESTIGACION_OFICIO);

        final String esquema = ObjectosOracle.SISMPA.getValue();
        final String paquete = ObjectosOracle.MPPK_REGISTRA_DENUNCIA_TURNO.getValue();
        final String procedimiento = ObjectosOracle.MPSP_REGISTRAR_DENUNCIA_TURNO.getValue();

        String procedureCall = String.format(
                "{call %s.%s.%s(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}",
                esquema, paquete, procedimiento
        );

        return jdbcTemplate.execute(procedureCall, (CallableStatementCallback<RespuestaRegistroDenuncia>) cs -> {

            OracleConnection oracleConn = cs.getConnection().unwrap(OracleConnection.class);

            // --- PARÁMETROS IN ---
            cs.setInt(1, tipoDenuncia);

            // CORRECCIÓN: Usar setObject para manejar explícitamente los nulos
            if (esDeOficio) {
                cs.setNull(2, Types.VARCHAR);
                cs.setNull(3, Types.VARCHAR);
            } else {
                cs.setString(2, datosDenuncia.getDatosLlamada().getIdDenuncia());
                cs.setString(3, datosDenuncia.getDatosLlamada().getIdCaso());
            }

            Array datosGeneralesArray = esDeOficio
                    ? construirDatosGenerales(oracleConn)
                    : oracleConn.createOracleArray(ObjectosOracle.CR_DATOS_GENERALES.getValue(), new Struct[0]);
            cs.setObject(4, datosGeneralesArray, Types.ARRAY);

            Array hechosArray = construirHechos(datosDenuncia.getHechos(), oracleConn);
            cs.setObject(5, hechosArray, Types.ARRAY);

            Array partesArray = construirPartes(datosDenuncia.getPartesInvolucradas(), oracleConn);
            cs.setObject(6, partesArray, Types.ARRAY);

            Array delitosArray = construirDelitos(datosDenuncia.getDelitos().getDelitosAgregados(), oracleConn);
            cs.setObject(7, delitosArray, Types.ARRAY);

            Array detenidosArray = construirDetenidos(datosDenuncia.getDelitos().getDelitosAsociados(), oracleConn);
            cs.setObject(8, detenidosArray, Types.ARRAY);

            cs.setString(9, jwtTokenUtility.getUsuario().getCodEspecialidad());
            cs.setString(10, jwtTokenUtility.getUsuario().getUsuario());

            // --- PARÁMETROS OUT ---
            cs.registerOutParameter(11, Types.VARCHAR);      // PO_V_ID_CASO
            cs.registerOutParameter(12, Types.VARCHAR);      // PO_V_CO_CASO
            cs.registerOutParameter(13, Types.VARCHAR);      // PO_V_CO_ENTIDAD
            cs.registerOutParameter(14, Types.NUMERIC);      // PO_N_ID_TIPO_ENTIDAD
            cs.registerOutParameter(15, Types.TIMESTAMP);    // PO_V_FE_CREACION
            cs.registerOutParameter(16, Types.VARCHAR);      // PO_V_ERR_COD
            cs.registerOutParameter(17, Types.VARCHAR);      // PO_V_ERR_MSG

            cs.execute();

            String codigoError = cs.getString(16);
            String descripcionError = cs.getString(17);

            // Aquí está el problema. Si hay un error, el objeto 'out' de la llamada original es nulo.
            // Y si el error no es manejado, la lambda puede devolver una excepción que Spring no sabe traducir.
            // Vamos a verificar el error ANTES de intentar obtener los demás valores.
            if (codigoError != null) {
                if (errorUtil.esErrorGenerico(codigoError)) {
                    // Lanzar SQLException para que se propague correctamente
                    throw new SQLException("Error en SP: " + codigoError + " - " + descripcionError);
                }
                if (errorUtil.esErrorValidacionNegocio(codigoError)) {
                    throw new UnprocessableEntityException(codigoError, descripcionError);
                }
            }

            return new RespuestaRegistroDenuncia(
                    cs.getString(12), // PO_V_CO_CASO
                    cs.getString(11), // PO_V_ID_CASO
                    cs.getString(13), // PO_V_CO_ENTIDAD
                    String.valueOf(cs.getInt(14)), // PO_N_ID_TIPO_ENTIDAD
                    cs.getTimestamp(15) // PO_V_FE_CREACION
            );
        });
    }

    private Array construirDatosGenerales(OracleConnection conn) throws SQLException {
        Object[] objeto = new Object[]{
                1, // Tipo de documento (DNI)
                jwtTokenUtility.getUsuario().getInfo().getDni(),
                jwtTokenUtility.getUsuario().getInfo().getNombres(),
                jwtTokenUtility.getUsuario().getInfo().getApellidoPaterno(),
                jwtTokenUtility.getUsuario().getInfo().getApellidoMaterno(),
                jwtTokenUtility.getUsuario().getTelefono(),
                jwtTokenUtility.getUsuario().getCorreoFiscal(),
                jwtTokenUtility.getUsuario().getUsuario(),
                TipoSujeto.MINISTERIO_PUBLICO.getValue(),
                jwtTokenUtility.getUsuario().getCodDependencia(),
                jwtTokenUtility.getUsuario().getCodDespacho(),
                null,
                jwtTokenUtility.getUsuario().getCodDistritoFiscal()
        };
        Struct struct = conn.createStruct(ObjectosOracle.T_DATOS_GENERALES.getValue(), objeto);
        return conn.createOracleArray(ObjectosOracle.CR_DATOS_GENERALES.getValue(), new Struct[]{struct});
    }

    private Array construirHechos(HechosDto hechos, OracleConnection conn) throws SQLException {
        Object[] objeto = new Object[]{
                hechos.getUbigeo(),
                hechos.getIdTipoLugar(),
                hechos.getIdTipoVia(),
                hechos.getDireccion(),
                hechos.getNumeroDireccion(),
                hechos.getLatitud(),
                hechos.getLongitud(),
                new java.sql.Date(hechos.getFechaHecho().getTime()),
                hechos.getFechaDetencion() != null ? new java.sql.Date(hechos.getFechaDetencion().getTime()) : null,
                hechos.getIdTipoHecho(),
                crearClobFromString(conn, hechos.getDescripcion()),
                hechos.getIdFiscal(),
                hechos.getNumeroFallecidos(),
                hechos.getEncargado()
        };

        Struct struct = conn.createStruct(ObjectosOracle.T_HECHO.getValue(), objeto);
        return conn.createOracleArray(ObjectosOracle.CR_HECHOS.getValue(), new Struct[]{struct});
    }

    private Array construirDelitos(List<DelitoCasoDto> delitos, OracleConnection conn) throws SQLException {
        if (delitos == null || delitos.isEmpty()) {
            return conn.createOracleArray(ObjectosOracle.CR_DELITOS.getValue(), new Struct[0]);
        }
        Struct[] structs = new Struct[delitos.size()];
        for (int i = 0; i < delitos.size(); i++) {
            DelitoCasoDto d = delitos.get(i);
            Object[] obj = new Object[]{
                    d.getIdDelitoGenerico(),
                    d.getIdDelitoSubgenerico(),
                    d.getIdDelitoEspecifico(),
                    d.getIdGrado(),
                    d.getIdOrigen()
            };
            structs[i] = conn.createStruct(ObjectosOracle.T_DELITO.getValue(), obj);
        }
        return conn.createOracleArray(ObjectosOracle.CR_DELITOS.getValue(), structs);
    }

    private Array construirDetenidos(List<DelitoAsociadoDto> lista, OracleConnection conn) throws SQLException {
        if (lista == null || lista.isEmpty()) {
            return conn.createOracleArray(ObjectosOracle.CR_DETENIDOS.getValue(), new Struct[0]);
        }
        Struct[] structs = new Struct[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            DelitoAsociadoDto da = lista.get(i);
            Object[] objeto = new Object[]{
                    da.getDetenido().getIdTipoDocumento(),
                    da.getDetenido().getNumeroDocumento(),
                    da.getDetenido().getIdTipoParteSujeto(),
                    mapearDelitosAsociados(da.getDelitos(), conn)
            };
            structs[i] = conn.createStruct(ObjectosOracle.T_DETENIDO.getValue(), objeto);
        }
        return conn.createOracleArray(ObjectosOracle.CR_DETENIDOS.getValue(), structs);
    }

    private Array construirPartes(PartesInvolucradasDto partesDto, OracleConnection conn) throws SQLException {
        if (partesDto == null || (partesDto.getPersonasNaturales().isEmpty() && partesDto.getPersonasJuridicas().isEmpty())) {
            return conn.createOracleArray(ObjectosOracle.CR_PARTES.getValue(), new Struct[0]);
        }

        List<Struct> structList = new ArrayList<>();

        // Personas naturales
        for (PartePersonaNaturalDto info : partesDto.getPersonasNaturales()) {
            Array direcciones = mapearDirecciones(info.getDirecciones(), conn);
            Array datosContacto = mapearDatosContacto(
                    info.getCorreoPrimario(),
                    info.getCorreoSecundario(),
                    info.getTelefonoPrimario(),
                    info.getTelefonoSecundario(),
                    conn
            );

            String tipoAgraviado = info.getIdTipoParteSujeto() == 1 ? info.getTipoAgraviado() : null;

            Object[] objeto = new Object[]{
                    info.getIdTipoParteSujeto(),
                    info.getIdTipoPersona(),
                    tipoAgraviado,
                    info.getEsDenunciadoDesconocido(),
                    info.getIdTipoDocumento(),
                    info.getNumeroDocumento(),
                    info.getNombres(),
                    info.getApellidoPaterno(),
                    info.getApellidoMaterno(),
                    info.getFechaNacimiento() != null ? new java.sql.Date(info.getFechaNacimiento().getTime()) : null,
                    info.getEdad(),
                    info.getIdEstadoCivil(),
                    info.getIdGradoInstruccion(),
                    info.getIdTipoActividadLaboral(),
                    info.getOficioTexto(),
                    esNull(info.getSexo()) ? null : info.getSexo(),
                    idValido(info.getIdTipoLengua()),
                    info.getIdTipoNacionalidad(),
                    idValido(info.getIdTipoDiscapacidad()),
                    idValido(info.getIdTipoOrientacion()),
                    idValido(info.getIdTipoPueblo()),
                    info.getRequiereTraductor(),
                    info.getEsFallecido(),
                    info.getEsAfroperuano(),
                    info.getEsTrabajadorDelHogar(),
                    info.getEsVictimaViolencia8020(),
                    info.getConDiscapacidad(),
                    info.getEstaPrivadoLibertad(),
                    info.getCoinfeccionVihTbc(),
                    info.getEsMigrante(),
                    info.getEsLgtbi(),
                    info.getEsExtranjero(),
                    info.getEstaVerificado(),
                    info.getIdPais(),
                    info.getNombreAlias(),
                    info.getFotoSujeto() != null ? crearBlobFromString(conn, info.getFotoSujeto()) : null,
                    info.getFotoSujeto() != null ? Base64.getDecoder().decode(info.getFotoSujeto()).length : 0,
                    info.getNombreMadre(),
                    info.getApellidoPaternoMadre(),
                    info.getApellidoMaternoMadre(),
                    info.getNombrePadre(),
                    info.getApellidoPaternoPadre(),
                    info.getApellidoMaternoPadre(),
                    null, null, null, null, null, null, null, // espacio PJ
                    datosContacto,
                    info.getEsFuncionarioPublico(),
                    info.getEsDefensor(),
                    info.getEsMenorDeEdad(),
                    info.getEsProcurador(),
                    info.getIdTipoCondicionParte(),
                    direcciones,
                    info.getIdCargoFuncionarioPublico(),
                    info.getIdInstitucionPublica(),
                    info.getIdTipoDefensor(),
                    info.getEsCuentaAsociacionDefensora(),
                    info.getFechaValoracion(),
                    info.getFechaDetencion(),
                    info.getIdCargoAsociacion(),
                    info.getIdTipoViolencia(),
                    info.getIdFactorRiesgo(),
                    info.getObservacionesInvestigacion()
            };

            structList.add(conn.createStruct(ObjectosOracle.T_PARTE.getValue(), objeto));
        }

        // Personas jurídicas
        for (PartePersonaJuridicaDto info : partesDto.getPersonasJuridicas()) {
            Array direcciones = mapearDirecciones(info.getDirecciones(), conn);
            Array datosContacto = mapearDatosContacto(
                    info.getCorreoPrimario(),
                    info.getCorreoSecundario(),
                    info.getTelefonoPrimario(),
                    info.getTelefonoSecundario(),
                    conn
            );

            String tipoAgraviado;

            if (info.getRazonSocial().equalsIgnoreCase("el estado")) {
                tipoAgraviado = "E";
            } else if (info.getRazonSocial().equalsIgnoreCase("la sociedad")) {
                tipoAgraviado = "S";
            } else if (info.getIdTipoParteSujeto() == 1) {
                tipoAgraviado = info.getTipoAgraviado();
            } else {
                tipoAgraviado = null;
            }

            Object[] objeto = new Object[]{
                    info.getIdTipoParteSujeto(),
                    info.getIdTipoPersona(),
                    tipoAgraviado,
                    info.getEsDenunciadoDesconocido(),
                    info.getIdTipoDocumento(),
                    info.getNumeroDocumento(),
                    null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, // datos naturales
                    null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                    null, null, null, null, null, null, null, // foto, padres
                    info.getRazonSocial(),
                    info.getActividadEmpresarial(),
                    info.getNombreRepresentanteLegal(),
                    info.getApellidoPaternoRepresentanteLegal(),
                    info.getApellidoMaternoRepresentanteLegal(),
                    info.getIdTipoDocumentoRepresentanteLegal(),
                    info.getNumeroDocumentoRepresentanteLegal(),
                    datosContacto,
                    null, null, null, null, null,
                    direcciones,
                    null, null, null, null, null, null, null, null, null, null
            };

            structList.add(conn.createStruct(ObjectosOracle.T_PARTE.getValue(), objeto));
        }

        Struct[] array = structList.toArray(new Struct[0]);
        return conn.createOracleArray(ObjectosOracle.CR_PARTES.getValue(), array);
    }

    private Integer idValido(Integer id) {
        return (id != null && id != 0) ? id : null;
    }

    private static boolean esNull(String valor) {
        return Objects.equals(valor, "NaN") || Objects.equals(valor, "undefined") || Objects.equals(valor, "null");
    }

    private static Blob crearBlobFromString(Connection connection, String data) throws SQLException {
        byte[] bytes = data.getBytes();
        Blob blob = connection.createBlob();
        blob.setBytes(1, bytes);
        return blob;
    }

    private static Clob crearClobFromString(Connection connection, String data) throws SQLException {
        Clob clob = connection.createClob();
        clob.setString(1, data);
        return clob;
    }

    @Override
    public MovimientoRegistradoDto generarMovimientoCargoIngresoDenuncia(String idCaso) throws Exception {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(ObjectosOracle.SISMPA.getValue())
                .withCatalogName(ObjectosOracle.MPPK_REGISTRA_DENUNCIA_TURNO.getValue())
                .withProcedureName(ObjectosOracle.MPSP_REGISTRAR_MOVIMIENTO_INGRESO_DENUNCIA.getValue())
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PI_V_ID_CASO", OracleTypes.VARCHAR),
                        new SqlParameter("PI_V_CO_US_CREACION", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ID_MOVIMIENTO", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_D_FE_CREACION", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ERR_COD", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ERR_MSG", OracleTypes.VARCHAR)
                );

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("PI_V_ID_CASO", idCaso);
        inParams.put("PI_V_CO_US_CREACION", jwtTokenUtility.getUsuario().getUsuario());

        Map<String, Object> out = simpleJdbcCall.execute(inParams);
        String codigoError = stringUtil.toStr(out.get("PO_V_ERR_COD"));
        String descripcionError = stringUtil.toStr(out.get("PO_V_ERR_MSG"));
        String idMovimiento = stringUtil.toStr(out.get("PO_V_ID_MOVIMIENTO"));
        String fechaIngreso = stringUtil.toStr(out.get("PO_D_FE_CREACION"));

        if (errorUtil.esErrorGenerico(codigoError)) {
            throw new Exception(descripcionError);
        }

        if (errorUtil.esErrorValidacionNegocio(codigoError)) {
            throw new UnprocessableEntityException(codigoError, descripcionError);
        }

        MovimientoRegistradoDto movimientoRegistrado = new MovimientoRegistradoDto();

        movimientoRegistrado.setIdMovimiento(idMovimiento);
        movimientoRegistrado.setFechaIngreso(fechaIngreso);

        return movimientoRegistrado;
    }

    private Array mapearDirecciones(List<DireccionDto> lista, OracleConnection conn) {
        try {
            // CORRECCIÓN: Si la lista es nula o vacía, devuelve un array Oracle vacío y tipado.
            if (lista == null || lista.isEmpty()) {
                return conn.createOracleArray(ObjectosOracle.CR_DIRECCION.getValue(), new Struct[0]);
            }

            List<Struct> structs = new ArrayList<>();
            for (DireccionDto dir : lista) {
                Object[] objeto = {
                        UbigeoExtranjero.esExtranjero(dir.getUbigeo()),
                        dir.getIdTipoDomicilio(),
                        dir.getCodigoPostal(),
                        dir.getUbigeoPueblo(),
                        dir.getIdTipoVia(),
                        dir.getDireccionResidencia(),
                        dir.getNumeroResidencia(),
                        dir.getIdTipoUrbanizacion(),
                        dir.getUrbanizacion(),
                        dir.getBlockDireccion(),
                        dir.getInterior(),
                        dir.getEtapa(),
                        dir.getManzana(),
                        dir.getLote(),
                        dir.getReferencia(),
                        dir.getLatitud(),
                        dir.getLongitud()
                };
                structs.add(conn.createStruct(ObjectosOracle.T_DIRECCION.getValue(), objeto));
            }

            return conn.createOracleArray(ObjectosOracle.CR_DIRECCION.getValue(), structs.toArray(new Struct[0]));

        } catch (Exception e) {
            throw new DireccionMappingException("Error al mapear direcciones", e);
        }
    }

    private Array mapearDatosContacto(String correoPrimario, String correoSecundario, String telefonoPrimario, String telefonoSecundario, OracleConnection conn) {
        try {

            List<DatoContactoDto> lista = new ArrayList<>();

            if (esTextoValido(correoPrimario)) lista.add(new DatoContactoDto("1", correoPrimario, "0"));
            if (esTextoValido(correoSecundario)) lista.add(new DatoContactoDto("1", correoSecundario, "1"));
            if (esTextoValido(telefonoPrimario)) lista.add(new DatoContactoDto("2", telefonoPrimario, "0"));
            if (esTextoValido(telefonoSecundario)) lista.add(new DatoContactoDto("2", telefonoSecundario, "1"));

            // CORRECCIÓN: Si la lista está vacía, devuelve un array Oracle vacío y tipado.
            if (lista.isEmpty()) {
                return conn.createOracleArray(ObjectosOracle.CR_CONTACTO.getValue(), new Struct[0]);
            }

            List<Struct> structs = new ArrayList<>();
            for (DatoContactoDto contacto : lista) {
                Object[] objeto = {
                        contacto.getTipoContacto(),
                        contacto.getDatoContacto(),
                        contacto.getEsContactoSecundario()
                };
                structs.add(conn.createStruct(ObjectosOracle.T_CONTACTO.getValue(), objeto));
            }

            return conn.createOracleArray(ObjectosOracle.CR_CONTACTO.getValue(), structs.toArray(new Struct[0]));

        } catch (Exception e) {
            throw new DatosContactoMappingException("Error al mapear datos de contacto", e);
        }
    }

    private boolean esTextoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    private Array mapearDelitosAsociados(List<DelitoCasoDto> lista, OracleConnection conn) {
        try {
            // CORRECCIÓN: Si la lista es nula o vacía, devuelve un array Oracle vacío y tipado.
            if (lista == null || lista.isEmpty()) {
                return conn.createOracleArray(ObjectosOracle.CR_DELITOS_ASOCIADOS.getValue(), new Struct[0]);
            }

            List<Struct> structs = new ArrayList<>();
            for (DelitoCasoDto delito : lista) {
                Object[] objeto = {
                        delito.getIdDelitoGenerico(),
                        delito.getIdDelitoSubgenerico(),
                        delito.getIdDelitoEspecifico(),
                        delito.getIdGrado(),
                        delito.getIdOrigen()
                };
                structs.add(conn.createStruct(ObjectosOracle.T_DELITO.getValue(), objeto));
            }

            return conn.createOracleArray(ObjectosOracle.CR_DELITOS_ASOCIADOS.getValue(), structs.toArray(new Struct[0]));

        } catch (Exception e) {
            throw new DelitosAsociadosMappingException("Error al mapear delitos asociados", e);
        }
    }

    public Optional<DatosGeneralesDenunciaRecord> getDatosGeneralesDenuncia(String casoId, Usuario usuario) {

        List<ProcedureParameter> parameters = Arrays.asList(
                new ProcedureParameter("PI_V_ID_CASO", OracleTypes.VARCHAR, casoId),
                new ProcedureParameter("PO_CR_DATOS_GENERALES", OracleTypes.CURSOR, new DatosGeneralesDenunciaMapper(usuario))
        );

        Map<String, Object> result = executeStoreProcedure(
                ObjectosOracle.SISMPA.getValue(),
                ObjectosOracle.MPPK_DENUNCIA_TURNO.getValue(),
                ObjectosOracle.MPSP_RECUPERAR_DATOS_GENERALES.getValue(),
                parameters
        );

        @SuppressWarnings("unchecked")
        Optional<DatosGeneralesDenunciaRecord> datosGeneralesDenunciaRecord = ((List<DatosGeneralesDenunciaRecord>) result.get("PO_CR_DATOS_GENERALES")).stream().findFirst();

        return datosGeneralesDenunciaRecord;

    }

    @Override
    public DatosGeneralesOficioDto obtenerDatosGeneralesDenunciaOficio(String idCaso) throws Exception {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(ObjectosOracle.SISMPA.getValue())
                .withCatalogName(ObjectosOracle.MPPK_REGISTRA_DENUNCIA_TURNO.getValue())
                .withProcedureName(ObjectosOracle.MPSP_OBTENER_DATOS_GENERALES_DENUNCIA_OFICIO.getValue())
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PI_V_ID_CASO", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_CR_DATOS_GENERALES", OracleTypes.CURSOR, new DatosGeneralesOficioMapper()),
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

        List<DatosGeneralesOficioDto> datosGeneralesOficio = (List<DatosGeneralesOficioDto>) out.get("PO_CR_DATOS_GENERALES");

        return datosGeneralesOficio.size() > 0 ? datosGeneralesOficio.get(0) : null;
    }

    @Override
    public String obtenerEspecialidad(String especialidad) {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(ObjectosOracle.SISMPA.getValue())
                .withCatalogName(ObjectosOracle.MPPK_REGISTRA_DENUNCIA_TURNO.getValue())
                .withFunctionName(ObjectosOracle.MPFN_OBTENER_ESPECIALIDAD.getValue())
                .declareParameters(
                        new SqlParameter("PI_V_ID_ESPECIALIDAD", OracleTypes.VARCHAR),
                        new SqlOutParameter("RESULT", OracleTypes.VARCHAR)
                );

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("PI_V_ID_ESPECIALIDAD", especialidad);

        return simpleJdbcCall.executeFunction(String.class, inParams);

    }


    public class DireccionMappingException extends RuntimeException {
        public DireccionMappingException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public class DelitosAsociadosMappingException extends RuntimeException {
        public DelitosAsociadosMappingException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    public class DatosContactoMappingException extends RuntimeException {
        public DatosContactoMappingException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}