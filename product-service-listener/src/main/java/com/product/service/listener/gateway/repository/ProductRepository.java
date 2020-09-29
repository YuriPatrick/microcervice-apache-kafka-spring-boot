package com.product.service.listener.gateway.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.product.service.listener.domain.Product;

public interface ProductRepository extends CrudRepository<Product, UUID> {

}
