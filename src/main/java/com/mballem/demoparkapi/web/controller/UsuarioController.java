package com.mballem.demoparkapi.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mballem.demoparkapi.dto.UsuarioCreateDto;
import com.mballem.demoparkapi.dto.UsuarioResponseDto;
import com.mballem.demoparkapi.dto.UsuarioSenhaDto;
import com.mballem.demoparkapi.dto.mapper.UsuarioMapper;
import com.mballem.demoparkapi.entity.Usuario;
import com.mballem.demoparkapi.service.UsuarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping
	public ResponseEntity<UsuarioResponseDto> create(@RequestBody @Valid UsuarioCreateDto usuarioDto) {
		// O responseEntity Encapsula o objeto usuario,que sera transformado num JSON
		// e enviado pro cliente.ele guarda tb o cabeçalho e a resposta,caso tenham.
		Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(usuarioDto));
		return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
		// Caso o usuario não seja valido porque o usuario colocou uma senha com o
		// tamanho menor do que 6 caracteres,por exemplo,será lançado um httpStatus 400-BAD REQUEST
	}

	@GetMapping("/{id}")
	public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id) {
		Usuario user = usuarioService.buscarPorId(id);
		return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toDto(user));
	}

	@PatchMapping("/{id}")
	public ResponseEntity<UsuarioResponseDto> updatePassword(@PathVariable Long id, @RequestBody UsuarioSenhaDto dto) {
		Usuario user = usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
		return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toDto(user));
	}

	@GetMapping
	public ResponseEntity<List<UsuarioResponseDto>> getAll() {
		List<Usuario> usuarios = usuarioService.buscarTodos();
		return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toListDto(usuarios));
	}

}
