package pe.gob.mpfn.cfms.mesadeturno.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.mpfn.cfms.generales.libreria.core.dto.PaginacionWrapperDTO;
import pe.gob.mpfn.cfms.mesadeturno.dto.BuscarDelitosDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.DelitosDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.RegistrarDelitosDetenidosDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.TiposGradoDelitoDto;
import pe.gob.mpfn.cfms.mesadeturno.service.DelitoService;

import java.util.List;

@RestController
@RequestMapping("e/delitos")
@Tag(name = "Delitos")
public class DelitoController {

    @Autowired
    private DelitoService delitoService;
    private static final String BUSCAR_DELITO = "/buscar";
    private static final String AGREGAR_DELITOS = "/agregar";
    private static final String REGISTRAR_DETENIDO_DELITO = "/registrar-detenido";
    private static final String LISTAR_TIPOS_GRADO_DELITOS = "/tipos-grado";

    @GetMapping(LISTAR_TIPOS_GRADO_DELITOS)
    public ResponseEntity<List<TiposGradoDelitoDto>> listarTiposGradoDelitos() throws Exception {
        return delitoService.listarTiposGradoDelitos();
    }

    @PostMapping(BUSCAR_DELITO)
    public ResponseEntity<PaginacionWrapperDTO<DelitosDto>> buscarDelitos(@RequestBody BuscarDelitosDto buscarDelitosDto)
            throws Exception {
        return delitoService.buscarDelitos(buscarDelitosDto);
    }

    @PostMapping(AGREGAR_DELITOS)
    public ResponseEntity<String> agregarDelitos(@RequestBody RegistrarDelitosDetenidosDto registrarDelitosDetenidosDto)
            throws Exception {
        return delitoService.registrarDelitos(registrarDelitosDetenidosDto);
    }

    @PostMapping(REGISTRAR_DETENIDO_DELITO)
    public ResponseEntity<String> registrarDetenido(@RequestBody RegistrarDelitosDetenidosDto registrarDelitosDetenidosDto)
            throws Exception {
        return delitoService.registrarDetenidos(registrarDelitosDetenidosDto);
    }
}
