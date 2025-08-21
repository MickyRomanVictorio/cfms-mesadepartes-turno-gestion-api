package pe.gob.mpfn.cfms.mesadeturno.dto.kafka;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class TramaDenuncia {
    private String idNode;
    private String idMovimientoCaso;
    private String idCaso;
    private String idTipoDocumento;
    private Long idTipoOrigen;
    private Long idTipoCopia;
    private String coDocumento;
    private String noDocumentoOrigen;
    private Long nuFolios;
    private String coUsCreacion;
    private Date feEmision;
    private Date feIngresoElemento;
    private Long idOaid;
    private String flMedidasProteccion;
    private String flAmbito;
    private String nuDocumento;
    private String coEntidad;
    private Long idTipoEntidad;
    private Long idClasificacionDocumento;
    private Long idEstadoElemento;
    private String observaciones;
    private String cvd;
}
