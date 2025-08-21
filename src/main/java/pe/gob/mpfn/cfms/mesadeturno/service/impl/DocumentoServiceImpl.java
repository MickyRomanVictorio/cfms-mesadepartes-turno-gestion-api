package pe.gob.mpfn.cfms.mesadeturno.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.gob.mpfn.cfms.mesadeturno.common.util.JwtTokenUtility;
import pe.gob.mpfn.cfms.mesadeturno.dto.ActaDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.GuardarActaDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.client.DocumentoActaDto;
import pe.gob.mpfn.cfms.mesadeturno.persistence.repository.DocumentoRepository;
import pe.gob.mpfn.cfms.mesadeturno.service.DocumentoService;
import pe.gob.mpfn.cfms.mesadeturno.util.Constantes;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentoServiceImpl implements DocumentoService {

    @Autowired
    DocumentoRepository documentoRepository;

    @Autowired
    private JwtTokenUtility jwtTokenUtility;

    @Override
    public ResponseEntity<List<ActaDto>> listarActasRegistradas(String idCaso) throws Exception {
        return new ResponseEntity<>(documentoRepository.listarActasRegistradas(idCaso), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> obtenerDocumentoPorId(String id) {
        var documentoResponse = documentoRepository.obtenerDocumentoPorId(id, jwtTokenUtility.getToken());
        return new ResponseEntity<>(
                documentoResponse.getData().stream().findFirst().orElseThrow(() ->
                        new RuntimeException("Error al recuperar archivo ")).getArchivo(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> guardarActa(GuardarActaDto datosActaPorGuardar) throws Exception {

        String idMovimiento = documentoRepository.registrarMovimientoCaso(
                datosActaPorGuardar.getIdCaso(),
                jwtTokenUtility.getUsuario().getUsuario()
        );


        var nuDocumento = Optional.ofNullable(datosActaPorGuardar.getNumeroDocumento()).orElse("S/N");


        var acta = DocumentoActaDto
                .builder()
                .idCaso(datosActaPorGuardar.getIdCaso())
                .idMovimientoCaso(idMovimiento)
                .archivoId(datosActaPorGuardar.getArchivo())
                .idTipoDocumento(Constantes.REPOSITORIO_TIPO_DOCUMENTO_ACTA)
                .noDocumentoOrigen(datosActaPorGuardar.getNombreArchivoOrigen())
                .deSumilla(datosActaPorGuardar.getContenidoActa())
                .coDocumento(datosActaPorGuardar.getNombreDocumento() + nuDocumento)
                .idTipoOrigen(Constantes.REPOSITORIO_TIPO_ORIGEN)
                .coUsCreacion(jwtTokenUtility.getUsuario().getUsuario())
                .feEmision(new Date())
                .idOaid(Constantes.ID_OAID_DOCUMENTO_PRINCIPAL)
                .idTipoActa(datosActaPorGuardar.getIdTipoActa())
                .idTipoCopia(datosActaPorGuardar.getIdTipoCopia())
                .nuDocumento(nuDocumento)
                .build();

        String idDocumento = documentoRepository.guardarDocumento(acta, jwtTokenUtility.getToken());

        return new ResponseEntity<>(idDocumento, HttpStatus.OK);

    }
}