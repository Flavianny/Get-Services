package converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import br.edu.ifpb.dac.getservices.model.ContratoServico;
import br.edu.ifpb.dac.getservices.service.ServiceContratoServico;
import exception.DacException;

@FacesConverter(managed = true, forClass = ContratoServico.class)
public class ContratoServicoConverter implements Converter<ContratoServico> {

	@Inject
	private ServiceContratoServico serviceContratoServico;

	@Override
	public ContratoServico getAsObject(FacesContext context, UIComponent component, String idContrato) {
		if (idContrato == null || idContrato.isEmpty()) {
			return null;
		}

		try {
			Integer id = Integer.parseInt(idContrato);
			System.out.println("ServicoConverter - id: " + idContrato);
			ContratoServico contratoRecuperado = serviceContratoServico.buscarPeloId(id);
			System.out.println("ServicoRecuperado= " + contratoRecuperado.getId());
			return contratoRecuperado;
		} catch (DacException | NumberFormatException e) {
			String msgErroStr = String.format(
					"Erro de conversão! Não foi possível realizar a conversão da string '%s' para o tipo esperado.",
					idContrato);
			FacesMessage msgErro = new FacesMessage(FacesMessage.SEVERITY_ERROR, msgErroStr, msgErroStr);
			throw new ConverterException(msgErro);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, ContratoServico contratoServico) {
		if (!(contratoServico instanceof ContratoServico)) {
			return null;
		}

		Integer id = ((ContratoServico) contratoServico).getId();
		return (id != null) ? id.toString() : null;
	}

}
