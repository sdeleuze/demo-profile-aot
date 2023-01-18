package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class ProdConfiguration {

	@Bean
	CommandLineRunner prodRunner() {
		return args -> System.out.println("Hello from prod");
	}
}
