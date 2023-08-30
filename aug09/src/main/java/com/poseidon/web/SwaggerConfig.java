package com.poseidon.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableSwagger2
@Configuration
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.basePackage("com.poseidon.web")).paths(PathSelectors.any()).build();
		
	}
	
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("choonang").version("0.0.1").description("설명").build();
				
		
		
	}
}

////클래스이긴 하지만 설정파일임
//@EnableSwagger2
//@Configuration
//public class SwaggerConfig {
//	
//	@Bean
//	public Docket api() {
//		Parameter parameterBuilder = new ParameterBuilder().name(HttpHeaders.AUTHORIZATION).description("Access Tocken").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
//		
//		
//		return new Docket(DocumentationType.SWAGGER_2).apiInfo(ApiInfo());	
//	}
//
//}
