package com.olimpiabank.domain.service;

import java.util.Optional;

import com.olimpiabank.model.ClienteInput;
import com.olimpiabank.model.ClienteModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olimpiabank.domain.model.Cliente;
import com.olimpiabank.domain.repository.ClienteRepository;
import com.olimpiabank.exceptionhandler.ClienteExceptionBadRequest;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ModelMapper modelMapper;

	public ClienteModel criarCliente(ClienteInput clienteInput) {
		Cliente clienteEntity = toEntity(clienteInput);
		Optional<Cliente> clienteOptinal = clienteRepository.findByCpf(clienteEntity.getCpf());
		if (clienteOptinal.isPresent()) {
			throw new ClienteExceptionBadRequest("Cliente já cadastrado...");
		}
		return toModel(clienteRepository.save(clienteEntity));
	}

	private ClienteModel toModel(Cliente cliente) {
		return modelMapper.map(cliente, ClienteModel.class);
	}

	private Cliente toEntity(ClienteInput clienteInput) {
		return modelMapper.map(clienteInput, Cliente.class);
	}
}
