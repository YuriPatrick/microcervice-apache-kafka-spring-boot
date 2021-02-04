package com.product.service.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.service.domain.Product;
import com.product.service.gateway.repository.ProductRepository;
/*
 * CLASSE DE SERVIÇO RESPONSÁVEL POR GRAVAR O DADO NO BD
 */
@Service
public class CreateProductService {

	@Autowired
	private ProductRepository productRepository;

	public UUID execute(Product product) {
		
		throw new RuntimeException("test KafkaErrorHandler");
		//product.setId(UUID.randomUUID());
		//productRepository.save(product);
		//return product.getId();
	}

	/*
	 * public Product findById(String id) throws JsonMappingException,
	 * JsonProcessingException {
	 * 
	 * ObjectMapper mapper = new ObjectMapper(); ProductJson productJson =
	 * mapper.readValue(id, ProductJson.class);
	 * 
	 * Optional<Product> obj =
	 * productRepository.findById(UUID.fromString(productJson.getUuid())); return
	 * obj.orElseThrow(() -> new ObjectNotFoundException("objeto não encontrado"));
	 * }
	 */
}
