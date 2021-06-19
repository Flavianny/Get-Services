package br.edu.ifpb.dac.getservices.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import br.edu.ifpb.dac.getservices.model.Endereco;
import br.edu.ifpb.dac.getservices.model.Usuario;
import br.edu.ifpb.dac.getservices.service.PersistenciaException;
import br.edu.ifpb.dac.getservices.service.ServiceUsuario;
import exception.DacException;

@Named
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private List<Usuario> usuarios;
	private String tipoUsuario;
	private List<String> tiposUsuario;
	@EJB
	private ServiceUsuario serviceUsuario;

	@PostConstruct
	public void init() {

		if (usuario == null) {
			usuario = new Usuario();
			usuario.setEndereco(new Endereco());
			tiposUsuario = new ArrayList<String>();
		} else {
			if (usuario.getEndereco() == null)
				usuario.setEndereco(new Endereco());
		}
	}

	public String salva() throws PersistenceException, DacException {

		try {
			if (isEdicaoUsuario()) {
				serviceUsuario.editar(usuario, false);
				System.out.println("Altera - Usuario: " + usuario.getNome());
			} else {
				System.out.println("Cadastra - Usuario: " + usuario.getNome());
				tiposUsuario.add(tipoUsuario);
				usuario.setTiposUsuarios(tiposUsuario);
				serviceUsuario.salvar(usuario);
				tiposUsuario = new ArrayList<String>();
			}
		} catch (PersistenciaException e) {
			e.printStackTrace();
		}
		System.out.println(tipoUsuario);

		return "/admin/index.xhtml?faces-redirect=true";
	}

	public boolean isEdicaoUsuario() {
		return usuario.getId() != null;
	}

	public boolean isProfissional(Usuario usuarioLogado) {
		return usuarioLogado != null && usuarioLogado.getTiposUsuarios().get(0).equals("PROFISSIONAL");
	}

	public boolean isCliente(Usuario usuarioLogado) {
		return usuarioLogado != null && usuarioLogado.getTiposUsuarios().get(0).equals("CLIENTE");
	}

	public void checarDisponibilidadeLogin() throws PersistenciaException {
		try {
			serviceUsuario.validarLogin(usuario);
			reportarMensagemDeSucesso(String.format("Login '%s' is available.", usuario.getLogin()));
		} catch (DacException e) {
			reportarMensagemDeErro(e.getMessage());
		}
	}

	protected void reportarMensagemDeErro(String detalhe) {
		reportarMensagem(true, detalhe, false);
	}

	protected void reportarMensagemDeSucesso(String detalhe) {
		reportarMensagem(false, detalhe, true);
	}

	private void reportarMensagem(boolean isErro, String detalhe, boolean keepMessages) {
		String sumario = "Success!";
		Severity severity = FacesMessage.SEVERITY_INFO;
		if (isErro) {
			sumario = "Error!";
			severity = FacesMessage.SEVERITY_ERROR;
			FacesContext.getCurrentInstance().validationFailed();
		}

		FacesMessage msg = new FacesMessage(severity, sumario + " " + detalhe, null);

		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.setKeepMessages(keepMessages);
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public ServiceUsuario getServiceUsuario() {
		return serviceUsuario;
	}

	public void setServiceUsuario(ServiceUsuario serviceUsuario) {
		this.serviceUsuario = serviceUsuario;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

}
