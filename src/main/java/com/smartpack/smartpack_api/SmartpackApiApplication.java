package com.smartpack.smartpack_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Classe SmartpackApiApplication
 * PRICIPAL MAIN
 */
@SpringBootApplication(scanBasePackages = "com.smartpack")
@EnableJpaRepositories("com.smartpack.repositories")
@EntityScan("com.smartpack.models")
public class SmartpackApiApplication {

	/**
	 * MAIN
	 * 
	 * @param args String llistat
	 */
	public static void main(String[] args) {
		SpringApplication.run(SmartpackApiApplication.class, args);
	}

}
