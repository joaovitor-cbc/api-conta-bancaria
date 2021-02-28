package com.apicontabancaria.request;

public class DadosDeposito {

	private Long idConta;
	private Double valorDeposito;

	public DadosDeposito(Long idConta, Double valorDeposito) {
		super();
		this.idConta = idConta;
		this.valorDeposito = valorDeposito;
	}

	public DadosDeposito() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getIdConta() {
		return idConta;
	}

	public void setIdConta(Long idConta) {
		this.idConta = idConta;
	}

	public Double getValorDeposito() {
		return valorDeposito;
	}

	public void setValorDeposito(Double valorDeposito) {
		this.valorDeposito = valorDeposito;
	}
}
