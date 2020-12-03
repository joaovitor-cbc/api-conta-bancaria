package com.apicontabancaria.domain.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.apicontabancaria.domain.model.Cliente;
import com.apicontabancaria.domain.service.ClienteService;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@PostMapping("/cadastrar-cliente")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cliente abrirConta(@Valid @RequestBody Cliente clienteInput) {
		return clienteService.criarCliente(clienteInput);
	}
}
