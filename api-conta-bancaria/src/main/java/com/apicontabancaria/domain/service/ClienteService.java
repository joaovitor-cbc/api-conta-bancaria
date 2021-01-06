package com.apicontabancaria.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apicontabancaria.domain.model.Cliente;
import com.apicontabancaria.domain.repository.ClienteRepository;
import com.apicontabancaria.exceptionhandler.NegocioException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	public Cliente criarCliente(Cliente cliente) {
		Cliente clienteEntity = cliente;
		Optional<Cliente> clienteOptinal = clienteRepository.findByCpf(clienteEntity.getCpf());
		if (clienteOptinal.isPresent()) {
			throw new NegocioException("Cliente já cadastrado...");
		}
		return clienteRepository.save(clienteEntity);
	}
}
