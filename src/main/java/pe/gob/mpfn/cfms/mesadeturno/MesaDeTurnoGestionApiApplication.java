package pe.gob.mpfn.cfms.mesadeturno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
		"pe.gob.mpfn.cfms.mesadeturno",
		"pe.gob.mpfn.cfms.generales.libreria.core.kafka",
		"pe.gob.mpfn.cfms.generales.libreria.core.exception",
		"pe.gob.mpfn.cfms.generales.libreria.core.handler",
		"pe.gob.mpfn.cfms.generales.libreria.core.config.webclient",
		"pe.gob.mpfn.cfms.generales.libreria.core.utils"
})
public class MesaDeTurnoGestionApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(MesaDeTurnoGestionApiApplication.class, args);
	}

}
