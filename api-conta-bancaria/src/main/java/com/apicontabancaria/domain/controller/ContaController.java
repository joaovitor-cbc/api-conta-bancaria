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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.apicontabancaria.domain.model.Conta;
import com.apicontabancaria.domain.service.ContaService;

@RestController
@RequestMapping("/conta")
public class ContaController {

	@Autowired
	private ContaService contaService;

	@GetMapping("/consulta-saldo/{idConta}")
	public ResponseEntity<Conta> consultaSaldo(@PathVariable Long idConta) {
		Conta conta = contaService.consultarSaldo(idConta);
		if (conta != null) {
			return ResponseEntity.ok(conta);
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/criar-conta/{idCliente}")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Conta abrirConta(@Valid @RequestBody Conta contaInput, @PathVariable Long idCliente) {
		return contaService.criarConta(contaInput, idCliente);
	}

	@PutMapping("/tranferencia/{idContaRemetente}/{idContaDestinatario}/{valorTranferencia}")
	public ResponseEntity<Conta> tranferirDinheiro(@PathVariable Long idContaRemetente,
			@PathVariable Long idContaDestinatario, @PathVariable Double valorTranferencia) {
		if (!contaService.contaExistePorId(idContaRemetente)) {
			return ResponseEntity.notFound().build();
		}
		contaService.Transferencia(valorTranferencia, idContaRemetente, idContaDestinatario);
		Conta conta = contaService.buscarConta(idContaRemetente);
		return ResponseEntity.ok(conta);
	}

	@PutMapping("/deposito/{idConta}/{valorDeposito}")
	public ResponseEntity<Conta> depositarDinheiro(@PathVariable Long idConta, @PathVariable Double valorDeposito) {
		if (!contaService.contaExistePorId(idConta)) {
			return ResponseEntity.notFound().build();
		}
		contaService.deposito(idConta, valorDeposito);
		Conta conta = contaService.buscarConta(idConta);
		return ResponseEntity.ok(conta);
	}

	@DeleteMapping("/apagar-conta/{idConta}")
	public ResponseEntity<Void> excluirConta(@PathVariable Long idConta) {
		if (!contaService.contaExistePorId(idConta)) {
			return ResponseEntity.notFound().build();
		}
		contaService.excluirConta(idConta);
		return ResponseEntity.noContent().build();
	}
}
