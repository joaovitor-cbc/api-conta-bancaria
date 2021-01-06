package com.apicontabancaria.repository;

import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.apicontabancaria.domain.model.Cliente;
import com.apicontabancaria.domain.repository.ClienteRepository;
import com.apicontabancaria.exceptionhandler.ClienteExceptionBadRequest;
import com.apicontabancaria.util.CriarCliente;

@DataJpaTest
@DisplayName("Teste de repositorio cliente")
public class ClienteRepositoryTest {

	@Autowired
	private ClienteRepository clienteRepository;

	@Test
	@DisplayName("Persistindo cliente")
	void save_PersistindoCliente() {
		Cliente cliente = CriarCliente.criarClienteParaSalvar();
		Cliente clienteSalvo = this.clienteRepository.save(cliente);

		Assertions.assertThat(clienteSalvo).isNotNull();
		Assertions.assertThat(clienteSalvo.getId()).isNotNull();
		Assertions.assertThat(clienteSalvo.getCpf()).isEqualTo(cliente.getCpf());
	}

	@Test
	@DisplayName("Atualizando cliente")
	void save_AtualizandoCliente() {
		Cliente cliente = CriarCliente.criarClienteParaSalvar();
		Cliente clienteSalvo = this.clienteRepository.save(cliente);

		clienteSalvo.setCpf("123.456.789.11");

		Cliente clienteUpdate = this.clienteRepository.save(clienteSalvo);

		Assertions.assertThat(clienteUpdate).isNotNull();
		Assertions.assertThat(clienteUpdate.getId()).isNotNull();
		Assertions.assertThat(clienteUpdate.getId()).isEqualTo(clienteSalvo.getId());
	}

	@Test
	@DisplayName("Deletando cliente")
	void delete_RemovendoCliente() {
		Cliente cliente = CriarCliente.criarClienteParaSalvar();
		Cliente clienteSalvo = this.clienteRepository.save(cliente);

		this.clienteRepository.delete(clienteSalvo);

		Optional<Cliente> clienteOptional = this.clienteRepository.findById(clienteSalvo.getId());
		Assertions.assertThat(clienteOptional).isEmpty();
	}

	@Test
	@DisplayName("Buscar cliente por cpf")
	void BuscarClientePorCpf() {
		Cliente cliente = CriarCliente.criarClienteParaSalvar();

		Cliente clienteSalvo = this.clienteRepository.save(cliente);
		String cpf = clienteSalvo.getCpf();

		Optional<Cliente> clienteOptional = this.clienteRepository.findByCpf(cpf);
		Assertions.assertThat(clienteOptional).isNotEmpty();
		Assertions.assertThat(clienteOptional).contains(clienteSalvo);
		Assertions.assertThat(clienteOptional).isNotNull();
	}

	@Test
	@DisplayName("Salve ou lance uma exceção ConstraintViolationException quando o campo do cpf é vazio")
	void save_ouLancConstraintViolationException_QuandoCpfVazio() {
		Cliente cliente = new Cliente();
		Assertions.assertThatThrownBy(() -> this.clienteRepository.save(cliente))
				.isInstanceOf(ConstraintViolationException.class);
	}
}
