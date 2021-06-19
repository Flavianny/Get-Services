package filters;

import java.util.Date;

import br.edu.ifpb.dac.getservices.model.StatusContrato;

public class ContratoFilter implements Filter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String descricao;
	private StatusContrato status;
	private String nomeProfissional;
	private String nomeServico;
	private Date dataInicio;
	private Date dataFinal;
	private Integer id;

	public ContratoFilter() {

	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public StatusContrato getStatus() {
		return status;
	}

	public void setStatus(StatusContrato statusContrato) {
		this.status = statusContrato;
	}

	public String getNomeProfissional() {
		return nomeProfissional;
	}

	public void setNomeProfissional(String nomeProfissional) {
		this.nomeProfissional = nomeProfissional;
	}

	public String getNomeServico() {
		return nomeServico;
	}

	public void setNomeServico(String nomeServico) {
		this.nomeServico = nomeServico;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
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

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public boolean isEmpty() {
		if (descricao != null)
			return false;
		if (status != null)
			return false;
		if (nomeProfissional != null)
			return false;
		if (nomeServico != null)
			return false;
		if (id != null)
			return false;
		if (dataInicio != null)
			return false;
		if (dataFinal != null)
			return false;
		return true;
	}
}
