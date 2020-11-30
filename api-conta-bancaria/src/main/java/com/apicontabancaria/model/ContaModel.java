package com.apicontabancaria.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import com.apicontabancaria.domain.model.StatusConta;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class ContaModel {
	
	@JsonProperty(access = Access.READ_ONLY)
	private Long id;

	@NotNull
	private Double saldo;

	@NotBlank
	private String tipoConta;

	
	private ClienteModel clienteModel;

	@JsonProperty(access = Access.READ_ONLY)
	private StatusConta statusConta;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public String getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}

	public ClienteModel getClienteModel() {
		return clienteModel;
	}

	public void setClienteModel(ClienteModel clienteModel) {
		this.clienteModel = clienteModel;
	}

	public StatusConta getStatusConta() {
		return statusConta;
	}

	public void setStatusConta(StatusConta statusConta) {
		this.statusConta = statusConta;
	}
}
