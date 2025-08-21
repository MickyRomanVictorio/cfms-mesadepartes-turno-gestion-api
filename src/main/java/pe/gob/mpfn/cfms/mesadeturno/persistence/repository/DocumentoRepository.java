package pe.gob.mpfn.cfms.mesadeturno.persistence.repository;

import pe.gob.mpfn.cfms.mesadeturno.dto.ActaDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.CargoIngresoDenunciaDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.DocumentoDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.client.DocumentoActaDto;

import java.util.List;

public interface DocumentoRepository {

    List<ActaDto> listarActasRegistradas(String idCaso ) throws Exception ;
    DocumentoDto obtenerDocumentoPorId(String idDocumento, String token);
    String registrarMovimientoCaso(String idCaso, String usuarioCreaRegistro) throws Exception;
    String guardarDocumento(DocumentoActaDto actaPorSubir, String token) throws Exception;
    String obtenerCargoIngresoDenuncia(CargoIngresoDenunciaDto cargo, String token);
}