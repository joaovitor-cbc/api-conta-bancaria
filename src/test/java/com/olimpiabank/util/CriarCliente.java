package com.olimpiabank.util;

import com.olimpiabank.domain.model.Cliente;

public class CriarCliente {
	
	public static Cliente criarClienteParaSalvar() {
		Cliente cliente = new Cliente();
		cliente.setCpf("123.456.789.01");
		return cliente;
	}
	
	public static  Cliente criarClienteValido() {
		Cliente cliente = new Cliente();
		cliente.setCpf("123.456.789.01");
		cliente.setId(1L);
		return cliente;		
	}
	
	public static Cliente criarClienteValidoUpdate() {
		Cliente cliente = new Cliente();
		cliente.setCpf("123.456.789.01");
		cliente.setId(1L);
		return cliente;	
	}
}
