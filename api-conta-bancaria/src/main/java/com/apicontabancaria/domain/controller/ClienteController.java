package com.apicontabancaria.domain.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.apicontabancaria.domain.model.Cliente;
import com.apicontabancaria.domain.repository.ClienteRepository;
import com.apicontabancaria.domain.service.ClienteService;
import com.apicontabancaria.model.ClienteModel;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@Autowired
	ClienteRepository clienteRepository;
	
	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/cadastrar-cliente")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ClienteModel abrirConta(@Valid @RequestBody ClienteModel clienteInput) {
		Cliente cliente = toEntity(clienteInput);
		clienteService.criarCliente(cliente);
		return toModel(cliente);
	}
	
	private Cliente toEntity(ClienteModel clienteModel) {
		return modelMapper.map(clienteModel, Cliente.class);
	}
	
	private ClienteModel toModel(Cliente cliente) {
		return modelMapper.map(cliente, ClienteModel.class);
	}
}
