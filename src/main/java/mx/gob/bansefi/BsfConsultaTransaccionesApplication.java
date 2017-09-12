package mx.gob.bansefi;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BsfConsultaTransaccionesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BsfConsultaTransaccionesApplication.class, args);
		PropertyConfigurator.configure("log4j.properties");
	}
}
