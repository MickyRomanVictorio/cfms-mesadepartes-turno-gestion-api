package pe.gob.mpfn.cfms.mesadeturno.dto.partesinvolucradas;

import java.io.Serializable;
import java.time.LocalDateTime;

public record DatosGeneralesDenunciaRecord(
        String idCaso,
        String idDenuncia,
        String numeroCaso,
        String correo,
        String numeroContacto,//revisar antes telefono
        Integer idDependenciaPolicial,
        String nombreDependenciaPolicial,
        LocalDateTime fechaLlamada,
        LocalDateTime horaLlamada,//revisar
        Integer idTipoDocumentoIdentidad,
        String tipoDocumentoIdentidad,
        String numeroDocumentoIdentidad,//revisar
        String nombreCiudadano,
        String apellidoPaterno,
        String apellidoMaterno,
        Boolean esVerificado,
        String idDistritoFiscal,//revisar
        String nombreDistritoFiscal,
        String idEspecialidad,
        String nombreEspecialidad,
        String idFiscalia,
        String nombreFiscalia,
        String idDespacho,
        String nombreDespacho,
        Integer tipoDenuncia,
        String fechaRegistroCaso
) implements Serializable {}

