package managedBeans;

import javax.security.enterprise.CallerPrincipal;

import br.edu.ifpb.dac.getservices.model.Usuario;

public class UsuarioPrincipal extends CallerPrincipal {

	private final Usuario usuario;

	public UsuarioPrincipal(Usuario usuario) {
		super(usuario.getLogin());
		this.usuario = usuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

}
