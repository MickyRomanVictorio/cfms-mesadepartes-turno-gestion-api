package pe.gob.mpfn.cfms.mesadeturno.dto.client;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class DocumentoActaDto {
                private String archivoId;
                private String idMovimientoCaso;
                private String idCaso;
                private String idTipoDocumento;
                private String deSumilla;
                private int idTipoOrigen;
                private int idTipoCopia;
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
                private int idTipoActa;
}
