package com.mballem.demoparkapi;

import com.mballem.demoparkapi.web.dto.UsuarioCreateDto;
import com.mballem.demoparkapi.web.dto.UsuarioResponseDto;
import com.mballem.demoparkapi.web.dto.UsuarioSenhaDto;
import com.mballem.demoparkapi.web.exception.ErrorMessage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//Para que o tomcat tenha mais de uma porta no momento da execução do teste
            //Testando toda a parte referente ao usuário
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios-delete.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UsuarioIT {

    @Autowired
    WebTestClient testClient;

    @Test
    //Os métodos de teste devem ter a anotação @Test e também devem ser públicos e com retorno Void
    //Não esquecer de colocar o nome explicando o que o método faz e também qual o retorno que ele deve ter

    public void createUsuario_ComUserNameEPasswordValidos_RetornarUsuarioCriadoComStatus201(){
        UsuarioResponseDto responseBody =  testClient.post() //Primeiro qual a requisição que será feita
                .uri("/api/v1/usuarios") //Passsar URI
                .contentType(MediaType.APPLICATION_JSON) //
                .bodyValue(new UsuarioCreateDto("tody@gmail.com" , "654321")) //corpo da requisição que queremos testar
                .exchange() //Agora vamos trabalhar com a resposta
                .expectStatus().isCreated() //Como retorno esperamos um isCreated (usuario é cadastrado com sucesso)
                .expectBody(UsuarioResponseDto.class)//deve ter retornar no corpo um usuarioResponse
                .returnResult().getResponseBody(); //GetResponseBody vai fazer com que retorno um UsuarioResponseDTO

        //Essa assertions vai verificar o retorno ,pra ver se os campos não estão vazios e também se retorna o que foi enviado
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull(); //o responseBody é o que vai ser retornado ali na linha 27
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("tody@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");//Esperamos do tipo cliente pois definimos anteriormente que
        //todos serão cadastrados por padrão como clientes
    }



    @Test
    public void createUsuario_ComUserNameEPasswordInvalidos_RetornarErrorMessageStatus422(){
        ErrorMessage responseBody =  testClient.post() //Primeiro qual a requisição que será feita
                .uri("/api/v1/usuarios") //Passsar URI
                .contentType(MediaType.APPLICATION_JSON) //
                .bodyValue(new UsuarioCreateDto("" , "654321")) // vou passar um usuario vazio
                .exchange() //Agora vamos trabalhar com a resposta
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        //Essa assertions vai verificar o retorno ,pra ver se os campos não estão vazios e também se retorna o que foi enviado
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull(); //o responseBody é o que vai ser retornado ali na linha 27
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);




         responseBody =  testClient.post() //Primeiro qual a requisição que será feita
                .uri("/api/v1/usuarios") //Passsar URI
                .contentType(MediaType.APPLICATION_JSON) //
                .bodyValue(new UsuarioCreateDto("joao@" , "654321")) // vou passar um usuario vazio
                .exchange() //Agora vamos trabalhar com a resposta
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        //Essa assertions vai verificar o retorno ,pra ver se os campos não estão vazios e também se retorna o que foi enviado
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull(); //o responseBody é o que vai ser retornado ali na linha 27
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


        responseBody =  testClient.post() //Primeiro qual a requisição que será feita
                .uri("/api/v1/usuarios") //Passsar URI
                .contentType(MediaType.APPLICATION_JSON) //
                .bodyValue(new UsuarioCreateDto("kamilla@email" , "789456")) // vou passar um usuario vazio
                .exchange() //Agora vamos trabalhar com a resposta
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        //Essa assertions vai verificar o retorno ,pra ver se os campos não estão vazios e também se retorna o que foi enviado
        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull(); //o responseBody é o que vai ser retornado ali na linha 27
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }




    @Test
    public void createUsuario_ComPasswordInvalidos_RetornarErrorMessageStatus422(){
        ErrorMessage responseBody =  testClient.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("tody@gmail.com" , ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);




        responseBody =  testClient.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("tody@gmail.com" , "12345"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


        responseBody =  testClient.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("tody@gmail.com" , "12345678"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }




    @Test
    public void createUsuario_ComUsernameRepetido_RetornarErrorMessageStatus409(){
        ErrorMessage responseBody =  testClient.post()
                .uri("/api/v1/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioCreateDto("ana@gmail.com" , "123456")) //foi criada la no usuarios-insert.sql
                .exchange()
                .expectStatus().isEqualTo(409)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);




    }


    @Test
    public void buscarUsuario_ComIdExistente_RetornarUsuarioStatus200(){
        UsuarioResponseDto responseBody =  testClient.get()
                .uri("/api/v1/usuarios/100") // passo o ID ( ele esta la no arquivo usuarios-insert.sql
                .exchange()
                .expectStatus().isOk() //isOK = retorno 200
                .expectBody(UsuarioResponseDto.class)
                .returnResult().getResponseBody();


        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        //Posso comparar os atributos que vão ser retornados no UsuarioResponseDto
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("ADMIN");
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("ana@gmail.com");
    }


    @Test
    public void buscarUsuario_ComIdInexistente_RetornarErrorMessageStatus404(){
        ErrorMessage responseBody =  testClient.get()
                .uri("/api/v1/usuarios/301") // passo um id que não existe
                .exchange()
                .expectStatus().isNotFound() //isNotFound = 404
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }


    @Test
    public void updateSenha_ComDadosValidos_RetornarErrorMessageStatus204(){
        testClient.patch()
                .uri("/api/v1/usuarios/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456" , "789651" , "789651")) //corpo da requisição que queremos testar
                .exchange()
                .expectStatus().isNoContent();
    }


    @Test
    public void updateSenha_ComIdInexistente_RetornarErrorMessageStatus404(){
        ErrorMessage responseBody =  testClient.patch()
                .uri("/api/v1/usuarios/301")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("123456" , "789651" , "789651"))
                .exchange()
                .expectStatus().isNotFound() //isNotFound = 404
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }


    @Test
    public void updateSenha_ComCamposInvalidos_RetornarErrorMessageStatus422() {
         ErrorMessage responseBody =  testClient.patch()
                .uri("/api/v1/usuarios/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("" , "" , ""))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);



        //Senhas menores que 6 caracteres
         responseBody = testClient.patch()
                .uri("/api/v1/usuarios/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("12345", "12345", "12345"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


        //Senhas maiores que 6 caracteres
        responseBody = testClient.patch()
                .uri("/api/v1/usuarios/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new UsuarioSenhaDto("1234567", "1234567", "1234567"))
                .exchange()
                .expectStatus().isEqualTo(422)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);


    }




    @Test
    public void updateSenha_ComSenhasInvalidas_RetornarErrorMessageStatus400() {
        ErrorMessage responseBody =  testClient.patch()
                .uri("/api/v1/usuarios/100")
                .contentType(MediaType.APPLICATION_JSON)
                // Nova senha e confirma senha com valores diferentes
                .bodyValue(new UsuarioSenhaDto("123456" , "789456" , "025764"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);



        //Senhas menores que 6 caracteres
        responseBody = testClient.patch()
                .uri("/api/v1/usuarios/100")
                .contentType(MediaType.APPLICATION_JSON)
                // Senha atual errada
                .bodyValue(new UsuarioSenhaDto("673912", "333444", "333444"))
                .exchange()
                .expectStatus().isEqualTo(400)
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);


    }


    @Test
    public void buscarUsuarios_Existentes_RetornarListaDeUsuariosStatus200(){
        List<UsuarioResponseDto> responseBody = testClient.get()
                .uri("/api/v1/usuarios") // passo o ID ( ele esta la no arquivo usuarios-insert.sql
                .exchange()
                .expectStatus().isOk() //isOK = retorno 200
                .expectBodyList(UsuarioResponseDto.class)
                //Especificando que retornara uma lista
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.size()).isEqualTo(3);

    }
}


