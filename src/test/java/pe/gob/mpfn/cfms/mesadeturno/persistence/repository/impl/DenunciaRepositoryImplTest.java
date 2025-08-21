package pe.gob.mpfn.cfms.mesadeturno.persistence.repository.impl;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pe.gob.mpfn.cfms.mesadeturno.bean.Usuario;
import pe.gob.mpfn.cfms.mesadeturno.dto.DatosGeneralesDto;
import pe.gob.mpfn.cfms.mesadeturno.enums.TipoDenuncia;
import pe.gob.mpfn.cfms.mesadeturno.persistence.repository.DenunciaRepository;

import java.util.Date;

@SpringBootTest
class DenunciaRepositoryImplTest {

    @Autowired
    private DenunciaRepository denunciaRepository;

    @Test
    void registrarDatosGenerales() throws Exception {

        var datos = new DatosGeneralesDto(
                "1",
                "222",
                "nombre",
                "nombre",
                "ap",
                "ap",
                "@",
                "33",
                new Date(),
                365
        );

        var u = new Usuario();
        u.setUsuario("32920589");
        u.setCodDependencia("506150101");
        u.setCodDespacho("4006014501-2");
        u.setCodDistritoFiscal("0047");
        var idDenuncia = denunciaRepository.registrarDatosGenerales(datos, u);
        Assertions.assertThat(idDenuncia).isNotNull();

    }


    @Test
    void registrarDatosGeneralesDenunciante() throws Exception {

        var datos = new DatosGeneralesDto(
                "1",
                "23213123",
                "nombre",
                "nombre",
                "ap",
                "ap",
                "1212121212",
                null,
                null,
                TipoDenuncia.DENUNCIA_VERBAL.getValue()
        );

        var u = new Usuario();
        u.setUsuario("32920589");
        u.setCodDependencia("506150101");
        u.setCodDespacho("4006014501-2");
        u.setCodDistritoFiscal("0047");
        var idDenuncia = denunciaRepository.registrarDatosGenerales(datos, u);
        Assertions.assertThat(idDenuncia).isNotNull();

    }
}