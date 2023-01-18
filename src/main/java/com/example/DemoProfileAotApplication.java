package com.example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(SampleConfigurationProperties.class)
public class DemoProfileAotApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoProfileAotApplication.class, args);
	}

	@Bean
	CommandLineRunner defaultRunner(SampleConfigurationProperties properties) {
		return args -> System.out.println(properties.message());
	}

}
