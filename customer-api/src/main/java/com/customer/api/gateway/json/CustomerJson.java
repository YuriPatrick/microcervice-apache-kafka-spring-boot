package com.customer.api.gateway.json;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Builder;

@Builder
public class CustomerJson implements Serializable{
	private static final long serialVersionUID = 1L;

	private String uuid;

	@NotNull
	@NotEmpty
	private String name;

	@NotNull
	@NotEmpty
	private String lastName;

	@NotNull
	@NotEmpty
	private String phone;

	@NotNull
	@NotEmpty
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private String birthDate;
	
	public CustomerJson() {}
	
	

	public CustomerJson(String uuid, @NotNull @NotEmpty String name, @NotNull @NotEmpty String lastName,
			@NotNull @NotEmpty String phone, @NotNull @NotEmpty String birthDate) {
		this.uuid = uuid;
		this.name = name;
		this.lastName = lastName;
		this.phone = phone;
		this.birthDate = birthDate;
	}



	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}



	@Override
	public String toString() {
		return "CustomerJson [uuid=" + uuid + ", name=" + name + ", lastName=" + lastName + ", phone=" + phone
				+ ", birthDate=" + birthDate + "]";
	}

	

}
