package br.edu.ifpb.dac.getservices.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.edu.ifpb.dac.getservices.model.ContratoServico;
import br.edu.ifpb.dac.getservices.model.Usuario;
import br.edu.ifpb.dac.getservices.service.ServiceContratoServico;
import exception.DacException;
import filters.ContratoFilter;

@Named
@ViewScoped
public class ContratoPorUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ServiceContratoServico serviceContratoServico;

	private List<ContratoServico> contratos;

	private ContratoFilter contratoFilter;

	@PostConstruct
	public void init(Usuario usuarioLogado) {
		limpar();
		buscar(usuarioLogado);
	}

	public String buscar(Usuario usuarioLogado) {

		try {
			getContratoFilter().setId(usuarioLogado.getId());
			System.out.println("Usuario do tipo : " + usuarioLogado.getTiposUsuarios().get(0));
			String tipoUsuario = usuarioLogado.getTiposUsuarios().get(0);
			if (tipoUsuario.equals("CLIENTE")) {		
				contratos = serviceContratoServico.listarPorCliente(getContratoFilter());
			} else if (tipoUsuario.equals("PROFISSIONAL")) {
				contratos = serviceContratoServico.listarPorProfissional(getContratoFilter());
			}

		} catch (DacException e) {
			return null;
		}
		return null;

	}

	public String limpar() {
		this.contratoFilter = new ContratoFilter();
		return null;
	}

	public ServiceContratoServico getServiceContratoServico() {
		return serviceContratoServico;
	}

	public void setServiceContratoServico(ServiceContratoServico serviceContratoServico) {
		this.serviceContratoServico = serviceContratoServico;
	}

	public List<ContratoServico> getContratos() {
		return contratos;
	}

	public void setContratos(List<ContratoServico> contratos) {
		this.contratos = contratos;
	}

	public ContratoFilter getContratoFilter() {
		return contratoFilter;
	}

	public void setContratoFilter(ContratoFilter contratoFilter) {
		this.contratoFilter = contratoFilter;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
