package pe.gob.mpfn.cfms.mesadeturno.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.mpfn.cfms.mesadeturno.dto.ActaDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.GuardarActaDto;
import pe.gob.mpfn.cfms.mesadeturno.service.DocumentoService;

import java.util.List;

@RestController
@RequestMapping("e/documentos")
@Tag(name = "Documentos")
public class DocumentoController {

    @Autowired
    DocumentoService documentoService;

    @GetMapping("/actas-registradas/{idCaso}")
    @Operation(summary = "Listar actas registradas en una denuncia de Mesa de Turno")
    public ResponseEntity<List<ActaDto>> listarActasRegistradas(@PathVariable String idCaso ) throws Exception {
        return documentoService.listarActasRegistradas(idCaso);
    }

    @GetMapping("/obtener/{idDocumento}")
    @Operation(summary = "Obtener documento de Acta Registrada en Mesa de Turno")
    public ResponseEntity<String> obtenerDocumentoPorId(@PathVariable String idDocumento) {
        return documentoService.obtenerDocumentoPorId(idDocumento);
    }

    @PostMapping("/guardar-acta")
    @Operation(summary = "Guardar Acta en Mesa de Turno")
    public ResponseEntity<String> guardarActa(@RequestBody GuardarActaDto datosActaPorGuardar) throws Exception {
        return documentoService.guardarActa(datosActaPorGuardar);
    }
}