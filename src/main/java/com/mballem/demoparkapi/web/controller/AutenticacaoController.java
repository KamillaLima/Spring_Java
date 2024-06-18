package com.mballem.demoparkapi.web.controller;


import com.mballem.demoparkapi.jwt.JwtToken;
import com.mballem.demoparkapi.jwt.JwtUserDetailsService;
import com.mballem.demoparkapi.web.dto.UsuarioLoginDto;
import com.mballem.demoparkapi.web.exception.ErrorMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1")
public class AutenticacaoController {

    private final JwtUserDetailsService jwtUserDetailsService;
    private final AuthenticationManager authenticationManager;

    //A interrogação é para que no retorno do catch não de problema , já que nele sera um retorno de bad request , enquanto
    //no try o retorno será um token
    @PostMapping("/auth")
    public ResponseEntity<?> autenticar (@RequestBody @Valid UsuarioLoginDto usuarioLoginDto , HttpServletRequest request){
        log.info("Processo de autenticação pelo login" , usuarioLoginDto.getUsername());
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(usuarioLoginDto.getUsername() , usuarioLoginDto.getPassword());
            authenticationManager.authenticate(authenticationToken);
            JwtToken token = jwtUserDetailsService.getTokenAuthentication(usuarioLoginDto.getUsername());
            return ResponseEntity.ok(token);
        }catch (AuthenticationException ex){
            log.warn("ERRO " + ex.getMessage());
        }
        return ResponseEntity.badRequest().body(new ErrorMessage(request , HttpStatus.BAD_REQUEST, "Credenciais invalidas"));
    }
}
