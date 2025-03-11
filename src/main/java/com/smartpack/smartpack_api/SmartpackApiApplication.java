package com.smartpack.smartpack_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.smartpack")
public class SmartpackApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartpackApiApplication.class, args);
	}

}
