package com.arkon.pipeline.v1;

import com.arkon.pipeline.v1.repository.AlcaldiaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication // Anotación que define a una aplicación web con Spring
@Configuration // Permite inyectar las configuraciones y beans de java al contexto de la aplicación
@EnableFeignClients // Habilita las peticiones a API con Feign
@EnableAsync
public class V1Application {
	private static final Logger log = LoggerFactory.getLogger(V1Application.class);

	private final AlcaldiaRepository alcaldiaRepository;

	public V1Application(AlcaldiaRepository alcaldiaRepository) {
		this.alcaldiaRepository = alcaldiaRepository;
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

	@Bean("threadPoolTaskExecutor")
	public TaskExecutor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(20);
		executor.setMaxPoolSize(1000);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setThreadNamePrefix("Async-");
		return executor;
	}

	public static void main(String[] args) {
		SpringApplication.run(V1Application.class, args);
	}

}
