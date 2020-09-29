package com.product.api.gateway.http;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.product.api.gateway.json.ProductJson;
import com.product.api.service.SaveProductService;
/*
 * API RESPONSÁVEL PELA EXECUÇÃO DA APLICAÇÃO E O ENVIO DO TOPICO
 */
@RestController
@RequestMapping("/v1")
public class ProductController {

	@Autowired
	private SaveProductService saveProductService;

	@PostMapping("/customers/{uuid}/products")
	public String create(@PathVariable("uuid") String uuid, @RequestBody ProductJson customerJson)
			throws JsonProcessingException, InterruptedException, ExecutionException {
		customerJson.setUuidCustomer(uuid);
		return saveProductService.execute(customerJson);

	}
	
}
