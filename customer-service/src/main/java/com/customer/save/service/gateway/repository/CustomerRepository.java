package com.customer.save.service.gateway.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.customer.save.service.domain.Customer;

/*
 * INTERFACE DE RESPOSITORIO PARA MANIPULAÇÃO DE DADOS
 */
public interface CustomerRepository extends CrudRepository<Customer, UUID> {

}
