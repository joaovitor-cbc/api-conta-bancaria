package com.olimpiabank.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static springfox.documentation.builders.RequestHandlerSelectors.basePackage;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	private static final String BASE_PACKAGE = "com.olimpiabank.controller";
	private static final String API_TITLE = "OlimpiaBank API.";
	private static final String API_DESCRIPTION = "REST API bancaria.";
	private static final String CONTACT_NAME = "João Vitor";
	private static final String CONTACT_GITHUB = "https://gtihub.com/joaovitor-cbc";
	private static final String CONTACT_EMAIL = "joaovitor.novacruz@gmail.com";

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(basePackage(BASE_PACKAGE))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(buildApiInfo());
	}

	private List<ResponseMessage> responseMessageForGET() {
		return new ArrayList<ResponseMessage>() {
			{
				add(new ResponseMessageBuilder().code(500).message("500 message").responseModel(new ModelRef("Error"))
						.build());
				add(new ResponseMessageBuilder().code(403).message("Forbidden!").build());
			}
		};
	}

	private ApiInfo buildApiInfo() {
		return new ApiInfoBuilder()
				.title(API_TITLE)
				.description(API_DESCRIPTION)
				.version("1.0.0")
				.license("licença MIT")
				.contact(new Contact(CONTACT_NAME, CONTACT_GITHUB, CONTACT_EMAIL))
				.build();
	}
}
