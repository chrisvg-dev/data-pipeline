package com.arkon.pipeline.v1;

import graphql.GraphQL;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication // Anotación que define a una aplicación web con Spring
@Configuration // Permite inyectar las configuraciones y beans de java al contexto de la aplicación
@EnableFeignClients // Habilita las peticiones a API con Feign

public class V1Application {
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}



	public static void main(String[] args) {
		SpringApplication.run(V1Application.class, args);
	}
}
