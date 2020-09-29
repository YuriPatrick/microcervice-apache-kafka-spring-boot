package com.product.service.listener.domain;

import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@PrimaryKey
	@NotNull
	@NotEmpty
	private UUID id;

	@NotNull
	@NotEmpty
	private String name;

	@NotNull
	@NotEmpty
	private String description;

	@NotEmpty
	private int qnt;

	@NotNull
	@NotEmpty
	private String obs;
	
	@NotNull
    @NotEmpty
    private String uuidCustomer;
}
