package pe.gob.mpfn.cfms.mesadeturno.rest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pe.gob.mpfn.cfms.mesadeturno.dto.ActaDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.GuardarActaDto;
import pe.gob.mpfn.cfms.mesadeturno.persistence.repository.DocumentoRepository;
import pe.gob.mpfn.cfms.mesadeturno.service.DocumentoService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DocumentoControllerTest {

    @Mock
    DocumentoService service;

    @Mock
    DocumentoRepository repository;

    @InjectMocks
    DocumentoController controller;

    private static String idCaso = "0BC47D5BF03155C2E0650250569D508A";

    private static String idDocumento = "0E11171757684C74E0650250569D508A";

    private static String archivo = "JVBERi0xLjQKJdPr6eEKMSAwIG9iago8PC9DcmVhdG9yIChNb3ppbGxhLzUuMCBcKFdpbmRvd3MgTlQgMTAuMDsgV2luNjQ7IHg2NFwpIEFwcGxlV";

    private static List<ActaDto> actasRegistradas;

    @BeforeAll
    static void configurarDatosPrueba(){
        actasRegistradas = new ArrayList<>();
        actasRegistradas.add(new ActaDto("0E10D3D40ED34B06E0650250569D508A", "ACTA DE DECLARACIÓN DEL TESTIGO-0004", "pdf", "Copia de copia"));
        actasRegistradas.add(new ActaDto("0E10D3D40ED44B06E0650250569D508A", "ACTA DE DECLARACIÓN DEL IMPUTADO-0005", "pdf", "Copia autenticada"));
        actasRegistradas.add(new ActaDto("0E11171757684C74E0650250569D508A", "ACTA DE DECLARACIÓN DEL AGRAVIADO-0006", "pdf", "Copia de copia"));
    }

    @Test
    void testListarActasRegistradas() throws Exception {

        List<ActaDto> respuestaServicio = actasRegistradas;

        when(service.listarActasRegistradas(anyString())).thenReturn( new ResponseEntity<>(respuestaServicio, HttpStatus.OK));

        ResponseEntity<List<ActaDto>> respuestaRest = null;

        respuestaRest = controller.listarActasRegistradas(idCaso);

        List<ActaDto> actasRegistradasObtenidos = Objects.requireNonNull(respuestaRest.getBody());
        ActaDto actaRegistrada = actasRegistradasObtenidos.get(0);

        assertSame(respuestaRest.getStatusCode(), HttpStatus.OK);
        assertNotNull(respuestaRest.getBody());
        assertTrue(respuestaRest.getBody().size() > 0);
        assertEquals(respuestaRest.getBody().size(), 3);
        assertEquals("ACTA DE DECLARACIÓN DEL TESTIGO-0004", actaRegistrada.getNombreDocumento());
        assertEquals("pdf", actaRegistrada.getExtensionDocumento());
        assertEquals("Copia de copia", actaRegistrada.getTipoDeCopia());
        verify(service, atMostOnce()).listarActasRegistradas(anyString());
        verify(service, times(1)).listarActasRegistradas(anyString());

    }

    @Test
    void testObtenerActaRegistrada() {

        String respuestaServicio = archivo;

        when(service.obtenerDocumentoPorId(anyString())).thenReturn( new ResponseEntity<>(respuestaServicio, HttpStatus.OK));

        ResponseEntity<String> respuestaRest = null;

        respuestaRest = controller.obtenerDocumentoPorId(idDocumento);

        String documentoObtenido = Objects.requireNonNull(respuestaRest.getBody());

        assertSame(respuestaRest.getStatusCode(), HttpStatus.OK);
        assertNotNull(respuestaRest.getBody());
        assertInstanceOf(String.class, documentoObtenido);
        assertEquals(archivo,documentoObtenido);
        verify(service, atMostOnce()).obtenerDocumentoPorId(anyString());
        verify(service, times(1)).obtenerDocumentoPorId(anyString());

    }

    @Test
    void testGuardarActa() throws Exception {

        GuardarActaDto actaPorGuardar = new GuardarActaDto();

        actaPorGuardar.setIdCaso(idCaso);
        actaPorGuardar.setNumeroDocumento("0001");
        actaPorGuardar.setContenidoActa("Contenido del acta");
        actaPorGuardar.setNombreArchivoOrigen("Acta de declaración de testigo.pdf");
        actaPorGuardar.setArchivo(archivo);
        actaPorGuardar.setNombreDocumento("ACTA DE DECLARACIÓN DEL TESTIGO-0001");
        actaPorGuardar.setIdTipoActa(341);
        actaPorGuardar.setIdTipoCopia(347);

        String respuestaServicio = idDocumento;

        when(service.guardarActa(any())).thenReturn( new ResponseEntity<>(respuestaServicio, HttpStatus.OK));

        ResponseEntity<String> respuestaRest = null;

        respuestaRest = controller.guardarActa(actaPorGuardar);

        String idDocumentoRegistrado = Objects.requireNonNull(respuestaRest.getBody());

        assertSame(respuestaRest.getStatusCode(), HttpStatus.OK);
        assertNotNull(respuestaRest.getBody());
        assertInstanceOf(String.class, idDocumentoRegistrado);
        assertEquals(idDocumento,idDocumentoRegistrado);
        verify(service, atMostOnce()).guardarActa(any());
        verify(service, times(1)).guardarActa(any());
        verify(repository, atMostOnce()).registrarMovimientoCaso(anyString(), anyString());

    }

}