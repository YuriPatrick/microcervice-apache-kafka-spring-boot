package com.customer.save.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.customer.save.service.domain.Customer;
import com.customer.save.service.gateway.repository.CustomerRepository;

/*
 * CLASS OF SERVICE RESPONSIBLE FOR RECORDING THE DATA ON BD
 * CLASSE DE SERVIÇO RESPONSÁVEL POR GRAVAR O DADO NO BD
 */
@Service
public class CreateCustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public UUID execute(Customer customer) {
		throw new RuntimeException("test KafkaErrorHandler");
		
		//throw new NumberFormatException();
		
		//customer.setId(UUID.randomUUID());
		//customerRepository.save(customer);
		//return customer.getId();
		

	}

}