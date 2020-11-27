package com.apicontabancaria.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apicontabancaria.domain.model.Cliente;
import com.apicontabancaria.domain.model.Conta;
import com.apicontabancaria.domain.model.StatusConta;
import com.apicontabancaria.domain.repository.ContaRepository;
import com.apicontabancaria.exceptionhandler.NegocioException;

@Service
public class ContaService {
	
	@Autowired
	private ContaRepository contaRepository;
	
	public Conta criar(Cliente cliente) {
		Conta conta = cliente.getConta();
		conta.setStatusConta(StatusConta.ABERTO);
		return contaRepository.save(conta);
	}
}
