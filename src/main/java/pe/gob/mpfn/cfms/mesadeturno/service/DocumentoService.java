package pe.gob.mpfn.cfms.mesadeturno.service;

import org.springframework.http.ResponseEntity;
import pe.gob.mpfn.cfms.mesadeturno.dto.ActaDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.GuardarActaDto;

import java.util.List;

public interface DocumentoService {

    public ResponseEntity<List<ActaDto>> listarActasRegistradas(String idCaso) throws Exception;
    public ResponseEntity<String> obtenerDocumentoPorId(String id);
    public ResponseEntity<String> guardarActa(GuardarActaDto datosActaPorGuardar) throws Exception;
}