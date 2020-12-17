package com.apicontabancaria.domain.controller;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.apicontabancaria.domain.model.Conta;
import com.apicontabancaria.domain.service.ContaService;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/conta")
public class ContaController {

	@Autowired
	private ContaService contaService;
	
	@ApiResponses(value = {
		    @ApiResponse(code = 200, message = "Retorna uma conta"),
		    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
		    @ApiResponse(code = 404, message = "Foi gerada uma exceção"),
		})
	@RequestMapping(value = "/consulta-saldo/{idConta}", method = RequestMethod.GET, produces="application/json")
	public ResponseEntity<Conta> consultaSaldo(@PathVariable Long idConta) {
		Conta conta = contaService.consultarSaldo(idConta);
		if (conta != null) {
			return ResponseEntity.ok(conta);
		}
		return ResponseEntity.notFound().build();
	}
	
	@ApiResponses(value = {
		    @ApiResponse(code = 201, message = "Cria e retorna uma conta"),
		    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
		    @ApiResponse(code = 500, message = "Foi gerada uma exceção"),
		})
	@RequestMapping(value = "/criar-conta/{idCliente}", method = RequestMethod.POST, produces="application/json")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Conta abrirConta(@Valid @RequestBody Conta contaInput, @PathVariable Long idCliente) {
		return contaService.criarConta(contaInput, idCliente);
	}
	
	@ApiResponses(value = {
		    @ApiResponse(code = 201, message = "Tranferencia de dinheiro"),
		    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
		    @ApiResponse(code = 404, message = "Foi gerada uma exceção"),
		})
	@RequestMapping(value = "/tranferencia/{idContaRemetente}/{idContaDestinatario}/{valorTranferencia}", method = RequestMethod.PUT, produces="application/json")
	public ResponseEntity<Conta> tranferirDinheiro(@PathVariable Long idContaRemetente,
			@PathVariable Long idContaDestinatario, @PathVariable Double valorTranferencia) {
		if (!contaService.contaExistePorId(idContaRemetente)) {
			return ResponseEntity.notFound().build();
		}
		contaService.Transferencia(valorTranferencia, idContaRemetente, idContaDestinatario);
		Conta conta = contaService.buscarConta(idContaRemetente);
		return ResponseEntity.ok(conta);
	}
	
	@ApiResponses(value = {
		    @ApiResponse(code = 201, message = "Deposita Dinheiro"),
		    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
		    @ApiResponse(code = 404, message = "Foi gerada uma exceção"),
		})
	@RequestMapping(value = "/deposito/{idConta}/{valorDeposito}", method = RequestMethod.PUT, produces="application/json")
	public ResponseEntity<Conta> depositarDinheiro(@PathVariable Long idConta, @PathVariable Double valorDeposito) {
		if (!contaService.contaExistePorId(idConta)) {
			return ResponseEntity.notFound().build();
		}
		contaService.deposito(idConta, valorDeposito);
		Conta conta = contaService.buscarConta(idConta);
		return ResponseEntity.ok(conta);
	}
	
	@ApiResponses(value = {
		    @ApiResponse(code = 204, message = "Apaga uma conta"),
		    @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
		    @ApiResponse(code = 404, message = "Foi gerada uma exceção"),
		})
	@RequestMapping(value = "/apagar-conta/{idConta}", method = RequestMethod.DELETE, produces="application/json")
	public ResponseEntity<Void> excluirConta(@PathVariable Long idConta) {
		if (!contaService.contaExistePorId(idConta)) {
			return ResponseEntity.notFound().build();
		}
		contaService.excluirConta(idConta);
		return ResponseEntity.noContent().build();
	}
}
