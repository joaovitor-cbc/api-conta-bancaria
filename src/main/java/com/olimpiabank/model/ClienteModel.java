package com.olimpiabank.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ClienteModel {

    @NotNull
    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String cpf;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
