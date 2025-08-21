package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CargoIngresoDenunciaDto {

    private String numeroCaso;
    private String nroCaso;
    private String distritoFiscal;
    private String especialidad;
    private String fiscalia;
    private String despacho;
    private String fechaIngreso;
    private String motivoIngreso;
    private String tipoDenuncia;
    private String efectivoPolicial;
    private String dependenciaPolicial;
    private String fechaHoraLlamada;
    private String fiscalAsignado;
    private String fechaHecho;
    private String fechaDetencion;
    private String cantidadFallecidos;
    private String levantamiento;
    private List<DelitoIngresoDenunciaDto> delitos;
    private String descripcionHecho;
    private List<PartesIngresoDenunciaDto> partes;
    private boolean duplicada;
    private String cvd;

}