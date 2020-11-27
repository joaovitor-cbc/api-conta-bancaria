package com.apicontabancaria.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apicontabancaria.domain.model.Cliente;
import com.apicontabancaria.domain.model.Conta;
import com.apicontabancaria.domain.model.StatusConta;
import com.apicontabancaria.domain.repository.ClienteRepository;
import com.apicontabancaria.domain.repository.ContaRepository;
import com.apicontabancaria.exceptionhandler.NegocioException;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente criarCliente(Cliente cliente){
		Cliente clienteExistente = clienteRepository.findByCpf(cliente.getCpf());
		if(cliente.getCpf().equals(clienteExistente)) {
			throw new NegocioException("Cliente já cadastrado...");
		}
		return clienteRepository.save(cliente);
	}
}
