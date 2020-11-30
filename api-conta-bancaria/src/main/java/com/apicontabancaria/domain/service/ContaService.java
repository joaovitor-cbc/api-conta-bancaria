package com.apicontabancaria.domain.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apicontabancaria.domain.model.Cliente;
import com.apicontabancaria.domain.model.Conta;
import com.apicontabancaria.domain.model.StatusConta;
import com.apicontabancaria.domain.repository.ClienteRepository;
import com.apicontabancaria.domain.repository.ContaRepository;
import com.apicontabancaria.exceptionhandler.NegocioException;

@Service
public class ContaService {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private ClienteRepository clienteRepository;
	

	public Conta criarConta(Conta contaInput, Long idCliente) {
		Conta conta = contaInput;
		Optional<Cliente> cliente = clienteRepository.findById(idCliente);
		if (cliente.isEmpty()) {
			throw new NegocioException("Cliente não cadastrado...");
		}
		conta.setCliente(cliente.get());
		conta.setStatusConta(StatusConta.ABERTO);
		return contaRepository.save(conta);
	}
	
	public Conta consultarSaldo(Long idConta) {
		Optional<Conta> contaOptional = contaRepository.findById(idConta);
		if(contaOptional.isEmpty()) {
			throw new NegocioException("Conta não cadastrada...");
		}
		Conta conta = contaOptional.get();
		return conta;
	}
}
