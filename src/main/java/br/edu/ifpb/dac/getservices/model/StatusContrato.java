package br.edu.ifpb.dac.getservices.model;

public enum StatusContrato {

	ABERTO("Aberto"), EM_SERVICO("Em Serviço"), FINALIZADO("Finalizado"), CANCELADO("Cancelado");

	private String statusContrato;

	private StatusContrato(String statusContrato) {
		this.statusContrato = statusContrato;
	}

	public String getStatusContrato() {
		return statusContrato;
	}

}
