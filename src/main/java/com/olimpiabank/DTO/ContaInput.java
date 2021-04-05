package com.olimpiabank.DTO;

public class ContaInput {


    private Double saldo;


    private String tipoConta;

    public ContaInput() {
    }

    public ContaInput(Double saldo,String tipoConta) {
        this.saldo = saldo;
        this.tipoConta = tipoConta;
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
}
