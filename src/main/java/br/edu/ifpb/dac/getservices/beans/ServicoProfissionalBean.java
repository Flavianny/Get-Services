package br.edu.ifpb.dac.getservices.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.edu.ifpb.dac.getservices.model.ContratoServico;
import br.edu.ifpb.dac.getservices.model.Servico;
import br.edu.ifpb.dac.getservices.model.Usuario;
import br.edu.ifpb.dac.getservices.service.ServiceServicos;
import br.edu.ifpb.dac.getservices.service.ServiceUsuario;
import exception.DacException;
import filters.ServicoFilter;

@Named
@ViewScoped
public class ServicoProfissionalBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private ServiceServicos serviceServicos;

	@EJB
	private ServiceUsuario serviceUsuario;

	private Usuario usuario;

	private Servico servico;

	private List<Servico> servicos;

	private ServicoFilter servicoFilter;

	@PostConstruct
	public void init(Usuario usuarioLogado) {
		limpar();
		buscar(usuarioLogado);
	}

	public String buscar(Usuario usuarioLogado) {
		try {
			getServicoFilter().setId(usuarioLogado.getId());
			servicos = serviceServicos.listarPorUsuario(getServicoFilter());
		} catch (DacException e) {
			return null;
		}
		return null;
	}

	public String limpar() {
		this.servicoFilter = new ServicoFilter();
		return null;
	}

	public String deletar(Servico servico) {
		try {
			serviceServicos.deletar(servico);
		} catch (DacException e) {
			return null;
		}

		return "/admin/profissional/servicos.xhtml?faces-redirect=true";
	}

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

	public ServiceServicos getServiceServicos() {
		return serviceServicos;
	}

	public void setServiceServicos(ServiceServicos serviceServicos) {
		this.serviceServicos = serviceServicos;
	}

	public ServiceUsuario getServiceUsuario() {
		return serviceUsuario;
	}

	public void setServiceUsuario(ServiceUsuario serviceUsuario) {
		this.serviceUsuario = serviceUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}