package com.apicontabancaria.domain.controller;



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
import com.apicontabancaria.domain.model.Conta;
import com.apicontabancaria.domain.repository.ContaRepository;
import com.apicontabancaria.domain.service.ContaService;


@RestController
@RequestMapping("/conta")
public class ContaController {
	
	@Autowired
	ContaService contaService;
	
	@Autowired
	ContaRepository contaRepository;
	
	@GetMapping("/consulta-saldo/{idConta}")
	public ResponseEntity<Conta> consultaSaldo(@PathVariable Long idConta) {
		Conta conta = contaService.consultarSaldo(idConta);
		if(conta != null) {
			return ResponseEntity.ok(conta);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/criar-conta/{idCliente}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Conta abrirConta(@Valid @RequestBody Conta contaInput, @PathVariable Long idCliente){
		return contaService.criarConta(contaInput, idCliente);
	}
}
