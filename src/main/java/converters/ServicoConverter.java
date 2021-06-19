package converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.edu.ifpb.dac.getservices.model.Servico;
import br.edu.ifpb.dac.getservices.service.ServiceServicos;
import exception.DacException;

@FacesConverter(managed = true, forClass = Servico.class)
public class ServicoConverter implements Converter<Servico> {

	@Inject
	private ServiceServicos serviceServicos;

	@Override
	public Servico getAsObject(FacesContext context, UIComponent component, String idUservico) {
		if (idUservico == null || idUservico.isEmpty()) {
			return null;
		}

		try {
			Integer id = Integer.parseInt(idUservico);
			System.out.println("ServicoConverter - id: " + idUservico);
			Servico servicoRecuperado = serviceServicos.buscaPeloId(id);
			System.out.println("ServicoRecuperado= " + servicoRecuperado.getNome());
			return servicoRecuperado;
		} catch (DacException | NumberFormatException e) {
			String msgErroStr = String.format(
					"Erro de conversão! Não foi possível realizar a conversão da string '%s' para o tipo esperado.",
					idUservico);
			FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgErroStr, msgErroStr);
			throw new ConverterException(msgErro);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Servico servico) {
		if (!(servico instanceof Servico)) {
			return null;
		}

		Integer id = ((Servico) servico).getId();
		return (id != null) ? id.toString() : null;
	}

}
