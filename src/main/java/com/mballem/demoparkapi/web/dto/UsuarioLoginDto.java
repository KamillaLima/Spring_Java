package com.mballem.demoparkapi.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioLoginDto {

    @NotBlank
    @Email(message="formato do e-mail inválido")
    private String username;
    @NotBlank
    @Size(min=6,max=6)
    private String password;

    public UsuarioLoginDto() {
        super();
    }

    public UsuarioLoginDto(String username, String password) {
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
