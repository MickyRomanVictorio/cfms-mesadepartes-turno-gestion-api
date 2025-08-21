package pe.gob.mpfn.cfms.mesadeturno.persistence.repository.impl;

import oracle.jdbc.OracleConnection;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import pe.gob.mpfn.cfms.generales.libreria.core.exception.NoControladoException;
import pe.gob.mpfn.cfms.generales.libreria.core.utils.StringUtil;
import pe.gob.mpfn.cfms.mesadeturno.dto.CompletarRegistrarDenunciaDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.PartePersonaJuridicaDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.PartePersonaNaturalDto;
import pe.gob.mpfn.cfms.mesadeturno.dto.denuncia.types.TypeDelitosDTO;
import pe.gob.mpfn.cfms.mesadeturno.dto.denuncia.types.TypeParticipantesDenunciaDuplicada;
import pe.gob.mpfn.cfms.mesadeturno.persistence.repository.DenunciaDuplicadaRepository;
import pe.gob.mpfn.cfms.mesadeturno.util.Constantes;

import javax.sql.DataSource;
import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DenunciaDuplicadaRepositoryImpl implements DenunciaDuplicadaRepository {
    private final JdbcTemplate jdbcTemplate;
    private final StringUtil stringUtil;
    public DenunciaDuplicadaRepositoryImpl(JdbcTemplate jdbcTemplate,
                                           StringUtil stringUtil){
        this.jdbcTemplate = jdbcTemplate;
        this.stringUtil = stringUtil;
    }

    @Override
    public Array prepararDatosParticipantesDenunciaDuplicada(CompletarRegistrarDenunciaDto denunciaDto) {
        Array datosParticipantesDenunciaDuplicada = null;

        DataSource dataSource = jdbcTemplate.getDataSource();
        if (dataSource == null) {
            throw new IllegalStateException("DataSource no está configurado");
        }

        try (Connection connectionMPE = dataSource.getConnection()) {

            List<TypeParticipantesDenunciaDuplicada> listaTypeDenunciaDuplicada = new ArrayList<>();

            agregarParticipantesNaturales(listaTypeDenunciaDuplicada,
                    denunciaDto.getPartesInvolucradas().getPersonasNaturales());

            agregarParticipantesJuridicas(listaTypeDenunciaDuplicada,
                    denunciaDto.getPartesInvolucradas().getPersonasJuridicas());

            if (connectionMPE.isWrapperFor(OracleConnection.class)) {
                OracleConnection oracleConnection = connectionMPE.unwrap(OracleConnection.class);
                datosParticipantesDenunciaDuplicada = crearArrayOracle(oracleConnection, listaTypeDenunciaDuplicada);
            }

        } catch (SQLException e) {
            throw new NoControladoException("Error al obtener datos prepararDatosParticipantesDenunciaDuplicada: " + e.getLocalizedMessage());
        }

        return datosParticipantesDenunciaDuplicada;
    }

    @Override
    public Array prepararDatosDelitos(CompletarRegistrarDenunciaDto denunciaDto) {
        Array datoDelito = null;

        DataSource dataSource = jdbcTemplate.getDataSource();
        if (dataSource == null) {
            throw new IllegalStateException("DataSource no está configurado");
        }

        try (Connection connectionMupDespacho = dataSource.getConnection()) {

            List<TypeDelitosDTO> listaTypeDelitos = new ArrayList<>();

            if (!stringUtil.isEmpty(denunciaDto.getDelitos().getDelitosAgregados())) {

                denunciaDto.getDelitos().getDelitosAgregados().forEach(delito -> {
                    TypeDelitosDTO typeDelitos = new TypeDelitosDTO();
                    typeDelitos.setCodDelito(delito.getIdDelitoGenerico());
                    typeDelitos.setCodDelitoSubgenerico(delito.getIdDelitoSubgenerico());
                    typeDelitos.setCodDelitoEspecifico(delito.getIdDelitoEspecifico());

                    listaTypeDelitos.add(typeDelitos);
                });

                if (connectionMupDespacho.isWrapperFor(OracleConnection.class)) {
                    OracleConnection oracleConnection = connectionMupDespacho.unwrap(OracleConnection.class);

                    Struct[] structTypeDelitos = new Struct[listaTypeDelitos.size()];
                    for (int i = 0; i < listaTypeDelitos.size(); i++) {
                        TypeDelitosDTO typeDelitosDTO = listaTypeDelitos.get(i);
                        Object[] objDelito = new Object[]{
                                typeDelitosDTO.getCodDelito(),
                                typeDelitosDTO.getCodDelitoSubgenerico(),
                                typeDelitosDTO.getCodDelitoEspecifico()
                        };
                        structTypeDelitos[i] = oracleConnection.createStruct(Constantes.TYPE_DELITO, objDelito);
                    }
                    datoDelito = oracleConnection.createOracleArray(Constantes.DELITO, structTypeDelitos);
                }
            }

        } catch (SQLException e) {
            throw new NoControladoException("Error al obtener datos de delitos: " + e.getLocalizedMessage());
        }

        return datoDelito;
    }

    private void agregarParticipantesNaturales(List<TypeParticipantesDenunciaDuplicada> lista, List<PartePersonaNaturalDto> partes) {
        if (partes != null) {
            partes.forEach(persona -> {
                TypeParticipantesDenunciaDuplicada participante = new TypeParticipantesDenunciaDuplicada();
                participante.setIdTtipoParteSujeto(Long.valueOf(persona.getIdTipoParteSujeto()));
                participante.setIdTipoDocIdent(Long.valueOf(persona.getIdTipoDocumento()));
                participante.setNuDocumento(persona.getNumeroDocumento() != null ? persona.getNumeroDocumento() : "");
                lista.add(participante);
            });
        }
    }

    private void agregarParticipantesJuridicas(List<TypeParticipantesDenunciaDuplicada> lista, List<PartePersonaJuridicaDto> partes) {
        if (partes != null) {
            partes.forEach(persona -> {
                TypeParticipantesDenunciaDuplicada participante = new TypeParticipantesDenunciaDuplicada();
                participante.setIdTtipoParteSujeto(Long.valueOf(persona.getIdTipoParteSujeto()));
                participante.setIdTipoDocIdent(Long.valueOf(persona.getIdTipoDocumento()));
                participante.setNuDocumento(persona.getNumeroDocumento() != null ? persona.getNumeroDocumento() : "");
                lista.add(participante);
            });
        }
    }

    private Array crearArrayOracle(OracleConnection oracleConnection, List<TypeParticipantesDenunciaDuplicada> lista) throws SQLException {
        Struct[] structs = new Struct[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            TypeParticipantesDenunciaDuplicada participante = lista.get(i);
            Object[] obj = new Object[]{
                    participante.getIdTipoDocIdent(),
                    participante.getNuDocumento(),
                    participante.getIdTtipoParteSujeto()
            };
            structs[i] = oracleConnection.createStruct(Constantes.TYPE_PARTICIPANTES_DENUNCIA_DUPLICADA, obj);
        }
        return oracleConnection.createOracleArray(Constantes.PARTICIPANTES_DENUNCIA_DUPLICADA, structs);
    }

}
