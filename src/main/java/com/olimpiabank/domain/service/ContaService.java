package com.olimpiabank.domain.service;

import java.util.Optional;


import org.modelmapper.ModelMapper;
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

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@Service
public class ContaService {

	@Autowired
	private ContaRepository contaRepository;

	@Autowired
	private ClienteRepository clienteRepository;

    /**
     * <h2>Metodo para criar uma conta.</h2>
     * <p> Recebe por parametro contaInput, idCliente,
     * e retorna Conta.</p>
     * @param contaInput
     * @param idCliente
     * Tipo parametros: contaInput – o tipo Conta o valor, idCliente – o tipo Long o valor
     * @return Conta
     * @author joao_vitor
     * @version 1.0.0
     * @since  1.0.0
     */
	public Conta criarConta(Conta contaInput, Long idCliente) {
		return validarAberturaConta(contaInput, idCliente);
	}

    /**
     * <h2>Metodo para validar abertura de conta.</h2>
     * <p> Recebe por parametro contaInput, idCliente,
     * e retorna Conta.</p>
     * @param contaInput
     * @param idCliente
     * Tipo parametros: contaInput – o tipo Conta o valor, idCliente – o tipo Long o valor
     * @return Conta
     * @author joao_vitor
     * @version 1.0.0
     * @since  1.0.0
     */
	private Conta validarAberturaConta(Conta contaInput, Long idCliente) {
		Cliente clienteEntity = validarCliente(idCliente);
		return possuiContaAtiva(contaInput,clienteEntity);
	}

    /**
     * <h2>Metodo para verificar ser possui conta ativa.</h2>
     * <p> Recebe por parametro contaInput, cliente,
     * e retorna Conta.</p>
     * @param contaInput
     * @param cliente
     * Tipo parametros: contaInput – o tipo Conta o valor, cliente – o tipo Cliente o valor
     * @return Conta
     * @throws ContaExceptionBadRequest  se o cliente já possuir uma conta
     * @author joao_vitor
     * @version 1.0.0
     * @since  1.0.0
     */
	private Conta possuiContaAtiva(Conta contaInput,Cliente cliente){
		Optional<Conta> conta = contaRepository.findById(cliente.getId());
		if(conta.isPresent()){
			if(cliente.getId().equals(conta.get().getId())){
				throw new ContaExceptionBadRequest("Cliente já possui conta ativa, status: "
						+conta.get().getStatusConta() );
			}
		} else {
			contaInput.setId(cliente.getId());
			contaInput.setCliente(cliente);
			contaInput.setStatusConta(StatusConta.ABERTO);
			return contaRepository.save(contaInput);
		}
		return null;
	}

    /**
     * <h2>Metodo para validar se o cliente é valido.</h2>
     * <p> Recebe por parametro cliente,
     * e retorna CLiente.</p>
     * @param idCliente
     * Tipo parametros: idCliente – o tipo Long o valor
     * @return Conta
     * @throws ClienteExceptionNotFound  se o cliente não for valido
     * @author joao_vitor
     * @version 1.0.0
     * @since  1.0.0
     */
	private Cliente validarCliente(Long idCliente){
		Cliente clienteEntity = clienteRepository.findById(idCliente)
				.orElseThrow(() -> new ClienteExceptionNotFound("Cliente não encontrado..."));
		return clienteEntity;
	}

    /**
     * <h2>Metodo para consulta o saldo.</h2>
     * <p> Recebe por parametro idConta,
     * e retorna Conta.</p>
     * @param idConta
     * Tipo parametros: idConta – o tipo Long o valor
     * @return Conta
     * @throws ContaExceptionNotFound  se o conta não for valida
     * @author joao_vitor
     * @version 1.0.0
     * @since  1.0.0
     */
	public Conta consultarSaldo(Long idConta) {
		Conta conta = contaRepository.findById(idConta)
				.orElseThrow(() -> new ContaExceptionNotFound("Conta não encontrada..."));
		return conta;
	}

    /**
     * <h2>Metodo para buscar uma conta.</h2>
     * <p> Recebe por parametro idConta,
     * e retorna Conta.</p>
     * @param idConta
     * Tipo parametros: idConta – o tipo Long o valor
     * @return Conta
     * @throws ContaExceptionBadRequest  se a conta não for valida
     * @author joao_vitor
     * @version 1.0.0
     * @since  1.0.0
     */
	public Conta buscarConta(Long idConta) {
		Conta conta = contaRepository.findById(idConta)
				.orElseThrow(() -> new ContaExceptionBadRequest("Conta não cadastrada..."));
		if (conta.getStatusConta().equals(StatusConta.FECHADA)) {
			throw new ContaExceptionBadRequest("Conta com status " + conta.getStatusConta());
		}
		return conta;
	}

