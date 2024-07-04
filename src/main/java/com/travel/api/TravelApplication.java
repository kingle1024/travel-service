package com.travel.api;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TravelApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TravelApplication.class);
		app.setDefaultProperties(Collections
			.singletonMap("spring.config.location", "file:/home/imrooney94/api/application.properties"));
		app.run(args);
	}

}
