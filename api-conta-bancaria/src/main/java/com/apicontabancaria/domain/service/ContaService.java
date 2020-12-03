package com.apicontabancaria.domain.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.apicontabancaria.domain.model.Cliente;
import com.apicontabancaria.domain.model.Conta;
import com.apicontabancaria.domain.model.StatusConta;
import com.apicontabancaria.domain.repository.ClienteRepository;
import com.apicontabancaria.domain.repository.ContaRepository;
import com.apicontabancaria.exceptionhandler.NegocioException;

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
				.orElseThrow(() -> new NegocioException("Conta não encontrada..."));
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

	public void Transferencia(Double valorTranferencia, Long idContaRemetente, Long idContaDestinatario) {

		Conta contaRemetente = contaRepository.findById(idContaRemetente)
				.orElseThrow(() -> new NegocioException("Conta do remetente não cadastrada..."));
		Conta contaDestinatario = contaRepository.findById(idContaDestinatario)
				.orElseThrow(() -> new NegocioException("Conta Destinataria não cadastrada..."));
		realizarTransferencia(contaRemetente, contaDestinatario, valorTranferencia);
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

	public void deposito(Long idConta, Double valor) {
		Conta contaDestinatario = contaRepository.findById(idConta)
				.orElseThrow(() -> new NegocioException("Conta não encontrada.."));
		if (contaDestinatario.getStatusConta().equals(StatusConta.FECHADA)) {
			throw new NegocioException("Conta com status " + contaDestinatario.getStatusConta());
		}
		Double valorDeposito = contaDestinatario.getSaldo() + valor;
		contaDestinatario.setSaldo(valorDeposito);
		contaRepository.save(contaDestinatario);
	}

	public void excluirConta(Long idConta) {
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
