package com.olimpiabank.domain.controller;

import com.olimpiabank.DTO.ClienteInput;
import com.olimpiabank.DTO.ClienteModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Api("Controler Cliente Docs")
public interface ClienteControllerDocs {

    @ApiResponses(value = { @ApiResponse(code = 201, message = "Cria e retorna uma cliente"),
            @ApiResponse(code = 403, message = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(code = 500, message = "Foi gerada uma exceção"), })
    public ClienteModel criarCliente(@Valid @RequestBody ClienteInput clienteInput);
}
