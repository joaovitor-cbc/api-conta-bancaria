package com.olimpiabank.domain.controller;

import com.olimpiabank.DTO.ContaInput;
import com.olimpiabank.DTO.ContaModel;
import com.olimpiabank.DTO.DadosDeposito;
import com.olimpiabank.DTO.DadosTransferencia;
import com.olimpiabank.domain.model.Conta;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface ContaControllerDocs {

    @ApiResponses(value = { @ApiResponse(code = 200, message = "Retorna uma conta"),
            @ApiResponse(code = 404, message = "Foi gerada uma exceção"), })
    public ResponseEntity<ContaModel> consultaSaldo(@PathVariable Long idConta);

    @ApiResponses(value = { @ApiResponse(code = 201, message = "Cria e retorna uma conta"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"), })
    public Conta abrirConta(@Valid @RequestBody ContaInput contaInput, @PathVariable Long idCliente);

    @ApiResponses(value = { @ApiResponse(code = 200, message = "Tranferencia de dinheiro"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 404, message = "Foi gerada uma exceção"), })
    public ResponseEntity<ContaModel> tranferirDinheiro(@RequestBody DadosTransferencia dadosTransferencia);

    @ApiResponses(value = { @ApiResponse(code = 200, message = "Deposita Dinheiro"),
            @ApiResponse(code = 404, message = "Foi gerada uma exceção"), })
    public ResponseEntity<ContaModel> depositarDinheiro(@RequestBody DadosDeposito dadosDeposito);

    @ApiResponses(value = { @ApiResponse(code = 204, message = "Apaga uma conta"),
            @ApiResponse(code = 404, message = "Foi gerada uma exceção"), })
    public ResponseEntity<Void> excluirConta(@PathVariable Long idConta);
}
