package pe.gob.mpfn.cfms.mesadeturno.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.mpfn.cfms.generales.libreria.core.dto.PaginacionWrapperDTO;
import pe.gob.mpfn.cfms.mesadeturno.dto.*;
import pe.gob.mpfn.cfms.mesadeturno.dto.partesinvolucradas.DatosGeneralesDenunciaRecord;
import pe.gob.mpfn.cfms.mesadeturno.dto.response.DatosGeneralesResponse;
import pe.gob.mpfn.cfms.mesadeturno.service.DenunciaService;

@RestController
@RequestMapping("e/denuncias")
@Tag(name = "Denuncia")
public class DenunciaController {

    private final DenunciaService denunciaService;

    public DenunciaController(DenunciaService denunciaService) {
        this.denunciaService = denunciaService;
    }

    @PostMapping("")
    @Operation(summary = "Listar denuncias registradas")
    public ResponseEntity<PaginacionWrapperDTO<DenunciaRegistradaDto>> listarDenunciasRegistradas(@RequestBody ListarDenunciasDto listarDenunciasDto) throws Exception {
        return denunciaService.listarDenunciasRegistradas(listarDenunciasDto);
    }

    @PostMapping("/datos-generales")
    @Operation(summary = "Registra los datos generales de la denuncia")
    public DatosGeneralesResponse registrarDatosGenerales(@RequestBody DatosGeneralesDto datosGeneralesDto) throws Exception {
        return denunciaService.registrarDatosGenerales(datosGeneralesDto);
    }

    @GetMapping("/validar-turno")
    @Operation(summary = "Validar si la mesa del usuario en sesión se encuentra de turno")
    public String validarSiMesaEstaDeTurno() {
        return denunciaService.validarSiMesaEstaDeTurno();
    }

    @PostMapping(value = "/duplicidadDenuncia")
    @Operation(summary = "Validar duplicidad de registro de denuncia")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseDTO<String>> identificarDenunciaDuplicada(@RequestBody CompletarRegistrarDenunciaDto datosDenuncia) {
        return new ResponseEntity<>(denunciaService.identificarDenunciaDuplicada(datosDenuncia), HttpStatus.CREATED);
    }

    @PostMapping("/tipo/{tipoDenuncia}/completar-registrar")
    @Operation(summary = "Completar o Registrar Denuncia en Mesa de Turno")
    public ResponseEntity<String> completarRegistrarDenuncia(@PathVariable Integer tipoDenuncia, @RequestBody CompletarRegistrarDenunciaDto datosDenuncia) throws Exception {
        return denunciaService.completarRegistrarDenuncia(tipoDenuncia, datosDenuncia);
    }

    @GetMapping("/{casoId}")
    public DatosGeneralesDenunciaRecord obtenerDenuncia(@PathVariable("casoId") String casoId) {
        return denunciaService.obtenerPorCasoId(casoId);
    }

    @GetMapping("/usuario/especialidad")
    @Operation(summary = "Obtiene la descripción de la especialidad del usuario en sesión")
    public ResponseEntity<String> obtenerEspecialidad() throws Exception {
        return denunciaService.obtenerEspecialidad();
    }

}