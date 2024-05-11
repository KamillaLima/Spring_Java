package com.mballem.demoparkapi.web.controller;

import java.util.List;

import com.mballem.demoparkapi.web.exception.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Usuários ", description = "Contém todas as operações pra fazer o CRUD do usuário")
//É referente ao processo de documentação da minha API
@RestController
@RequestMapping("api/v1/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Operation(
            summary = "Criar um novo usuário"
            , description = "Recurso responsável por criar um novo usuário",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Criado com sucesso", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "409" , description = "Usuario email já existe", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422" , description = "Dados de entrada invalidos", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    //Referente a documentação do meu método
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@RequestBody @Valid UsuarioCreateDto usuarioDto) {
        // O responseEntity Encapsula o objeto usuario,que sera transformado num JSON
        // e enviado pro cliente.ele guarda tb o cabeçalho e a resposta,caso tenham.
        Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(usuarioDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
        // Caso o usuario não seja valido porque o usuario colocou uma senha com o
        // tamanho menor do que 6 caracteres,por exemplo,será lançado um httpStatus 400-BAD REQUEST
    }


    @Operation(
            summary = "Recuperar usuário por ID"
            , description = "Recurso responsável por Recuperar um  usuário",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recuperado com sucesso", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UsuarioResponseDto.class))),
                    @ApiResponse(responseCode = "404" , description = "Recurso não encontrado", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> getById(@PathVariable Long id) {
        Usuario user = usuarioService.buscarPorId(id);
        return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toDto(user));
    }

    @Operation(
            summary = "Alterar a senha"
            , description = "Recurso responsável por alterar a senha de um usuário",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Alterado com sucesso", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Void.class))),
                    @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campos invalidos ou mal formatados", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "400", description = "Senha não confere", content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class))),

            }
    )
    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> updatePassword(@PathVariable Long id, @RequestBody UsuarioSenhaDto dto) {
        Usuario user = usuarioService.editarSenha(id, dto.getSenhaAtual(), dto.getNovaSenha(), dto.getConfirmaSenha());
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Listar todos usuários"
            , description = "Recurso responsável por listar todos os usuários",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Listagem com sucesso", content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = UsuarioResponseDto.class))))

            }
    )
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDto>> getAll() {
        List<Usuario> usuarios = usuarioService.buscarTodos();
        return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toListDto(usuarios));
    }

}
