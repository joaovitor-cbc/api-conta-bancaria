package com.olimpiabank.DTO;

public class DadosTransferencia {

	private Long idContaRemetente;

	private Long idContaDestinatario;

	private Double valorTranferencia;

	public DadosTransferencia(Long idContaRemetente, Long idContaDestinatario, Double valorTranferencia) {
		super();
		this.idContaRemetente = idContaRemetente;
		this.idContaDestinatario = idContaDestinatario;
		this.valorTranferencia = valorTranferencia;
	}

	public DadosTransferencia() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getIdContaRemetente() {
		return idContaRemetente;
	}

	public void setIdContaRemetente(Long idContaRemetente) {
		this.idContaRemetente = idContaRemetente;
	}

	public Long getIdContaDestinatario() {
		return idContaDestinatario;
	}

	public void setIdContaDestinatario(Long idContaDestinatario) {
		this.idContaDestinatario = idContaDestinatario;
	}

	public Double getValorTranferencia() {
		return valorTranferencia;
	}

	public void setValorTranferencia(Double valorTranferencia) {
		this.valorTranferencia = valorTranferencia;
	}
}
