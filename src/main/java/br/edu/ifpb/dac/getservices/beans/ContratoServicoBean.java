package br.edu.ifpb.dac.getservices.beans;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import br.edu.ifpb.dac.getservices.model.ContratoServico;
import br.edu.ifpb.dac.getservices.model.Servico;
import br.edu.ifpb.dac.getservices.model.StatusContrato;
import br.edu.ifpb.dac.getservices.model.StatusServico;
import br.edu.ifpb.dac.getservices.model.Usuario;
import br.edu.ifpb.dac.getservices.service.PersistenciaException;
import br.edu.ifpb.dac.getservices.service.ServiceContratoServico;
import exception.DacException;
import filters.ContratoFilter;

@Named
@ViewScoped
public class ContratoServicoBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private ContratoServico contratoServico;
	private List<ContratoServico> contratos;

	private ContratoFilter contratoFilter;

	@EJB
	private ServiceContratoServico serviceContratoServico;

	private Servico servico;

	@PostConstruct
	public void init() {

		if (contratoServico == null) {
			contratoServico = new ContratoServico();
		}
	}

	public String salvar(Usuario cliente) {
		try {
			if (isEdicao())
				serviceContratoServico.editar(contratoServico);
			else {
				contratoServico.setUsuario(cliente);
				contratoServico.setServico(servico);
				contratoServico.setStatusContrato(StatusContrato.ABERTO);
				serviceContratoServico.salvar(contratoServico);
			}
		} catch (DacException e) {
			
		}

		return "/admin/index.xhtml?faces-redirect=true";
	}


	public StatusContrato[] getStatusContratos() {
		return StatusContrato.values();
	}
	
	public boolean isEdicao() {
		return contratoServico.getId() != null;
	}

	public ContratoServico getContratoServico() {
		return contratoServico;
	}

	public void setContratoServico(ContratoServico contratoServico) {
		this.contratoServico = contratoServico;
	}

	public List<ContratoServico> getContratos() {
		return contratos;
	}

	public void setContratos(List<ContratoServico> contratos) {
		this.contratos = contratos;
	}

	public ServiceContratoServico getServiceContratoServico() {
		return serviceContratoServico;
	}

	public void setServiceContratoServico(ServiceContratoServico serviceContratoServico) {
		this.serviceContratoServico = serviceContratoServico;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ContratoFilter getContratoFilter() {
		return contratoFilter;
	}

	public void setContratoFilter(ContratoFilter contratoFilter) {
		this.contratoFilter = contratoFilter;
	}

}
