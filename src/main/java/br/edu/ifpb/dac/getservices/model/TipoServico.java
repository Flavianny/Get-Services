package br.edu.ifpb.dac.getservices.model;

public enum TipoServico {

	ASSISTENCIA_TECNICA("Assistência Técnica"), AULAS("Aulas"), AUTOS("Autos"), CONSULTORIA("Consultoria"),
	DESIGN_E_TECNOLOGIA("Design e Tecnologia"), EVENTOS("Eventos"), MODA_E_BELEZA("Moda e Beleza"),
	REFORMAS("Reformas"), SAUDE("Saúde"), SERVICOS_DOMESTICOS("Serviços Domésticos");

	private String tipoServico;

	private TipoServico(String tipoServico) {
		this.tipoServico = tipoServico;
	}

	public String getTipoServico() {
		return tipoServico;
	}

	public void setTipoServico(String tipoServico) {
		this.tipoServico = tipoServico;
	}

}
