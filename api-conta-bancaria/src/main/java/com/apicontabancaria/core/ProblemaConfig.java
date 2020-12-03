package com.apicontabancaria.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.apicontabancaria.domain.exception.Problema;

@Configuration
public class ProblemaConfig {
	@Bean
	public Problema problema() {
		return new Problema();
	}
}
