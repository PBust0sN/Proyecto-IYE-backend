package iye.grupo2.cronicotrak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CronicotrakApplication {

	public static void main(String[] args) {
		SpringApplication.run(CronicotrakApplication.class, args);
	}

}
