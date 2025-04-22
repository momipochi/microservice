package com.products.query.proudcts_query;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;

@EnableWebFlux
@SpringBootApplication
public class ProudctsQueryApplication {

	public static void main(String[] args) throws InterruptedException {
		Thread.sleep(10000);
		SpringApplication.run(ProudctsQueryApplication.class, args);
	}

}
