package pe.gob.mpfn.cfms.mesadeturno.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pe.gob.mpfn.cfms.generales.libreria.core.kafka.producer.KafkaProducerLibrary;
import pe.gob.mpfn.cfms.mesadeturno.client.almacenamiento.GeneralesAlmacenamientoClient;
import pe.gob.mpfn.cfms.mesadeturno.client.almacenamiento.RepositorioPublicoDTO;
import pe.gob.mpfn.cfms.mesadeturno.client.almacenamiento.RepositorioResponseDTO;
import pe.gob.mpfn.cfms.mesadeturno.common.util.JwtTokenUtility;
import pe.gob.mpfn.cfms.mesadeturno.dto.*;
import pe.gob.mpfn.cfms.mesadeturno.dto.kafka.TramaDenuncia;
import pe.gob.mpfn.cfms.mesadeturno.persistence.repository.DocumentoRepository;
import pe.gob.mpfn.cfms.mesadeturno.service.CargoService;
import pe.gob.mpfn.cfms.mesadeturno.util.ByteArrayMultipartFile;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import static pe.gob.mpfn.cfms.mesadeturno.util.Constantes.*;


@Slf4j
@Service
public class CargoServiceImpl implements CargoService {

    private final GeneralesAlmacenamientoClient generalesAlmacenamientoClient;
    private final KafkaProducerLibrary kafkaProducerLibrary;
    private final JwtTokenUtility jwtTokenUtility;
    private final DocumentoRepository documentoRepository;
    private final String topicoDenuncia;

    public CargoServiceImpl(GeneralesAlmacenamientoClient generalesAlmacenamientoClient,
                            KafkaProducerLibrary kafkaProducerLibrary,
                            JwtTokenUtility jwtTokenUtility,
                            DocumentoRepository documentoRepository,
                            @Value("${kafka.topic-denuncia}") String topicoDenuncia) {
        this.generalesAlmacenamientoClient = generalesAlmacenamientoClient;
        this.kafkaProducerLibrary = kafkaProducerLibrary;
        this.jwtTokenUtility = jwtTokenUtility;
        this.documentoRepository = documentoRepository;
        this.topicoDenuncia = topicoDenuncia;
    }

    private RepositorioPublicoDTO guardarCargodeB64aMultipart(String archivob64, String nombre) {
        byte[] pdfBytes = Base64.getDecoder().decode( archivob64 );
        MultipartFile multipartFile = new ByteArrayMultipartFile(pdfBytes, nombre, MediaType.APPLICATION_PDF_VALUE, nombre);
        RepositorioResponseDTO repositorioResponseDTO = generalesAlmacenamientoClient.guardarDocumento( multipartFile );
        return repositorioResponseDTO.getData();
    }

    @Override
    public ResponseDTO<String> procesarDataDenuncia(CompletarRegistrarDenunciaDto datosDenuncia, String idMovimiento, RespuestaRegistroDenuncia dbResponse) {

        //cargo
        String archivob64 = documentoRepository.obtenerCargoIngresoDenuncia(datosDenuncia.getCargo(), jwtTokenUtility.getToken());
        String codigoDocumento = NO_TIPO_DOCUMENTO_CARGO_INGRESO_DENUNCIA.concat(" ").concat(datosDenuncia.getCargo().getNumeroCaso());
        String nombreCargoOrigen = codigoDocumento.concat(EXTENSION_PDF);

        RepositorioPublicoDTO repositorioPublicoDTO = guardarCargodeB64aMultipart(archivob64, nombreCargoOrigen);
        String codigoUsuario = jwtTokenUtility.getPayload().getUsuario().getUsuario();
        TramaDenuncia tramaDenuncia = new TramaDenuncia();
        tramaDenuncia.setIdNode( repositorioPublicoDTO.getNodeId() );
        tramaDenuncia.setIdMovimientoCaso( idMovimiento );
        tramaDenuncia.setIdCaso( dbResponse.idCaso() );
        tramaDenuncia.setIdTipoDocumento( TIPO_DOCUMENTO_CARGO_INGRESO_DENUNCIA );
        tramaDenuncia.setIdTipoOrigen( ID_TIPO_ORIGEN );
        tramaDenuncia.setIdTipoCopia( null );
        tramaDenuncia.setCoDocumento( codigoDocumento );
        tramaDenuncia.setNoDocumentoOrigen( nombreCargoOrigen );
        tramaDenuncia.setNuFolios( repositorioPublicoDTO.getNumeroPaginas() );
        tramaDenuncia.setCoUsCreacion( codigoUsuario );
        tramaDenuncia.setFeEmision( dbResponse.fechaRegistro() );
        tramaDenuncia.setFeIngresoElemento( null );
        tramaDenuncia.setIdOaid( ID_OAID_CARGO_DENUNCIA );
        tramaDenuncia.setFlAmbito( FLAG_AMBITO_INTERNO );
        tramaDenuncia.setNuDocumento( dbResponse.coCaso() );
        tramaDenuncia.setCoEntidad( dbResponse.codigoEntidad() );
        tramaDenuncia.setIdTipoEntidad( Long.valueOf(dbResponse.idTipoEntidad()) );
        tramaDenuncia.setIdClasificacionDocumento( CLASIFICACION_DOCUMENTO_CARGO );
        tramaDenuncia.setIdEstadoElemento( null );
        tramaDenuncia.setObservaciones(null);
        tramaDenuncia.setCvd(datosDenuncia.getCargo().getCvd());
        CorreoDto correoDTO = new CorreoDto();
        if (TIPO_DENUNCIA_INVESTIGACION_OFICIO != datosDenuncia.getDatosLlamada().getTipoDenuncia()) {
            var datos = datosDenuncia.getDatosLlamada();
            Date fecha = datosDenuncia.getDatosLlamada().getFechaLlamada();
            SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoHora = new SimpleDateFormat("hh:mm a");
            String fechaComoString = formatoFecha.format(fecha);
            String horaComoString = formatoHora.format(fecha);
            String cuerpo = CORREO_BODY
                    .replace("NOMBRE_USUARIO", datos.getNombres())
                    .replace("dd/mm/aaaa", fechaComoString)
                    .replace("HH:mm", horaComoString)
                    .replace("NUMERO_CASO", dbResponse.coCaso())
                    .replace("FISCALIA", datos.getNombreFiscalia())
                    .replace("DESPACHO", datos.getNombreDespacho())
                    .replace("ENLACE", ENLACE);

            correoDTO.setDestinatario( datosDenuncia.getDatosLlamada().getCorreo() );
            correoDTO.setNombreAdjunto( nombreCargoOrigen );
            correoDTO.setCuerpo( cuerpo );
            correoDTO.setAsunto( CORREO_SUBJECT );
        }

        String[] procesos = { PROCESO_CARGO_FIRMA, PROCESO_CORREO };
        RegistrarDenunciaProducerDTO  registrarDenunciaProducerDTO = new RegistrarDenunciaProducerDTO();
        registrarDenunciaProducerDTO.setDenuncia( tramaDenuncia );
        registrarDenunciaProducerDTO.setProcesos( procesos );
        registrarDenunciaProducerDTO.setCorreo( correoDTO );
        log.info("data a producir {}", registrarDenunciaProducerDTO);
        kafkaProducerLibrary.produceMessageToTopic(topicoDenuncia, registrarDenunciaProducerDTO);

        ResponseDTO<String> responseDto= new ResponseDTO<>();
        responseDto.setData( repositorioPublicoDTO.getNodeId() );
        responseDto.setMensaje( nombreCargoOrigen );
        responseDto.setCodigo(HttpStatus.OK.value());

        return responseDto;
    }


}
