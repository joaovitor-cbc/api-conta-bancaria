package com.apicontabancaria.domain.controller;

import java.util.Optional;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.apicontabancaria.domain.model.Cliente;
import com.apicontabancaria.domain.model.Conta;
import com.apicontabancaria.domain.repository.ClienteRepository;
import com.apicontabancaria.domain.service.ClienteService;
import com.apicontabancaria.domain.service.ContaService;
import com.apicontabancaria.exceptionhandler.NegocioException;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	
	@Autowired
	private ContaService contaService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	ClienteRepository clienteRepository; 
	
	@GetMapping("/consulta-saldo/{id}")
	public ResponseEntity<Cliente> consultaSaldo(@PathVariable Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if(cliente.isPresent()) {
			Cliente client = cliente.get();
			return ResponseEntity.ok(client);
		}
		return ResponseEntity.notFound().build();
	}
	@PostMapping("/abrir-conta")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cliente abrirConta(@Valid @RequestBody Cliente clienteInput){
		Cliente cliente = clienteInput;
		contaService.criar(cliente);
		return clienteService.criarCliente(cliente);
	}
	
}
