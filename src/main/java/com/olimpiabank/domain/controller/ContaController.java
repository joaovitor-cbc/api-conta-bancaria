package com.olimpiabank.domain.controller;

import com.olimpiabank.DTO.ContaInput;
import com.olimpiabank.DTO.ContaModel;
import com.olimpiabank.DTO.DadosDeposito;
import com.olimpiabank.DTO.DadosTransferencia;
import com.olimpiabank.domain.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/conta")
public class ContaController {

	@Autowired
	private ContaService contaService;


	@RequestMapping(value = "/consulta-saldo/{idConta}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<ContaModel> consultaSaldo(@PathVariable Long idConta) {
		return ResponseEntity.ok(contaService.consultarSaldo(idConta));
	}


	@RequestMapping(value = "/criar-conta/{idCliente}", method = RequestMethod.POST, produces = "application/json")
	@ResponseStatus(code = HttpStatus.CREATED)
	public ContaModel abrirConta(@Valid @RequestBody ContaInput contaInput, @PathVariable Long idCliente) {
		return contaService.criarConta(contaInput, idCliente);
	}


	@RequestMapping(value = "/tranferencia", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<ContaModel> tranferirDinheiro(@RequestBody DadosTransferencia dadosTransferencia) {
		contaService.validarTransferencia(dadosTransferencia);
		return ResponseEntity.ok(contaService.buscarConta(dadosTransferencia.getIdContaRemetente()));
	}


	@RequestMapping(value = "/deposito", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<ContaModel> depositarDinheiro(@RequestBody DadosDeposito dadosDeposito) {
		contaService.deposito(dadosDeposito);
		return ResponseEntity.ok(contaService.buscarConta(dadosDeposito.getIdConta()));
	}


	@RequestMapping(value = "/apagar-conta/{idConta}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<Void> excluirConta(@PathVariable Long idConta) {
		contaService.excluirConta(idConta);
		return ResponseEntity.noContent().build();
	}
}
