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
		Conta conta = contaInput;
		Optional<Cliente> cliente = clienteRepository.findById(idCliente);
		if (cliente.isEmpty()) {
			throw new NegocioException("Cliente não cadastrado...");
		}
		conta.setCliente(cliente.get());
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
		if (conta.getStatusConta().equals("FECHADO")) {
			throw new NegocioException("Conta com status " + conta.getStatusConta());
		}
		return conta;
	}

	public void Transferencia(Double valorTranferencia, Long idContaRemetente, Long idContaDestinatario) {
		Conta contaRemetente = contaRepository.findById(idContaRemetente)
				.orElseThrow(() -> new NegocioException("Conta do remetente não cadastrada..."));
		Conta contaDestinatario = contaRepository.findById(idContaDestinatario)
				.orElseThrow(() -> new NegocioException("Conta Destinataria não cadastrada..."));
		if (valorTranferencia > contaRemetente.getSaldo()) {
			throw new NegocioException("Saldo insuficiente para fazer tranferencia.");
		} else if (contaDestinatario.getStatusConta().equals("FECHADO")) {
			throw new NegocioException("Conta com status " + contaDestinatario.getStatusConta());
		} else if (valorTranferencia <= contaRemetente.getSaldo()) {
			Double subtracaoTranferencia = contaRemetente.getSaldo() - valorTranferencia;
			contaRemetente.setSaldo(subtracaoTranferencia);
			Double adicaoTranferencia = contaDestinatario.getSaldo() + valorTranferencia;
			contaDestinatario.setSaldo(adicaoTranferencia);
			contaRepository.save(contaRemetente);
			contaRepository.save(contaDestinatario);
		}
	}

	public void deposito(Long idConta, Double valor) {
		Conta contaDestinatario = contaRepository.findById(idConta)
				.orElseThrow(() -> new NegocioException("Conta não encontrada.."));
		if (contaDestinatario.getStatusConta().equals("FECHADO")) {
			throw new NegocioException("Conta com status " + contaDestinatario.getStatusConta());
		}
		Double valorDeposito = contaDestinatario.getSaldo() + valor;
		contaDestinatario.setSaldo(valorDeposito);
		contaRepository.save(contaDestinatario);
	}

	public void excluirConta(Long idConta) {
		contaRepository.deleteById(idConta);
	}
}
