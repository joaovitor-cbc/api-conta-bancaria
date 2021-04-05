package com.olimpiabank.domain.service;

import com.olimpiabank.DTO.ClienteInput;
import com.olimpiabank.DTO.ClienteModel;
import com.olimpiabank.domain.model.Cliente;
import com.olimpiabank.domain.repository.ClienteRepository;
import com.olimpiabank.exceptionhandler.ClienteExceptionBadRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * <h2>Metodo para criar Cliente.</h2>
	 * <p> Recebe por parametro clienteInput e retorna ClienteModel.</p>
	 * @param clienteInput
	 * Tipo parametros: contaInput – o tipo ClienteInput o valor
	 * @return ClienteModel
	 * @throws ClienteExceptionBadRequest  se o cliente já existe
	 * @author joao_vitor
	 * @version 1.0.1
	 * @since  1.0.0
	 */
	public ClienteModel criarCliente(ClienteInput clienteInput) {
		Cliente clienteEntityInput = toEntityCliente(clienteInput);
		Optional<Cliente> clienteOptinal = clienteRepository.findByCpf(clienteEntityInput.getCpf());
		if (clienteOptinal.isPresent()) {
			throw new ClienteExceptionBadRequest("Cliente já cadastrado...");
		}
		return toModelCliente(clienteRepository.save(clienteEntityInput));
	}

	private Cliente toEntityCliente(ClienteInput clienteInput){
		return modelMapper.map(clienteInput, Cliente.class);
	}

	private ClienteModel toModelCliente(Cliente cliente){
		return modelMapper.map(cliente, ClienteModel.class);
	}

}
