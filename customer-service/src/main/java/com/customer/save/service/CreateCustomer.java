package com.customer.save.service;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

import com.customer.save.service.domain.Customer;
import com.customer.save.service.gateway.json.CustomerJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
/*
 * CLASSE DE SERVIÇO RESPONSÁVEL PELO RECEBIMENTO DO TOPICO ENVIADO PELA API
 */
@Service
public class CreateCustomer {

	@Autowired
	private CreateCustomerService createCustomerService;
	
	@KafkaListener(topics = "${kafka.topic.request-topic}")
	@SendTo
	public String listen(String json) throws InterruptedException, JsonProcessingException {
		
		ObjectMapper mapper = new ObjectMapper();
		
        CustomerJson customerJson = mapper.readValue(json, CustomerJson.class);
		
        UUID uuid = createCustomerService.execute(Customer
                .builder()
                .name(customerJson.getName())
                .lastName(customerJson.getLastName())
                .birthDate(customerJson.getBirthDate())
                .phone(customerJson.getPhone())
                .build()
        );
		
        customerJson.setUuid(uuid.toString());

        return mapper.writeValueAsString(customerJson);
	}
	
}
