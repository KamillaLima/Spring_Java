package com.mballem.demoparkapi.config;

import java.util.TimeZone;

import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class SpringTimezoneConfig {

	@PostConstruct
	//Após a classe ser inicializada,o construtor é iniciado e logo em seguida esse metodo também é
	public void timeZoneConfig() {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
		//usando o metodo pra passar o timeZone de são paulo
	}
}
