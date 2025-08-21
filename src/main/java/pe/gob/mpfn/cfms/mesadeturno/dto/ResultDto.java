package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResultDto {
    private String numeroCaso;
    private String distritoFiscal;
    private String especialidad;
    private String fiscalia;
    private String despacho;
    private String fechaIngreso;
    private String motivoIngreso;
    private String efectivoPolicial;
    private String dependenciaPolicial;
    private String fechaHoraLlamada;
    private String fiscalAsignado;
    private String fechaHecho;
    private String fechaDetencion;
    private String fechaDefuncion;
    private List<DelitoResultDto> delitos;
    private long cantidadFallecidos;
    private String levantamiento;
    private String descripcionHecho;
    private List<ParteResultDto> partes;
}
