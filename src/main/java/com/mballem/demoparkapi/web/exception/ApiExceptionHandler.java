package com.mballem.demoparkapi.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mballem.demoparkapi.exception.UsernameUniqueViolationException;
import com.mballem.demoparkapi.service.exception.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
/* Captura exceções e faz o tratamento delas */
public class ApiExceptionHandler {

	// Nesse metodo eu irei tratar a exceção de quando eu tento adicionar um email
	// que tem formato invalido,ou que já existe no
	// banco de dados.Também vale para os outros campos de senha,role.
	@ExceptionHandler(MethodArgumentNotValidException.class)

	public ResponseEntity<ErrorMessage> methodArgumentNotValidException(
			HttpServletRequest request, BindingResult result) {

		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.UNPROCESSABLE_ENTITY,"campos inválidos ",result));
		// erro quando nao da pra processar o objeto enviado
	}

	
	
	@ExceptionHandler(UsernameUniqueViolationException.class)
	public ResponseEntity<ErrorMessage> UsernameUniqueViolationExpcetion(RuntimeException ex,
			HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.CONFLICT).contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.CONFLICT,ex.getMessage()));
														//O conflict é usado quando há um conflito
														//de informações,no caso um usuário tentando
														//cadastrar um e-mail que já existe
	}
	
	
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<ErrorMessage> EntityNotFoundException(RuntimeException ex,
			HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON)
				.body(new ErrorMessage(request, HttpStatus.NOT_FOUND,ex.getMessage()));
														//O conflict é usado quando há um conflito
														//de informações,no caso um usuário tentando
														//cadastrar um e-mail que já existe
	}

	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ErrorMessage> NullPointerException(RuntimeException ex,HttpServletRequest request){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(new ErrorMessage(request,HttpStatus.BAD_REQUEST,ex.getMessage()));
	}
	
	
}
