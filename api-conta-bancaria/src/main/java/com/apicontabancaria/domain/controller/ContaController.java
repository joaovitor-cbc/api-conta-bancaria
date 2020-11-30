package com.apicontabancaria.domain.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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
import com.apicontabancaria.domain.repository.ClienteRepository;
import com.apicontabancaria.domain.repository.ContaRepository;
import com.apicontabancaria.domain.service.ContaService;
import com.apicontabancaria.model.ContaModel;

@RestController
@RequestMapping("/conta")
public class ContaController {

	@Autowired
	ContaService contaService;

	@Autowired
	ContaRepository contaRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	ClienteRepository clienteRepository;

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
	public ContaModel abrirConta(@Valid @RequestBody ContaModel contaInput, @PathVariable Long idCliente) {
		Conta conta = toEntity(contaInput);
		return contaService.criarConta(conta, idCliente);

	}

	@PutMapping("/tranferencia/{idContaRemetente}/{idContaDestinatario}/{valorTranferencia}")
	public ResponseEntity<Conta> tranferirDinheiro(@PathVariable Long idContaRemetente,
			@PathVariable Long idContaDestinatario, @PathVariable Double valorTranferencia) {
		if (!contaRepository.existsById(idContaRemetente)) {
			return ResponseEntity.notFound().build();
		}
		contaService.Transferencia(valorTranferencia, idContaRemetente, idContaDestinatario);
		Conta conta = contaService.buscarConta(idContaRemetente);
		return ResponseEntity.ok(conta);
	}

	@PutMapping("/deposito/{idConta}/{valorDeposito}")
	public ResponseEntity<Conta> depositarDinheiro(@PathVariable Long idConta, @PathVariable Double valorDeposito) {
		if (!contaRepository.existsById(idConta)) {
			return ResponseEntity.notFound().build();
		}
		contaService.deposito(idConta, valorDeposito);
		Conta conta = contaService.buscarConta(idConta);
		return ResponseEntity.ok(conta);
	}

	@DeleteMapping("/apagar-conta/{idConta}")
	public ResponseEntity<Void> excluirConta(@PathVariable Long idConta) {
		if (!contaRepository.existsById(idConta)) {
			return ResponseEntity.notFound().build();
		}
		contaService.excluirConta(idConta);
		return ResponseEntity.noContent().build();
	}

	private Conta toEntity(ContaModel contaModel) {
		return modelMapper.map(contaModel, Conta.class);
	}

}