    /**
     * <h2>Metodo para validar transferencia.</h2>
     * <p> Recebe por parametro dadosTransferencia,
     * e retorna void.</p>
     * @param dadosTransferencia
     * Tipo parametros: dadosTransferencia – o tipo DadosTransferencia o valor
     * @return void
     * @throws ContaExceptionBadRequest  <p>se o id da conta remetente for null ou id da conta
     * destinataria for null ou valor da tranferencia for null</p>
     * @throws ContaExceptionNotFound se o id da conta remetente ou destinataria não existirem
     * @author joao_vitor
     * @version 1.0.0
     * @since  1.0.0
     */
	public void validarTransferencia(DadosTransferencia dadosTransferencia) {
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

    /**
     * <h2>Metodo para realizar transferencia.</h2>
     * <p> Recebe por parametro dadosTransferencia,
     * e retorna void.</p>
     * @param remetenteInput
     * @param destinatarioInput
     * @param valor
     * Tipo parametros: <p>remetenteInput – o tipo Conta o valor,
     * destinatarioInput - o tipo Contao valor, valor - o tipo Double o valor</p>
     * @return void
     * @throws ContaExceptionBadRequest <p>se o saldo da conta remetente for insuficiente,
     * ou se o status da conta destinataria for igual FECHADO</p>
     * @author joao_vitor
     * @version 1.0.0
     * @since  1.0.0
     */
	@Transactional
	private void realizarTransferencia(Conta remetenteInput, Conta destinatarioInput, Double valor) {

		if (valor > remetenteInput.getSaldo()) {
			throw new ContaExceptionBadRequest("Saldo insuficiente para fazer tranferencia.");
		} else if (destinatarioInput.getStatusConta().equals(StatusConta.FECHADA)) {
			throw new ContaExceptionBadRequest("Conta com status " + destinatarioInput.getStatusConta());
		} else if (remetenteInput.getSaldo() >= valor) {
			Double subtracaoTranferencia = remetenteInput.getSaldo() - valor;
			remetenteInput.setSaldo(subtracaoTranferencia);
			Double adicaoTranferencia = destinatarioInput.getSaldo() + valor;
			destinatarioInput.setSaldo(adicaoTranferencia);
			contaRepository.save(remetenteInput);
			contaRepository.save(destinatarioInput);
		}
	}

    /**
     * <h2>Metodo para realizar deposito.</h2>
     * <p> Recebe por parametro dadosDeposito,
     * e retorna void.</p>
     * @param dadosDeposito
     * Tipo parametros: <p>dadosDeposito – o tipo DadosDeposito o valor</p>
     * @return void
     * @throws ContaExceptionBadRequest <p>se o id da conta remetente ou saldo for insuficiente,
     * ou se a conta destinatrio não existir ou se o status dela for FECHADA</p>
     * @author joao_vitor
     * @version 1.0.0
     * @since  1.0.0
     */
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

    /**
     * <h2>Metodo para excluir uma conta.</h2>
     * <p> Recebe por parametro idConta,
     * e retorna void.</p>
     * @param idConta
     * Tipo parametros: <p>idConta – o tipo Long o valor</p>
     * @return void
     * @author joao_vitor
     * @version 1.0.0
     * @since  1.0.0
     */
	public void excluirConta(Long idConta) {
		if (contaExistePorId(idConta)) {
			contaRepository.deleteById(idConta);
		}
	}

    /**
     * <h2>Metodo para verificarse a conta existe.</h2>
     * <p> Recebe por parametro dadosDeposito,
     * e retorna boolean.</p>
     * @param idConta
     * Tipo parametros: <p>dadosDeposito – o tipo DadosDeposito o valor</p>
     * @return boolean
     * @throws ContaExceptionNotFound <p>se a conta não existir</p>
     * @author joao_vitor
     * @version 1.0.0
     * @since  1.0.0
     */
	public boolean contaExistePorId(Long idConta) {
		Optional<Conta> contaOptional = contaRepository.findById(idConta);
		if (contaOptional.isPresent()) {
			return true;
		} else {
			throw new ContaExceptionNotFound("Conta não encontrada...");
		}
	}

}
