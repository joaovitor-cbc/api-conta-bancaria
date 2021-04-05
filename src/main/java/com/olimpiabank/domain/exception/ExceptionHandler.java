package com.olimpiabank.domain.exception;

import com.olimpiabank.exceptionhandler.ClienteExceptionBadRequest;
import com.olimpiabank.exceptionhandler.ClienteExceptionNotFound;
import com.olimpiabank.exceptionhandler.ContaExceptionBadRequest;
import com.olimpiabank.exceptionhandler.ContaExceptionNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;

@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private Problema problema;
	
	@org.springframework.web.bind.annotation.ExceptionHandler(ContaExceptionBadRequest.class)
	public ResponseEntity<Object> handleContaExceptionBadRequest(
            com.olimpiabank.exceptionhandler.ContaExceptionBadRequest ex, WebRequest request) {
		var status = HttpStatus.BAD_REQUEST;
		problema.setStatus(status.value());
		problema.setTitulo(ex.getMessage());
		problema.setDataHora(OffsetDateTime.now());
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(ContaExceptionNotFound.class)
	public ResponseEntity<Object> handleContaExceptionNotFound(
            com.olimpiabank.exceptionhandler.ContaExceptionNotFound ex, WebRequest request) {
		var status = HttpStatus.NOT_FOUND;
		problema.setStatus(status.value());
		problema.setTitulo(ex.getMessage());
		problema.setDataHora(OffsetDateTime.now());
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(ClienteExceptionBadRequest.class)
	public ResponseEntity<Object> handleClienteExceptionBadRequest(
            com.olimpiabank.exceptionhandler.ClienteExceptionBadRequest ex, WebRequest request) {
		var status = HttpStatus.BAD_REQUEST;
		problema.setStatus(status.value());
		problema.setTitulo(ex.getMessage());
		problema.setDataHora(OffsetDateTime.now());
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(ClienteExceptionNotFound.class)
	public ResponseEntity<Object> handleClienteExceptionNotFound(
            com.olimpiabank.exceptionhandler.ClienteExceptionNotFound ex, WebRequest request) {
		var status = HttpStatus.NOT_FOUND;
		problema.setStatus(status.value());
		problema.setTitulo(ex.getMessage());
		problema.setDataHora(OffsetDateTime.now());
		return handleExceptionInternal(ex, problema, new HttpHeaders(), status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		var campos = new ArrayList<Problema.Campo>();
		for (ObjectError erro : ex.getBindingResult().getAllErrors()) {
			String nome = ((FieldError) erro).getField();
			String mensagem = messageSource.getMessage(erro, LocaleContextHolder.getLocale());
		}
		problema.setStatus(status.value());
		problema.setTitulo("Um ou mais campos estão inválidos." + " Faça o preenchimento correto e faça novamente");
		problema.setDataHora(OffsetDateTime.now());
		problema.setCampos(campos);
		return super.handleExceptionInternal(ex, problema, headers, status, request);
	}
}
