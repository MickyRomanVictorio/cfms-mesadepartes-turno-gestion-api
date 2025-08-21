package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CompletarRegistrarDenunciaDto {

    private DatosLlamadaDto datosLlamada;
    private HechosDto hechos;
    private PartesInvolucradasDto partesInvolucradas;
    private DelitosCasoDto delitos;
    private CargoIngresoDenunciaDto cargo;

}