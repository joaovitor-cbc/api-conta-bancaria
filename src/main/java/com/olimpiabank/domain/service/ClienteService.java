package com.olimpiabank.domain.service;

import java.util.Optional;

import com.olimpiabank.exceptionhandler.ContaExceptionBadRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.olimpiabank.domain.model.Cliente;
import com.olimpiabank.domain.repository.ClienteRepository;
import com.olimpiabank.exceptionhandler.ClienteExceptionBadRequest;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * <h2>Metodo para criar Cliente.</h2>
	 * <p> Recebe por parametro clienteInput e retorna Cliente.</p>
	 * @param clienteInput
	 * Tipo parametros: contaInput – o tipo Cliente o valor
	 * @return Cliente
	 * @throws ClienteExceptionBadRequest  se o cliente já existe
	 * @author joao_vitor
	 * @version 1.0.0
	 * @since  1.0.0
	 */
	public Cliente criarCliente(Cliente clienteInput) {
		Optional<Cliente> clienteOptinal = clienteRepository.findByCpf(clienteInput.getCpf());
		if (clienteOptinal.isPresent()) {
			throw new ClienteExceptionBadRequest("Cliente já cadastrado...");
		}
		return clienteRepository.save(clienteInput);
	}

}
