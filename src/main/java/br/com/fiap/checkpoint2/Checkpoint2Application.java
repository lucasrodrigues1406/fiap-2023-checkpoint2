package br.com.fiap.checkpoint2;

import org.slf4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class Checkpoint2Application implements CommandLineRunner {
	
	private static Logger LOG = 
			org.slf4j.LoggerFactory.getLogger(Checkpoint2Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Checkpoint2Application.class, args);
	}
	

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Implementando run");
		LOG.info("Estou testando o log");
	}	

}
