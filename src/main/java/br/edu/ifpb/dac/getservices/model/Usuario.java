package br.edu.ifpb.dac.getservices.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank
	private String nome;

	@Column(unique = true)
	@NotBlank
	private String login;

	@NotBlank
	private String senha;

	@Column(length = 700)
	private String saltoHash;

	private String telefone;

	@Embedded
	private Endereco endereco;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<ContratoServico> contratosServicos;

	@ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
	private List<String> tiposUsuarios;

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	private List<Servico> servicos;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public List<ContratoServico> getContratosServicos() {
		return contratosServicos;
	}

	public void setContratosServicos(List<ContratoServico> contratosServicos) {
		this.contratosServicos = contratosServicos;
	}

	public Set<String> getTiposUsuariosString() {
		return new HashSet<String>(tiposUsuarios);
	}

	public List<String> getTiposUsuarios() {
		return tiposUsuarios;
	}

	public void setTiposUsuarios(List<String> tiposUsuarios) {
		this.tiposUsuarios = tiposUsuarios;
	}

	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public String getSaltoHash() {
		return saltoHash;
	}

	public void setSaltoHash(String saltoHash) {
		this.saltoHash = saltoHash;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

}
