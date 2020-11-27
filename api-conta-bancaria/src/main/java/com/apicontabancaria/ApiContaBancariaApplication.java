package com.apicontabancaria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@EnableJpaRepositories(basePackages = "com.apicontabancaria.domain.repository")
@SpringBootApplication
public class ApiContaBancariaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiContaBancariaApplication.class, args);
	}

}
