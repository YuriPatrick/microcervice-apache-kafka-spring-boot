package com.product.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
/*
 * CLASSE DE INICIALIZAÇÃO DO SPRING
 */
@SpringBootApplication
@EnableKafka
@EnableAsync
public class ProductSaveApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductSaveApiApplication.class, args);
	}

}
