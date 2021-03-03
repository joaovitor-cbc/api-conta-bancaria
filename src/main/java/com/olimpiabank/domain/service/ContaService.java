package com.olimpiabank.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.olimpiabank.domain.model.Cliente;
import com.olimpiabank.domain.model.Conta;
import com.olimpiabank.domain.model.StatusConta;
import com.olimpiabank.domain.repository.ClienteRepository;
import com.olimpiabank.domain.repository.ContaRepository;
import com.olimpiabank.exceptionhandler.ClienteExceptionNotFound;
import com.olimpiabank.exceptionhandler.ContaExceptionBadRequest;
import com.olimpiabank.exceptionhandler.ContaExceptionNotFound;
import com.olimpiabank.request.DadosDeposito;
import com.olimpiabank.request.DadosTransferencia;

@Service
public class ContaService {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	public Conta criarConta(Conta contaInput, Long idCliente) {
		return validarAberturaConta(contaInput, idCliente);
	}

	private Conta validarAberturaConta(Conta contaInput, Long idCliente) {
		Cliente clienteEntity =  validarCliente(idCliente);
		//possuiContaAtiva(idCliente);
		Conta conta = contaInput;
		conta.setCliente(clienteEntity);
		conta.setStatusConta(StatusConta.ABERTO);
		return contaRepository.save(conta);
	}

	private void possuiContaAtiva(Long idCliente){
		Optional<Cliente> clienteEntity = clienteRepository.findById(idCliente);
		Long idConta = clienteEntity.get().getConta().getId();
		Optional<Conta> optionalConta = contaRepository.findById(idConta);
		if(optionalConta.isPresent()){
			if(optionalConta.get().getStatusConta().equals(StatusConta.ABERTO)){
				throw new ContaExceptionBadRequest("Cliente já possui conta"
						+optionalConta.get().getStatusConta());
			}
		}
	}

	private Cliente validarCliente(Long idCliente){
		Cliente clienteEntity = clienteRepository.findById(idCliente)
				.orElseThrow(() -> new ClienteExceptionNotFound("Cliente não encontrado..."));
		return clienteEntity;
	}

	public Conta consultarSaldo(Long idConta) {
		Conta conta = contaRepository.findById(idConta)
				.orElseThrow(() -> new ContaExceptionNotFound("Conta não encontrada..."));
		return conta;
	}

	public Conta buscarConta(Long idConta) {
		Conta conta = contaRepository.findById(idConta)
				.orElseThrow(() -> new ContaExceptionBadRequest("Conta não cadastrada..."));
		if (conta.getStatusConta().equals(StatusConta.FECHADA)) {
			throw new ContaExceptionBadRequest("Conta com status " + conta.getStatusConta());
		}
		return conta;
	}

	public void Transferencia(DadosTransferencia dadosTransferencia) {
		if (dadosTransferencia.getIdContaRemetente() == null || dadosTransferencia.getIdContaDestinatario() == null
				|| dadosTransferencia.getValorTranferencia() == null) {
			throw new ContaExceptionBadRequest("há campos vazios, necessario preencher todos os campos!");
		}
		Conta contaRemetente = contaRepository.findById(dadosTransferencia.getIdContaRemetente())
				.orElseThrow(() -> new ContaExceptionNotFound("Conta remetente não cadastrada..."));
		Conta contaDestinatario = contaRepository.findById(dadosTransferencia.getIdContaDestinatario())
				.orElseThrow(() -> new ContaExceptionNotFound("Conta destinataria não cadastrada..."));
		realizarTransferencia(contaRemetente, contaDestinatario, dadosTransferencia.getValorTranferencia());
	}
	
	@Transactional
	private void realizarTransferencia(Conta remetente, Conta destinatario, Double valor) {

		if (valor > remetente.getSaldo()) {
			throw new ContaExceptionBadRequest("Saldo insuficiente para fazer tranferencia.");
		} else if (destinatario.getStatusConta().equals(StatusConta.FECHADA)) {
			throw new ContaExceptionBadRequest("Conta com status " + destinatario.getStatusConta());
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
			throw new ContaExceptionBadRequest("há campos vazios, necessario preencher todos os campos!");
		}
		Conta contaDestinatario = contaRepository.findById(dadosDeposito.getIdConta())
				.orElseThrow(() -> new ContaExceptionNotFound("Conta não encontrada..."));
		if (contaDestinatario.getStatusConta().equals(StatusConta.FECHADA)) {
			throw new ContaExceptionBadRequest("Conta com status " + contaDestinatario.getStatusConta());
		}
		Double valorDeposito = contaDestinatario.getSaldo() + dadosDeposito.getValorDeposito();
		contaDestinatario.setSaldo(valorDeposito);
		contaRepository.save(contaDestinatario);
	}

	public void excluirConta(Long idConta) {
		if (contaExistePorId(idConta)) {
			contaRepository.deleteById(idConta);
		}
	}

	public boolean contaExistePorId(Long idConta) {
		Optional<Conta> contaOptional = contaRepository.findById(idConta);
		if (contaOptional.isPresent()) {
			return true;
		} else {
			throw new ContaExceptionNotFound("Conta não encontrada...");
		}
	}
}
