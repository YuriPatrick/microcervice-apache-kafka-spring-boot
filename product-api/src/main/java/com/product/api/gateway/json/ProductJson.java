package com.product.api.gateway.json;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductJson {

	
	private String uuid;

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
