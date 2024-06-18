package com.mballem.demoparkapi.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SecondaryRow;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JwtToken {

    //Classe que vai ser responsável por retornar o token que foi gerado
    private String token;

}
