package com.olimpiabank.DTO;

import io.swagger.annotations.ApiModelProperty;

public class ClienteModel {

    @ApiModelProperty(value = "id do cliente.")
    private Long id;

    @ApiModelProperty(value = "Nome do cliente.")
    private String nome;

    @ApiModelProperty(value = "Cpf do cliente.")
    private String cpf;

    public ClienteModel() {
    }

    public ClienteModel(Long id, String nome, String cpf) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
    }

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
