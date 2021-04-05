package com.olimpiabank.DTO;

import com.olimpiabank.domain.model.StatusConta;
import io.swagger.annotations.ApiModelProperty;

public class ContaModel {

    @ApiModelProperty(value = "id da conta")
    private Long id;

    @ApiModelProperty(value = "Saldo da conta")
    private Double saldo;

    @ApiModelProperty(value = "tipo da conta")
    private String tipoConta;

    @ApiModelProperty(value = "Status da conta")
    private StatusConta statusConta;


    public ContaModel() {
    }

    public ContaModel(Long id, Double saldo, String tipoConta) {
        this.id = id;
        this.saldo = saldo;
        this.tipoConta = tipoConta;
    }

    public ContaModel(Long id, Double saldo, String tipoConta, StatusConta statusConta) {
        this.id = id;
        this.saldo = saldo;
        this.tipoConta = tipoConta;
        this.statusConta = statusConta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(String tipoConta) {
        this.tipoConta = tipoConta;
    }

    public StatusConta getStatusConta() {
        return statusConta;
    }

    public void setStatusConta(StatusConta statusConta) {
        this.statusConta = statusConta;
    }

}
