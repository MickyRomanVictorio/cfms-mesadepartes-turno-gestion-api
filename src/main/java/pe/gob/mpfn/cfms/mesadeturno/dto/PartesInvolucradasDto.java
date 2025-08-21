package pe.gob.mpfn.cfms.mesadeturno.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PartesInvolucradasDto {

    private List<PartePersonaNaturalDto> personasNaturales;
    private List<PartePersonaJuridicaDto> personasJuridicas;

}