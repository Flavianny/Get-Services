package managedBeans;

import java.io.Serializable;
import java.security.Principal;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;

import br.edu.ifpb.dac.getservices.model.Usuario;
import br.edu.ifpb.dac.getservices.service.ServiceUsuario;
import exception.DacException;
import filters.UsuarioFilter;

public class AbstractBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ServiceUsuario serviceUsuario;

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

	public boolean isUserInRole(String role) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		return externalContext.isUserInRole(role);
	}

	public String getUserLogin() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Principal userPrincipal = externalContext.getUserPrincipal();
		if (userPrincipal == null) {
			return "";
		}

		return userPrincipal.getName();
	}

	public Usuario getUsuarioLogado() {
		UsuarioFilter filter = new UsuarioFilter();
		filter.setLogin(getUserLogin());
		List<Usuario> usuarios = null;
		try {
			usuarios = serviceUsuario.listar(filter);
		} catch (DacException e) {
			e.printStackTrace();
			reportarMensagemDeErro("Erro ao recuperar o usuário logado!");
		}

		if (!usuarios.isEmpty()) {
			return usuarios.get(0);
		}

		return null;
	}

}
