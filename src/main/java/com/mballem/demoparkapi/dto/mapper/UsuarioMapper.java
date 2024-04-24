package com.mballem.demoparkapi.dto.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.mballem.demoparkapi.dto.UsuarioCreateDto;
import com.mballem.demoparkapi.dto.UsuarioResponseDto;
import com.mballem.demoparkapi.entity.Usuario;

public class UsuarioMapper {

	/*
	 * Transformando um usuarioCreateDto em usuario,porque pra inserir no banco a
	 * nossa interface UsuarioRepository apenas aceita objetos do tipo Usuario
	 */
	public static Usuario toUsuario(UsuarioCreateDto u) {
		return new ModelMapper().map(u, Usuario.class);

	}

	/*
	 * Qual a função desse método ? Quando realizamos operações no banco,devido a
	 * interface do UsuarioRepository os objetos vem modelados na classe
	 * Usuario,nela trazem inumeros atributos e entre eles senha,por exemplo. Não
	 * seria uma boa prática retornar uma senha,desse modo o método toDto esta
	 * mapeando para que no momento de retorno o objeto sera transformado de Usuario
	 * para UsuarioResponseDto,no qual possui menos atributos para serem
	 * retornados(apenas o username e o id).
	 */
	public static UsuarioResponseDto toDto(Usuario usuario) {
		// String role = usuario.getRole().name().replace("ROLE_", "");
		/*
		 * O .name() tem como função obter o nome do ENUM como uma String ,ao inves de
		 * ENUM
		 * 
		 * Usando apenas getRole vai retornar = Role.ROLE_ADMIN Usando o
		 * .getRole().name() vai retornar = ROLE_ADMIN
		 *
		 * PropertyMap<Usuario, UsuarioResponseDto> props = new PropertyMap<Usuario,
		 * UsuarioResponseDto>() {
		 * 
		 * @Override protected void configure() { map().setRole(role); } };
		 * 
		 * ModelMaper,precisa importar ele lá no POM.xml se não ele não vai funcinoar.
		 * Ele vai ser usado pra transformar de um objeto usuario,para um objeto
		 * usuarioResponseDto
		 *
		 * ModelMapper mapper = new ModelMapper(); mapper.addMappings(props); return
		 * mapper.map(usuario, UsuarioResponseDto.class);
		 * 
		 */

		String role = usuario.getRole().name().replace("ROLE_", "");
		UsuarioResponseDto usuarioResponseDto = new UsuarioResponseDto();
		usuarioResponseDto.setRole(role);
		usuarioResponseDto.setId(usuario.getId());
		usuarioResponseDto.setUsername(usuario.getUsername());
		return usuarioResponseDto;

	}

	public static List<UsuarioResponseDto> toListDto(List<Usuario> usuarios) {

		/*
		 * public static List<UsuarioResponseDto> toListDto(List<Usuario> usuarios) {
		 * List<UsuarioResponseDto> dtos = new ArrayList<>(); // Criamos uma lista vazia
		 * para armazenar os objetos UsuarioResponseDto
		 * 
		 * for (Usuario user : usuarios) { // Iteramos sobre cada usuário na lista de
		 * usuários UsuarioResponseDto dto = toDto(user); // Convertemos o usuário para
		 * um UsuarioResponseDto usando o método toDto dtos.add(dto); // Adicionamos o
		 * UsuarioResponseDto convertido à lista de dtos }
		 * 
		 * return dtos; // Retornamos a lista de UsuarioResponseDto }
		 * 
		 * 
		 * Para explicar a linha abaixo leve em consideração o bloco de código acima
		 */
		return usuarios.stream().map(user -> toDto(user)).collect(Collectors.toList());
		/*
		 * O stream tem como função pegar um usuario de cada vez,nesse caso ele seria
		 * como o nosso for;
		 * 
		 * 
		 * O map para é usado para transformar cada usuário em um UsuarioResponseDto.
		 * Para fazer isso, estamos chamando o método toDto, passando cada usuário como
		 * argumento. Isso é feito para cada usuário na lista.
		 * 
		 * 
		 * O collect será usado para criar uma nova lista com todos os novos usuáriosDTO's
		 */
	}

}
