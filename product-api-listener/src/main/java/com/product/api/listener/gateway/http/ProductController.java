package com.product.api.listener.gateway.http;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.product.api.listener.gateway.json.ProductJson;
import com.product.api.listener.service.ListenerProductService;

@RestController
@RequestMapping("/v1")
public class ProductController {

	@Autowired
	private ListenerProductService productService;

	@GetMapping("/product/{uuid}")
	public ResponseEntity<ProductJson> create(@PathVariable("uuid") String uuid)
			throws ExecutionException, InterruptedException, IOException {

		ProductJson product = productService.execute(ProductJson.builder().uuid(uuid).build());

		return ResponseEntity.ok().body(product);
	}
}
