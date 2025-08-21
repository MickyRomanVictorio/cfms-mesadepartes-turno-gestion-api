package pe.gob.mpfn.cfms.mesadeturno.service;

import pe.gob.mpfn.cfms.mesadeturno.dto.CompletarRegistrarDenunciaDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.ResponseDTO;
import pe.gob.mpfn.cfms.mesadeturno.dto.RespuestaRegistroDenuncia;

public interface CargoService {

    ResponseDTO<String> procesarDataDenuncia(CompletarRegistrarDenunciaDto datosDenuncia, String idMovimiento, RespuestaRegistroDenuncia dbResponse);
}
