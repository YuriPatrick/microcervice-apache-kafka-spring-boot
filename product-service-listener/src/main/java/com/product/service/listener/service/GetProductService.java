package com.product.service.listener.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.service.listener.domain.Product;
import com.product.service.listener.gateway.json.ProductJson;
import com.product.service.listener.gateway.repository.ProductRepository;


@Service
public class GetProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@KafkaListener(topics = "${kafka.topic.request-topic}")
    @SendTo
	public String execute(String json) throws JsonProcessingException {

	      
        ObjectMapper mapper = new ObjectMapper();
        ProductJson productJson = mapper.readValue(json, ProductJson.class);

        Optional<Product> product = productRepository.findById(UUID.fromString(productJson.getUuid()));
        ProductJson productReturn = ProductJson
                .builder()
                .uuid(productJson.getUuid())
                .name(product.get().getName())
                .description(product.get().getDescription())
                .qnt(product.get().getQnt())
                .obs(product.get().getObs())
                .uuidCustomer(product.get().getUuidCustomer())
                .build();

        return mapper.writeValueAsString(productReturn);
    }
	
}
