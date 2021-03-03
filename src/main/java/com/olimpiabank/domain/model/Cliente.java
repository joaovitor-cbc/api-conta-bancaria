package com.olimpiabank.domain.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
/**
 * 
 * @author joao_vitor
 * 
 * <h2>Classe que representa cliente</h2>
 * 
 * @param recebe por parametro duas String que
 * representa o @atributo nome e cpf.
 * 
 * @return uma instancia de cliente.
 *
 * @version 1.0.1
 */
@Entity
public class Cliente {

	@ApiModelProperty(value = "id do cliente.")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty(value = "Nome do cliente.")
	private String nome;

	@ApiModelProperty(value = "Cpf do cliente.")
	@Column(unique = true)
	private String cpf;

	@ApiModelProperty(value = "Conta do cliente.")
	@Transient
	@OneToOne(mappedBy = "cliente")
	private Conta conta;

	public Cliente(Long id, String nome ,String cpf) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
	}

	public Cliente() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String name) {
		this.nome = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		return true;
	}
}
