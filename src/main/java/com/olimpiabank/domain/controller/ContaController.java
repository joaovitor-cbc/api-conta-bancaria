package com.olimpiabank.domain.controller;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.olimpiabank.domain.model.Conta;
import com.olimpiabank.domain.service.ContaService;
import com.olimpiabank.request.DadosDeposito;
import com.olimpiabank.request.DadosTransferencia;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/conta")
public class ContaController {

	@Autowired
	private ContaService contaService;

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna uma conta"),
			@ApiResponse(code = 404, message = "Foi gerada uma exceção"), })
	@RequestMapping(value = "/consulta-saldo/{idConta}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Conta> consultaSaldo(@PathVariable Long idConta) {
		return ResponseEntity.ok(contaService.consultarSaldo(idConta));
	}

	@ApiResponses(value = { @ApiResponse(code = 201, message = "Cria e retorna uma conta"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"), })
	@RequestMapping(value = "/criar-conta/{idCliente}", method = RequestMethod.POST, produces = "application/json")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Conta abrirConta(@Valid @RequestBody Conta contaInput, @PathVariable Long idCliente) {
		return contaService.criarConta(contaInput, idCliente);
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Tranferencia de dinheiro"),
			@ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
			@ApiResponse(code = 404, message = "Foi gerada uma exceção"), })
	@RequestMapping(value = "/tranferencia", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Conta> tranferirDinheiro(@RequestBody DadosTransferencia dadosTransferencia) {
		contaService.validarTransferencia(dadosTransferencia);
		return ResponseEntity.ok(contaService.buscarConta(dadosTransferencia.getIdContaRemetente()));
	}

	@ApiResponses(value = { @ApiResponse(code = 200, message = "Deposita Dinheiro"),
			@ApiResponse(code = 404, message = "Foi gerada uma exceção"), })
	@RequestMapping(value = "/deposito", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<Conta> depositarDinheiro(@RequestBody DadosDeposito dadosDeposito) {
		contaService.deposito(dadosDeposito);
		return ResponseEntity.ok(contaService.buscarConta(dadosDeposito.getIdConta()));
	}

	@ApiResponses(value = { @ApiResponse(code = 204, message = "Apaga uma conta"),
			@ApiResponse(code = 404, message = "Foi gerada uma exceção"), })
	@RequestMapping(value = "/apagar-conta/{idConta}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Void> excluirConta(@PathVariable Long idConta) {
		contaService.excluirConta(idConta);
		return ResponseEntity.noContent().build();
	}
}
