package com.mballem.demoparkapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioCreateDto {
	/*
	 * Essa classe foi criada para que no momento de cadastro o usuário passe apenas
	 * o username e a senha,as outras infos são pra auditoria,então não tem
	 * necessidade de pedir para que o usuário passe elas
	 */

	@NotBlank
	@Email(message="formato do e-mail inválido")
	private String username;
	@NotBlank
	@Size(min=6,max=6)
	//A senha obrigatoriamente vai ter 6 caracteres
	private String password;

	public UsuarioCreateDto() {
		super();
	}

	public UsuarioCreateDto(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UsuarioCreateDTO [username=" + username + ", password=" + password + "]";
	}

}
