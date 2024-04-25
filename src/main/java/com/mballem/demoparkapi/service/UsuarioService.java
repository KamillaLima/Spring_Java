package com.mballem.demoparkapi.service;

import java.util.List;

import com.mballem.demoparkapi.exception.PasswordInvalidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mballem.demoparkapi.entity.Usuario;
import com.mballem.demoparkapi.exception.UsernameUniqueViolationException;
import com.mballem.demoparkapi.repository.UsuarioRepository;
import com.mballem.demoparkapi.service.exception.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException; 
@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Transactional
	// o spring vai tomar conta pra abrir gerenciar e fechar
	// a a transacao do metodo save.
	public Usuario salvar(Usuario usuario) {
		try {
			return usuarioRepository.save(usuario);
		} catch (DataIntegrityViolationException ex) {
			throw new UsernameUniqueViolationException(
					String.format("Username deve ser unico para cada usuário : ", usuario.getUsername()));
		}

	}

	@Transactional(readOnly = true)
	// indicando que o metodo é exclusivo pra uma consulta
	public Usuario buscarPorId(Long id) {
		return usuarioRepository.findById(id).orElseThrow(
				/*
				 * vai lancar uma exceção e também transformar o retorno de Optional para um
				 * objeto.Outra forma de fazer isso também seria usando o
				 * usuarioRepository.findById(id).get()
				 **/

				() -> new EntityNotFoundException("USUARIO NÃO ENCONTRADO : " + id));
	}

	@Transactional
	/*
	 * Essa anotação serve para que eu não precise colocar o método save aqui dentro
	 * do metodo abaixo para atualizar no banco de dados. quando você chama
	 * u.setPassword(novaSenha);, está alterando a senha do usuário u, que é uma
	 * entidade gerenciada pelo JPA. Como isso ocorre dentro de um método
	 * transacional, o Spring garante que essa alteração seja persistida no banco de
	 * dados quando a transação for concluída, sem a necessidade de chamar
	 * explicitamente o método save.
	 */
	public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
		if (!novaSenha.equals(confirmaSenha)) {
			throw new PasswordInvalidException("Nova senha não confere com confirmação de senha.");
		}

		Usuario user = buscarPorId(id);
		if (!user.getPassword().equals(senhaAtual)) {
			throw new PasswordInvalidException("Sua senha não confere.");
		}

		user.setPassword(novaSenha);
		return user;
	}

	@Transactional(readOnly = true)
	// indicando que o metodo é exclusivo pra uma consulta
	public List<Usuario> buscarTodos() {
		return usuarioRepository.findAll();
	}
}
