package pe.gob.mpfn.cfms.mesadeturno.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.gob.mpfn.cfms.mesadeturno.dto.*;
import pe.gob.mpfn.cfms.mesadeturno.dto.informaciongeneral.DireccionInfoDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.renadespple.OperacionFichaRequest;
import pe.gob.mpfn.cfms.mesadeturno.persistence.repository.RenadesppleRepository;
import pe.gob.mpfn.cfms.mesadeturno.service.RenadesppleService;

import java.util.List;

@Service
public class RenadesppleServiceImpl implements RenadesppleService {

    @Autowired
    private RenadesppleRepository renadesppleRepository;

    @Override
    public ResponseEntity<List<TiposGradoDelitoDto>> listarOficinasRenadespple() throws Exception {
        return new ResponseEntity<>(renadesppleRepository.listarOficinasRenadespple(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<FichaRenadesppleDto> obtenerFichaRenadespple(String idCaso, String idCasoSujeto) throws Exception {
        List<FichaRenadesppleDto> fichas = renadesppleRepository.obtenerFichaRenadespple(idCaso, idCasoSujeto);
        return new ResponseEntity<>(fichas.isEmpty() ? null : fichas.get(0), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ItemLista>> listarMotivosDetencion() throws Exception {
        return new ResponseEntity<>(renadesppleRepository.listarMotivosDetencion(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ItemLista>> listarSituacionesJuridicas() throws Exception {
        return new ResponseEntity<>(renadesppleRepository.listarSituacionesJuridicas(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<DireccionInfoDto>> listarDireccionesInformacionGeneral(String idSujetoCaso) throws Exception {
        return new ResponseEntity<>(renadesppleRepository.listarDireccionesInformacionGeneral(idSujetoCaso), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> eliminarDireccionInformacionGeneral(String idDireccion) throws Exception {
        return new ResponseEntity<>(renadesppleRepository.eliminarDireccionInfo(idDireccion), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> operacionDireccionInformacionGeneral(DireccionInfoDto direccionInfoDto) throws Exception {
        return new ResponseEntity<>(renadesppleRepository.operacionDireccionInfo(direccionInfoDto), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> actualizarFichaRenadespple(OperacionFichaRequest operacionFichaRequest) throws Exception {
        return new ResponseEntity<>(renadesppleRepository.actualizarFichaRenadespple(operacionFichaRequest), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MomentoDetencionDto> obtenerMomentoDetencion(String idCaso) throws Exception {
        MomentoDetencionDto momento = renadesppleRepository.obtenerMomentoDetencion(idCaso);
        return new ResponseEntity<>(momento, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<InvestigacionPolicialDto> obtenerInvestigacionPolicial(String idSujetoCaso) throws Exception {
        InvestigacionPolicialDto investigacionPolicialDto = renadesppleRepository.obtenerInvestigacionPolicial(idSujetoCaso);
        return new ResponseEntity<>(investigacionPolicialDto, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<DatosReniecDto> obtenerDatosReniec(String idSujetoCaso) throws Exception {
        return new ResponseEntity<>(renadesppleRepository.obtenerDatosReniec(idSujetoCaso), HttpStatus.OK);
    }

}