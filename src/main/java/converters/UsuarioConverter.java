package converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.edu.ifpb.dac.getservices.model.Usuario;
import br.edu.ifpb.dac.getservices.service.ServiceUsuario;
import exception.DacException;

@FacesConverter(managed = true, forClass = Usuario.class)
public class UsuarioConverter implements Converter<Usuario> {

	@Inject
	private ServiceUsuario serviceUsuario;

	@Override
	public Usuario getAsObject(FacesContext context, UIComponent component, String idUsuario) {
		if (idUsuario == null || idUsuario.isEmpty()) {
			return null;
		}

		try {
			Integer id = Integer.parseInt(idUsuario);
			System.out.println("UsuarioConverter - id: " + idUsuario);
			Usuario usuarioRecuperado = serviceUsuario.buscaPeloId(id);
			System.out.println("Usuario Recuperado= " + usuarioRecuperado.getNome());
			return usuarioRecuperado;
		} catch (DacException | NumberFormatException e) {
			String msgErroStr = String.format(
					"Erro de conversão! Não foi possível realizar a conversão da string '%s' para o tipo esperado.",
					idUsuario);
			FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgErroStr, msgErroStr);
			throw new ConverterException(msgErro);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Usuario usuario) {
		if (!(usuario instanceof Usuario)) {
			return null;
		}

		Integer id = ((Usuario) usuario).getId();
		return (id != null) ? id.toString() : null;
	}

}
