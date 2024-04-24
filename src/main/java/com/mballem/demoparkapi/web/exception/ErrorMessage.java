package com.mballem.demoparkapi.web.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.servlet.http.HttpServletRequest;

public class ErrorMessage {

	private String path;
	// Se trata do caminho em que a solicitação foi feita
	// exemplo : /api/v1/usuarios
	private String method;
	// Qual foi o método que fez a exceção ser lançada
	private int status;
	// Numero do HTTTP STATUS
	private String statusText;
	// Nome do HTTP STATUS
	private String message;
	// Mensagem que eu posso definir quando a exceção for lançada ou posso usar um
	// getMessage pro spring já gerar pra mim
	@JsonInclude(JsonInclude.Include.NON_NULL)
	//Com essa anotação,se o campo estiver nulo ele não será mostrado no insomnia
	private Map<String, String> errors;
	// Nome do campo que deu problema e qual foi o problema
	/*
	 * "errors": { "password": "tamanho deve ser entre 6 e 6", "username":
	 * "formato do e-mail inválido" }
	 */

	/*
	 * Usando essa classe deve retornar isso aqui quando a requisição der problema :
	 * { "path": "/api/v1/usuarios", "method": "POST", "status": 422, "statusText":
	 * "Unprocessable Entity", "message": "campos inválidos ", "errors": {
	 * "password": "tamanho deve ser entre 6 e 6", "username":
	 * "formato do e-mail inválido" } }
	 */
	public ErrorMessage() {
		super();
	}

	public String getPath() {
		return path;
	}

	public String getMethod() {
		return method;
	}

	public int getStatus() {
		return status;
	}

	public String getStatusText() {
		return statusText;
	}

	public String getMessage() {
		return message;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public ErrorMessage(HttpServletRequest request, HttpStatus status, String message) {
		this.path = request.getRequestURI();
		this.method = request.getMethod();
		this.status = status.value();
		this.statusText = status.getReasonPhrase();
		this.message = message;
	}

	public ErrorMessage(HttpServletRequest request, HttpStatus status, String message, BindingResult results) {
		this.path = request.getRequestURI();
		this.method = request.getMethod();
		this.status = status.value();
		this.statusText = status.getReasonPhrase();
		this.message = message;
		addErrors(results);
	}

	private void addErrors(BindingResult results) {
		this.errors = new HashMap<>();
		for (FieldError fieldError : results.getFieldErrors()) {
			this.errors.put(fieldError.getField(), fieldError.getDefaultMessage());
		}

	}

	@Override
	public String toString() {
		return "ErrorMessage [path=" + path + ", method=" + method + ", status=" + status + ", statusText=" + statusText
				+ ", message=" + message + ", errors=" + errors + "]";
	}

}
