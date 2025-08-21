package pe.gob.mpfn.cfms.mesadeturno.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.gob.mpfn.cfms.mesadeturno.dto.DelitoDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.ImputadoRenadesppleDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.PersonaJuridicaDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.PersonaNaturalDto;
import pe.gob.mpfn.cfms.mesadeturno.service.DelitoPartesService;

import java.util.List;

@RestController
@RequestMapping("e/delitos-partes")
@Tag(name = "Delitos y Partes")
public class DelitoPartesController {

    @Autowired
    DelitoPartesService delitoPartesService;

    @GetMapping("/{idCaso}/delitos")
    @Operation(summary = "Listar delitos de una denuncia")
    public ResponseEntity<List<DelitoDto>> listarDelitos(@PathVariable String idCaso ) throws Exception {
        return delitoPartesService.listarDelitos(idCaso);
    }

    @GetMapping("/{idCaso}/personas-naturales")
    @Operation(summary = "Listar partes involucradas de tipo personas natural en una denuncia")
    public ResponseEntity<List<PersonaNaturalDto>> listarPersonasNaturales(@PathVariable String idCaso ) throws Exception {
        return delitoPartesService.listarPersonasNaturales(idCaso);
    }

    @GetMapping("/{idCaso}/personas-juridicas")
    @Operation(summary = "Listar partes involucradas de tipo personas jurídica en una denuncia")
    public ResponseEntity<List<PersonaJuridicaDto>> listarPersonasJuridicas(@PathVariable String idCaso ) throws Exception {
        return delitoPartesService.listarPersonasJuridicas(idCaso);
    }

    @GetMapping("/{idCaso}/imputados-renadespple")
    @Operation(summary = "Listar imputados de un caso para Ficha Renadespple")
    public ResponseEntity<List<ImputadoRenadesppleDto>> listarImputadosRenadespple(@PathVariable String idCaso ) throws Exception {
        return delitoPartesService.listarImputadosRenadespple(idCaso);
    }

}