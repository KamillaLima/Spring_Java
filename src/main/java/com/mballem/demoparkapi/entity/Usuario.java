package com.mballem.demoparkapi.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
	// boa prática quando usa JPA e hibernate

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "username", nullable = false, unique = true, length = 100)
	private String username;
	@Column(name = "password", nullable = false, length = 200)
	private String password;
	@Column(name = "role", nullable = false, length = 25)
	@Enumerated(EnumType.STRING)
	private Role role = Role.ROLE_CLIENTE;
	//todo usuario sera iniciado como um cliente,caso precise ser um admin,a alteração é realizada depois

	/*
	 * vai transformar o enum em uma String, Se eu usar o EnumType.ORDINAL,ele vai
	 * salvar tipo : como o meu role tem primeiro o enum role_admin,no banco quem
	 * for admin ficaria com o campo salvo com o numero 0 e quem for cliente ficaria
	 * com o campo salvo no banco com o numero 1
	 */

	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao;

	@Column(name = "data_modificacao")
	private LocalDateTime dataModificacao;

	@Column(name = "criado_por")
	private String criadoPor;
	// marcar o nome do usuario que fez o insert e alteracao na tabela
	@Column(name = "modificado_por")
	private String modificadoPor;

	
	public Usuario() {
		super();
	}

	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public LocalDateTime getDataModificacao() {
		return dataModificacao;
	}

	public void setDataModificacao(LocalDateTime dataModificacao) {
		this.dataModificacao = dataModificacao;
	}

	public String getCriadoPor() {
		return criadoPor;
	}

	public void setCriadoPor(String criadoPor) {
		this.criadoPor = criadoPor;
	}

	public String getModificadoPor() {
		return modificadoPor;
	}

	public void setModificadoPor(String modificadoPor) {
		this.modificadoPor = modificadoPor;
	}



	public enum Role {
		ROLE_ADMIN, ROLE_CLIENTE
	}
	// Criei o enum já direto na classe Usuario,pois esse enum
	// será usado apenas aqui dentro

	
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	/*
	 * O método hashCode() é usado para gerar um valor numérico (hash) que
	 * representa exclusivamente um objeto. É essencial para estruturas de dados que
	 * dependem de hashes, como HashMaps e HashSets. Ao implementá-lo corretamente,
	 * você garante que as entidades JPA possam ser usadas de forma eficiente em
	 * coleções que usam hashing para pesquisa e armazenamento.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}

	/*
	 * O método equals() é usado para comparar se dois objetos são iguais. Ao
	 * implementá-lo, você pode comparar duas instâncias de uma entidade JPA com
	 * base em critérios específicos, como a igualdade de identificadores ou outras
	 * propriedades relevantes.
	 */

	@Override
	public String toString() {
		return "Usuario [id=" + id + "]";
	}

}
