package br.edu.ifpb.dac.getservices.beans;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import br.edu.ifpb.dac.getservices.model.Servico;
import br.edu.ifpb.dac.getservices.model.StatusServico;
import br.edu.ifpb.dac.getservices.model.TipoServico;
import br.edu.ifpb.dac.getservices.model.Usuario;
import br.edu.ifpb.dac.getservices.service.PersistenciaException;
import br.edu.ifpb.dac.getservices.service.ServiceServicos;
import exception.DacException;
import filters.ServicoFilter;

@Named
@ApplicationScoped
public class ServicoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Servico servico;
	private List<Servico> servicos;

	@EJB
	private ServiceServicos serviceServico;

	private ServicoFilter servicoFilter;

	private Usuario usuario;

	private String imagemDoServicoSelecionado;

	private StreamedContent imagem;

	@PostConstruct
	public void init() throws PersistenciaException {

		if (servico == null) {
			servico = new Servico();
		}
		servicos = new ArrayList<Servico>();
	}

	public String salvar(Usuario usuarioLogado) throws PersistenceException, DacException, IOException {

		try {
			if (isEdicaoServico()) {
				serviceServico.editar(servico);
				System.out.println("Altera - Servico: " + servico.getNome());
			} else {
				System.out.println("Cadastra - Servico: " + servico.getNome());
				servico.setUsuario(usuarioLogado);
				servicos.add(servico);
				serviceServico.salvar(servico);
			}
		} catch (PersistenciaException e) {
			e.printStackTrace();
		}

		return "/admin/profissional/servicos.xhtml?faces-redirect=true";
	}

	public StreamedContent getImagem() {
		return imagem;
	}

	public void setImagem(StreamedContent imagem) {
		this.imagem = imagem;
	}

	public StreamedContent getImagemAtual(Servico servico) throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			return new DefaultStreamedContent();
		} else {
			return new DefaultStreamedContent(new ByteArrayInputStream(servico.getImageServico()));
		}
	}

	public void uploadImagemServico(FileUploadEvent event) {
		servico.setImageServico(event.getFile().getContents());
	}

	public boolean isEdicaoServico() {
		return servico.getId() != null;
	}

	public TipoServico[] getTiposServicos() {
		return TipoServico.values();
	}

	public StatusServico[] getStatusServicos() {
		return StatusServico.values();
	}
	
	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public ServiceServicos getServiceServico() {
		return serviceServico;
	}

	public void setServiceServico(ServiceServicos serviceServico) {
		this.serviceServico = serviceServico;
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

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getImagemDoServicoSelecionado() {
		return imagemDoServicoSelecionado;
	}

	public void setImagemDoServicoSelecionado(String imagemDoServicoSelecionado) {
		this.imagemDoServicoSelecionado = imagemDoServicoSelecionado;
	}

}
