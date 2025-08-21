package pe.gob.mpfn.cfms.mesadeturno.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.mpfn.cfms.mesadeturno.dto.*;
import pe.gob.mpfn.cfms.mesadeturno.dto.informaciongeneral.DireccionInfoDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.renadespple.OperacionFichaRequest;
import pe.gob.mpfn.cfms.mesadeturno.service.RenadesppleService;

import java.util.List;

@RestController
@RequestMapping("e/renadespple")
@Tag(name = "Renadespple")
public class RenadesppleController {

    @Autowired
    private RenadesppleService renadesppleService;

    private static final String FICHA = "/obtenerFicha";
    private static final String OFICINAS = "/oficinas";
    private static final String LISTAR_DIRECCIONES = "/direcciones/{idSujetoCaso}";
    private static final String LISTAR_MOTIVOS_DETENCION = "/motivosDetencion";
    private static final String LISTAR_SITUACIONES_JURIDICAS = "/situacionesJuridicas";
    private static final String MOMENTO_DETENCION = "/momentoDetencion/{idCaso}";
    private static final String INVESTIGACION_POLICIAL = "/investigacionPolicial/{idSujetoCaso}";
    private static final String ELIMINAR_DIRECCION = "/direccion/{idDireccion}";
    private static final String OPERACION_DIRECCION = "/direccion";
    private static final String OPERACION_FICHA_RENADESPPLE = "/ficha";
    private static final String CONSULTAR_PERSONA = "/consultarPersona/{idSujetoCaso}";

    @GetMapping(FICHA)
    @Operation(summary = "Obtener Ficha Renadespple")
    public ResponseEntity<FichaRenadesppleDto> obtenerFichaRenadespple(@RequestParam("idCaso") String idCaso,
                                                                       @RequestParam("idSujetoCaso") String idSujetoCaso
    ) throws Exception {
        return renadesppleService.obtenerFichaRenadespple(idCaso, idSujetoCaso);
    }

    @GetMapping(OFICINAS)
    @Operation(summary = "Listar Oficinas Renadespple")
    public ResponseEntity<List<TiposGradoDelitoDto>> listarOficinasRenadespple() throws Exception {
        return renadesppleService.listarOficinasRenadespple();
    }

    @GetMapping(LISTAR_DIRECCIONES)
    @Operation(summary = "Listar direcciones en Información General")
    public ResponseEntity<List<DireccionInfoDto>> listarDireccionesInfo(@PathVariable String idSujetoCaso) throws Exception {
        return renadesppleService.listarDireccionesInformacionGeneral(idSujetoCaso);
    }

    @GetMapping(LISTAR_MOTIVOS_DETENCION)
    @Operation(summary = "Listar Motivos Detención en Momento Detención")
    public ResponseEntity<List<ItemLista>> listarMotivosDetencion() throws Exception {
        return renadesppleService.listarMotivosDetencion();
    }

    @GetMapping(MOMENTO_DETENCION)
    @Operation(summary = "Obtener el Momento Detención")
    public ResponseEntity<MomentoDetencionDto> obtenerMomentoDetencion(@PathVariable String idCaso) throws Exception {
        return renadesppleService.obtenerMomentoDetencion(idCaso);
    }

    @GetMapping(INVESTIGACION_POLICIAL)
    @Operation(summary = "Obtener la data de Investigación Policial")
    public ResponseEntity<InvestigacionPolicialDto> obtenerInvestigacionPolicial(@PathVariable String idSujetoCaso) throws Exception {
        return renadesppleService.obtenerInvestigacionPolicial(idSujetoCaso);
    }

    @PutMapping(ELIMINAR_DIRECCION)
    @Operation(summary = "Eliminar direccion en Informacion General")
    public ResponseEntity<String> eliminarDireccionInfo(@PathVariable String idDireccion) throws Exception {
        return renadesppleService.eliminarDireccionInformacionGeneral(idDireccion);
    }

    @PostMapping(OPERACION_DIRECCION)
    @Operation(summary = "Registrar / Actualizar dirección en Información General")
    public ResponseEntity<String> operacionDireccionInfo(@RequestBody DireccionInfoDto direccionInfoDto) throws Exception {
        return renadesppleService.operacionDireccionInformacionGeneral(direccionInfoDto);
    }

    @GetMapping(LISTAR_SITUACIONES_JURIDICAS)
    @Operation(summary = "Listar Situaciones Juridicas")
    public ResponseEntity<List<ItemLista>> listarSituacionesJuridicas() throws Exception {
        return renadesppleService.listarSituacionesJuridicas();
    }

    @PostMapping(OPERACION_FICHA_RENADESPPLE)
    @Operation(summary = "Registrar / Actualizar Ficha Renadespple")
    public ResponseEntity<String> operacionFichaRenadespple(@RequestBody OperacionFichaRequest operacionFichaRequest) throws Exception {
        return renadesppleService.actualizarFichaRenadespple(operacionFichaRequest);
    }

    @GetMapping(CONSULTAR_PERSONA)
    public ResponseEntity<DatosReniecDto> obtenerDatosReniec(@PathVariable String idSujetoCaso) throws Exception {
        return renadesppleService.obtenerDatosReniec(idSujetoCaso);
    }
}
