package com.apicontabancaria.model;

import javax.validation.constraints.NotBlank;

public class ClienteModel {
	
	private Long id;
	
	@NotBlank
	private String cpf;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}
