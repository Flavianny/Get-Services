package br.edu.ifpb.dac.getservices.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Servico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String descricao;
	
	@Lob
    @Column(name = "imageServico")
	private byte[] imageServico;
	
	@Enumerated(EnumType.STRING)
	private TipoServico tipoServico;

	@Enumerated(EnumType.STRING)
	private StatusServico statusservico;

	@ManyToOne
	private Usuario usuario;

	@OneToMany(mappedBy = "servico", cascade = CascadeType.ALL)
	private List<ContratoServico> contratos;

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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public TipoServico getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(TipoServico tipoServico) {
		this.tipoServico = tipoServico;
	}

	public StatusServico getStatusservico() {
		return statusservico;
	}

	public void setStatusservico(StatusServico statusservico) {
		this.statusservico = statusservico;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<ContratoServico> getContratos() {
		return contratos;
	}

	public void setContratos(List<ContratoServico> contratos) {
		this.contratos = contratos;
	}

	public byte[] getImageServico() {
		return imageServico;
	}

	public void setImageServico(byte[] imageServico) {
		this.imageServico = imageServico;
	}

}
