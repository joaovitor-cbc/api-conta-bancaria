package com.olimpiabank.DTO;

import javax.validation.constraints.NotBlank;

public class ClienteInput {


    @NotBlank
    private String nome;


    @NotBlank
    private String cpf;

    public ClienteInput(String nome, String cpf) {
        this.nome = nome;
        this.cpf = cpf;
    }

    public ClienteInput() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
