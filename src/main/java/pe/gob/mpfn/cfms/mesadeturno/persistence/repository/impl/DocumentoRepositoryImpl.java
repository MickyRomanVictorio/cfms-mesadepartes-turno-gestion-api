package pe.gob.mpfn.cfms.mesadeturno.persistence.repository.impl;

import lombok.extern.slf4j.Slf4j;
import oracle.jdbc.OracleTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;
import pe.gob.mpfn.cfms.generales.libreria.core.exception.UnprocessableEntityException;
import pe.gob.mpfn.cfms.generales.libreria.core.utils.ErrorUtil;
import pe.gob.mpfn.cfms.generales.libreria.core.utils.StringUtil;
import pe.gob.mpfn.cfms.mesadeturno.dto.ActaDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.ArchivoDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.CargoIngresoDenunciaDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.DocumentoDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.client.DocumentoActaDto;
import pe.gob.mpfn.cfms.mesadeturno.enums.ObjectosOracle;
import pe.gob.mpfn.cfms.mesadeturno.persistence.mapper.ActaMapper;
import pe.gob.mpfn.cfms.mesadeturno.persistence.repository.DocumentoRepository;

import java.util.*;

@Repository
@Slf4j
public class DocumentoRepositoryImpl implements DocumentoRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StringUtil stringUtil;

    @Autowired
    private ErrorUtil errorUtil;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${clients.gestionDocumentos}")
    private String gestionDocumentoEndpoint;

    @Value("${clients.generalesAlmacenamiento}")
    private String generalesAlmacenamiento;

    @Value("${clients.gestionDocumentoMesaDePartes}")
    private String gestionDocumentoMesaDePartesEndpoint;

    @Override
    public List<ActaDto> listarActasRegistradas( String idCaso ) throws Exception {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(ObjectosOracle.SISMPA.getValue())
                .withCatalogName(ObjectosOracle.MPPK_DOCUMENTO_CASO_TURNO.getValue())
                .withProcedureName(ObjectosOracle.MPSP_LISTAR_ACTAS_REGISTRADAS.getValue())
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PI_V_ID_CASO", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_CR_ACTAS_REGISTRADAS", OracleTypes.CURSOR, new ActaMapper()),
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

        return (List<ActaDto>) out.get("PO_CR_ACTAS_REGISTRADAS");
    }

    @Override
    public DocumentoDto obtenerDocumentoPorId(String idDocumento, String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setBearerAuth(token);

        var url = "%s/obtienearchivo/%s".formatted(gestionDocumentoEndpoint, idDocumento);
        log.info("url {}", url);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity< byte[]> responseEntity = restTemplate
                .exchange(
                        url,
                        HttpMethod.GET,
                        entity,
                        byte[].class
                );

        // Check the response status code.
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.error("Falla al enviar documento " + idDocumento+ " respuesta: " + responseEntity.getStatusCode());
            throw new RuntimeException("Error al recuperar archivo " + idDocumento);
        }

        var d = new DocumentoDto();
        var a = new ArchivoDto();
        a.setArchivo(Base64.getEncoder().encodeToString(responseEntity.getBody()));
        d.setData(List.of(a));
        return d;
    }

    @Override
    public String registrarMovimientoCaso(String idCaso, String usuarioCreaRegistro) throws Exception {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withSchemaName(ObjectosOracle.SISMPA.getValue())
                .withCatalogName(ObjectosOracle.MPPK_DOCUMENTO_CASO_TURNO.getValue())
                .withProcedureName(ObjectosOracle.MPSP_REGISTRAR_MOVIMIENTO.getValue())
                .withoutProcedureColumnMetaDataAccess()
                .declareParameters(
                        new SqlParameter("PI_V_ID_CASO", OracleTypes.VARCHAR),
                        new SqlParameter("PI_V_CO_US_CREACION", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ID_MOVIMIENTO", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ERR_COD", OracleTypes.VARCHAR),
                        new SqlOutParameter("PO_V_ERR_MSG", OracleTypes.VARCHAR)
                );

        Map<String, Object> inParams = new HashMap<>();
        inParams.put("PI_V_ID_CASO", idCaso);
        inParams.put("PI_V_CO_US_CREACION", usuarioCreaRegistro);

        Map<String, Object> out = simpleJdbcCall.execute(inParams);
        String codigoError = stringUtil.toStr(out.get("PO_V_ERR_COD"));
        String descripcionError = stringUtil.toStr(out.get("PO_V_ERR_MSG"));

        if (errorUtil.esErrorGenerico(codigoError)) {
            throw new Exception(descripcionError);
        }

        if (errorUtil.esErrorValidacionNegocio(codigoError)) {
            throw new UnprocessableEntityException(codigoError, descripcionError);
        }

        return (String) out.get("PO_V_ID_MOVIMIENTO");
    }

    public String guardarDocumento(DocumentoActaDto actaPorSubir, String token) throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        var requestEntity = new HttpEntity<>(actaPorSubir, headers);
        var url = "%s/registraarchivofirmado".formatted(gestionDocumentoEndpoint);
        log.info(url);
        ResponseEntity<Object> responseEntity = restTemplate.postForEntity(url, requestEntity, Object.class);

        // Check the response status code.
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Error al intentar grabar archivo!");
        }

        String idDocumento = ((LinkedHashMap<Object,Object>)responseEntity.getBody()).get("idDocumento").toString();

        return idDocumento;
    }

    @Override
    public String obtenerCargoIngresoDenuncia(CargoIngresoDenunciaDto cargo, String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        var requestEntity = new HttpEntity<>(cargo, headers);
        var url = "%s/cargo-ingreso-denuncia".formatted(gestionDocumentoMesaDePartesEndpoint);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);

        // Check the response status code.
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("Error al intentar obtener cargo de ingreso de denuncia");
        }

        String cargoIngresoDenunciaB64 = (String) responseEntity.getBody();

        return cargoIngresoDenunciaB64;
    }

}