package com.arkon.pipeline.v1;

import com.arkon.pipeline.v1.model.Information;
import com.arkon.pipeline.v1.repository.InformationRepository;
import com.coxautodev.graphql.tools.SchemaParser;
import graphql.schema.GraphQLScalarType;
import graphql.schema.GraphQLSchema;
import org.apache.commons.io.IOUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.management.Query;
import java.io.IOException;
import java.nio.charset.Charset;

@SpringBootApplication
@EnableFeignClients
@Configuration
public class V1Application {
	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(V1Application.class, args);
	}

}
