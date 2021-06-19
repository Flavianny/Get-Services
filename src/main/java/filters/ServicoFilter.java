package filters;

import br.edu.ifpb.dac.getservices.model.StatusServico;
import br.edu.ifpb.dac.getservices.model.TipoServico;

public class ServicoFilter implements Filter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nome;
	private String descricao;
	private TipoServico tipoServico;
	private String profissional;
	private StatusServico status;
	private String cidade;
	private Integer id;

	public ServicoFilter() {
		// TODO Auto-generated constructor stub

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

	public String getProfissional() {
		return profissional;
	}

	public void setProfissional(String profissional) {
		this.profissional = profissional;
	}

	public StatusServico getStatus() {
		return status;
	}

	public void setStatus(StatusServico status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public boolean isEmpty() {
		if (nome != null)
			return false;
		if (descricao != null)
			return false;
		if (tipoServico != null)
			return false;
		if (profissional != null)
			return false;
		if (cidade != null)
			return false;
		return true;
	}
}
