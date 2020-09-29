package com.product.service.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.service.domain.Product;
import com.product.service.gateway.json.ProductJson;
/*
 * CLASSE DE SERVIÇO RESPONSÁVEL PELO RECEBIMENTO DO TOPICO ENVIADO PELA API
 */
@Service
public class CreateProduct {

	@Autowired
	private CreateProductService createProductService;
	
	@KafkaListener(topics = "${kafka.topic.request-topic}")
	@SendTo
	public String listen(String json) throws InterruptedException, JsonProcessingException {
		
		ObjectMapper mapper= new ObjectMapper();
		
		ProductJson productJson = mapper.readValue(json, ProductJson.class);
		
		UUID uuid = createProductService.execute(Product
				.builder()
				.name(productJson.getName())
				.description(productJson.getDescription())
				.qnt(productJson.getQnt())
				.obs(productJson.getObs())
				.uuidCustomer(productJson.getUuidCustomer())
				.build()
		);
		
		productJson.setUuid(uuid.toString());
		
		return mapper.writeValueAsString(productJson);
		
	}
}
