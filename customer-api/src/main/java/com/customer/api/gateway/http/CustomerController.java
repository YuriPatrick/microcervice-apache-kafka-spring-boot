package com.customer.api.gateway.http;

import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.customer.api.gateway.json.CustomerJson;
import com.customer.api.service.SaveCustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;

/*
 * API RESPONSIBLE FOR EXECUTING THE APPLICATION AND SENDING THE TOPICO
 * API RESPONSÁVEL PELA EXECUÇÃO DA APLICAÇÃO E O ENVIO DO TOPICO
 */
@RestController
@RequestMapping("/v1")
public class CustomerController {

	@Autowired
	private SaveCustomerService saveCustomerService;

	@PostMapping("/")
	public String create(@RequestBody CustomerJson customerJson, Optional<Object> object)
			throws JsonProcessingException, InterruptedException, ExecutionException {
		
		//throw new NumberFormatException();
		
		return saveCustomerService.execute(customerJson);
		
	}
}