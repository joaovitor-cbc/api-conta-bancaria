package com.olimpiabank.domain.controller;

import com.olimpiabank.DTO.ClienteInput;
import com.olimpiabank.DTO.ClienteModel;
import com.olimpiabank.domain.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;


	@RequestMapping(value = "/cadastrar-cliente", method = RequestMethod.POST, produces = "application/json")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ClienteModel criarCliente(@Valid @RequestBody ClienteInput clienteInput) {
		return clienteService.criarCliente(clienteInput);
	}
}
