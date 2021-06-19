package br.edu.ifpb.dac.getservices.model;

public enum StatusServico {

	DISPONIVEL("Disponível"), INDISPONIVEL("Indisponível");

	private String status;

	private StatusServico(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

}
