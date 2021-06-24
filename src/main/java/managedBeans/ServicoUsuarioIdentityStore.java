package managedBeans;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

import br.edu.ifpb.dac.getservices.model.Usuario;
import br.edu.ifpb.dac.getservices.service.ServiceUsuario;

@ApplicationScoped
public class ServicoUsuarioIdentityStore implements IdentityStore {

	@Inject
	private ServiceUsuario serviceUsuario;

	@Override
	public CredentialValidationResult validate(Credential credential) {
		UsernamePasswordCredential login = (UsernamePasswordCredential) credential;
		CredentialValidationResult resultadoValidacao = CredentialValidationResult.INVALID_RESULT;

		String username = login.getCaller();
		String password = login.getPasswordAsString();

		Usuario usuarioRecuperado = serviceUsuario.buscaPeloLoginESenha(username, password);

		if (usuarioRecuperado != null) {
			resultadoValidacao = new CredentialValidationResult(new UsuarioPrincipal(usuarioRecuperado),
					usuarioRecuperado.getTiposUsuariosString());
		}

		System.out.println("Resultado Validacao: " + resultadoValidacao.getStatus());
		return resultadoValidacao;
	}

}
