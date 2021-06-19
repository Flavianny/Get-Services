package br.edu.ifpb.dac.getservices.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.edu.ifpb.dac.getservices.model.Servico;
import br.edu.ifpb.dac.getservices.model.StatusServico;
import br.edu.ifpb.dac.getservices.service.ServiceServicos;
import br.edu.ifpb.dac.getservices.service.ServiceUsuario;
import exception.DacException;
import filters.ServicoFilter;

@Named
@ViewScoped
public class ServicoClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ServiceServicos serviceServicos;

	@EJB
	private ServiceUsuario serviceUsuario;

	private List<Servico> servicos;

	private ServicoFilter servicoFilter;

	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public ServicoFilter getServicoFilter() {
		return servicoFilter;
	}

	public void setServicoFilter(ServicoFilter servicoFilter) {
		this.servicoFilter = servicoFilter;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@PostConstruct
	public void init() {
		limpar();
		buscar();
	}

	public String buscar() {
		try {
			getServicoFilter().setStatus(StatusServico.DISPONIVEL);
			servicos = serviceServicos.listar(getServicoFilter());
		} catch (DacException e) {
			return null;
		}
		return null;
	}

	public String limpar() {
		this.servicoFilter = new ServicoFilter();
		return null;
	}

}
