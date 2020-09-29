package com.product.service.gateway.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.product.service.domain.Product;

public interface ProductRepository extends CrudRepository<Product, UUID> {

}
