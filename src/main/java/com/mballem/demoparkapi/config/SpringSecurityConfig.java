package com.mballem.demoparkapi.config;

import com.mballem.demoparkapi.jwt.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@Configuration
@EnableWebMvc

public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return  http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(auth -> auth.
                        requestMatchers(HttpMethod.POST,"/api/v1/usuarios").permitAll().
                        requestMatchers(HttpMethod.POST,"/api/v1/auth").permitAll().
                        anyRequest().authenticated()

                ).sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).
                addFilterBefore(jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class).
                build();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter(){
        return  new JwtAuthorizationFilter();
    }



    @Bean
    //Esse método será responsável para que a senha sempre seja encriptada de uma forma diferente , então se duas pessoas
    //tiverem a mesma senha , ela deverá ser encriptada de maneira diferente para cada uma delas
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
