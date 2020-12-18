package com.apicontabancaria.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.apicontabancaria.domain.model.Cliente;
import com.apicontabancaria.domain.model.Conta;
import com.apicontabancaria.domain.model.StatusConta;
import com.apicontabancaria.domain.repository.ClienteRepository;
import com.apicontabancaria.domain.repository.ContaRepository;
import com.apicontabancaria.exceptionhandler.NegocioException;
import com.apicontabancaria.request.DadosDeposito;
import com.apicontabancaria.request.DadosTransferencia;

@Service
public class ContaService {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	public Conta criarConta(Conta contaInput, Long idCliente) {
		Cliente clienteEntity = clienteRepository.findById(idCliente)
				.orElseThrow(() -> new NegocioException("Cliente não encontrado"));
		Conta conta = contaInput;
		conta.setCliente(clienteEntity);
		conta.setStatusConta(StatusConta.ABERTO);
		return contaRepository.save(conta);
	}

	public Conta consultarSaldo(Long idConta) {
		Conta conta = contaRepository.findById(idConta)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada..."));
		return conta;
	}

	public Conta buscarConta(Long idConta) {
		Conta conta = contaRepository.findById(idConta)
				.orElseThrow(() -> new NegocioException("Conta não cadastrada..."));
		if (conta.getStatusConta().equals(StatusConta.FECHADA)) {
			throw new NegocioException("Conta com status " + conta.getStatusConta());
		}
		return conta;
	}

	public void Transferencia(DadosTransferencia dadosTransferencia) {
		if (dadosTransferencia.getIdContaRemetente() == null || dadosTransferencia.getIdContaDestinatario() == null
				|| dadosTransferencia.getValorTranferencia() == null) {
			throw new NegocioException("há campos vazios, necessario preencher todos os campos!");
		}
		Conta contaRemetente = contaRepository.findById(dadosTransferencia.getIdContaRemetente()).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta remetente não cadastrada..."));
		Conta contaDestinatario = contaRepository.findById(dadosTransferencia.getIdContaDestinatario()).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta destinataria não cadastrada..."));
		realizarTransferencia(contaRemetente, contaDestinatario, dadosTransferencia.getValorTranferencia());
	}

	private void realizarTransferencia(Conta remetente, Conta destinatario, Double valor) {

		if (valor > remetente.getSaldo()) {
			throw new NegocioException("Saldo insuficiente para fazer tranferencia.");
		} else if (destinatario.getStatusConta().equals(StatusConta.FECHADA)) {
			throw new NegocioException("Conta com status " + destinatario.getStatusConta());
		} else if (remetente.getSaldo() >= valor) {
			Double subtracaoTranferencia = remetente.getSaldo() - valor;
			remetente.setSaldo(subtracaoTranferencia);
			Double adicaoTranferencia = destinatario.getSaldo() + valor;
			destinatario.setSaldo(adicaoTranferencia);
			contaRepository.save(remetente);
			contaRepository.save(destinatario);
		}
	}

	public void deposito(DadosDeposito dadosDeposito) {
		if (dadosDeposito.getIdConta() == null || dadosDeposito.getValorDeposito() == null) {
			throw new NegocioException("há campos vazios, necessario preencher todos os campos!");
		}
		Conta contaDestinatario = contaRepository.findById(dadosDeposito.getIdConta())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada..."));
		if (contaDestinatario.getStatusConta().equals(StatusConta.FECHADA)) {
			throw new NegocioException("Conta com status " + contaDestinatario.getStatusConta());
		}
		Double valorDeposito = contaDestinatario.getSaldo() + dadosDeposito.getValorDeposito();
		contaDestinatario.setSaldo(valorDeposito);
		contaRepository.save(contaDestinatario);
	}

	public void excluirConta(Long idConta) {
		if (!contaExistePorId(idConta)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Conta não encontrada...");
		}
		contaRepository.deleteById(idConta);
	}

	public boolean contaExistePorId(Long idConta) {
		Optional<Conta> contaOptional = contaRepository.findById(idConta);
		if (contaOptional.isPresent()) {
			return true;
		} else {
			return false;
		}
	}
}
