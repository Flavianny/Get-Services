package br.edu.ifpb.dac.getservices.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import br.edu.ifpb.dac.getservices.model.ContratoServico;
import br.edu.ifpb.dac.getservices.model.Servico;
import filters.ContratoFilter;

@Stateless
public class ServiceContratoServico {

	@PersistenceContext
	private EntityManager manager;

	@PostConstruct
	void aposCriacao() {
		System.out.println("[INFO] ContratoServicoDao foi criado.");
	}

	@RolesAllowed({ "PROFISSIONAL", "CLIENTE" })
	public void salvar(ContratoServico contratoServico) throws PersistenciaException {
		System.out.println("[INFO] Salvando o Contrato ");

		try {
			manager.persist(contratoServico);
			System.out.println("[INFO] Salvou o contrato ");
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException("Ocorreu algum erro ao tentar salvar o contrato de serviço.", pe);
		}
	}

	@RolesAllowed({ "PROFISSIONAL" })
	public void editar(ContratoServico contratoServico) throws PersistenciaException {
		try {
			manager.merge(contratoServico);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException("Ocorreu algum erro ao tentar atualizar o contrato de serviço.", pe);
		}
	}

	@PermitAll
	public void deletar(ContratoServico contratoServico) throws PersistenciaException {
		try {
			contratoServico = manager.find(ContratoServico.class, contratoServico.getId());
			manager.remove(contratoServico);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException("Ocorreu algum erro ao tentar remover o contrato de serviço.", pe);
		}
	}

	@PermitAll
	public ContratoServico buscarPeloId(Integer id) throws PersistenciaException {
		ContratoServico contratoServico = null;
		try {
			contratoServico = manager.find(ContratoServico.class, id);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException(
					"Ocorreu algum erro ao tentar recuperar o contrato de serviço com base no ID.", pe);
		}
		return contratoServico;
	}

	@PermitAll
	public List<ContratoServico> listarPorCliente(ContratoFilter filter) throws PersistenciaException {
		List<ContratoServico> contratos = new ArrayList<>();

		try {
			String jpql = "SELECT c FROM ContratoServico c WHERE 1 = 1 and c.usuario = " + filter.getId() + " ";

			// Descricao
			if (notEmpty(filter.getDescricao()))
				jpql += "AND c.descricaoServico LIKE :descricao ";

			// Servico
			if (notEmpty(filter.getNomeServico()))
				jpql += "AND c.servico.nome LIKE :nomeServico ";

			// TipoServico
			if (notEmpty(filter.getNomeProfissional()))
				jpql += "AND c.servico.usuario.nome LIKE :nomeProfissional ";

			// Status
			if (notEmpty(filter.getStatus()))
				jpql += "AND c.statusContrato = :status ";

			// DataInicio
			if (notEmpty(filter.getDataInicio()))
				jpql += "AND c.dataInicio = :dataInicio ";

			// DataFinal
			if (notEmpty(filter.getDataFinal()))
				jpql += "AND c.dataFinal = :dataFinal ";

			TypedQuery<ContratoServico> query = manager.createQuery(jpql, ContratoServico.class);

			// Descricao
			if (notEmpty(filter.getDescricao()))
				query.setParameter("descricao", "%" + filter.getDescricao() + "%");

			// TipoServico
			if (notEmpty(filter.getNomeServico()))
				query.setParameter("nomeServico", "%" + filter.getNomeServico() + "%");

			// Profissinal
			if (notEmpty(filter.getNomeProfissional()))
				query.setParameter("nomeProfissional", "%" + filter.getNomeProfissional() + "%");

			// Status
			if (notEmpty(filter.getStatus()))
				query.setParameter("status", filter.getStatus());

			// DataInicio
			if (notEmpty(filter.getDataInicio()))
				query.setParameter("dataInicio", filter.getDataInicio());

			// DataInicio
			if (notEmpty(filter.getDataFinal()))
				query.setParameter("dataFinal", filter.getDataFinal());

			contratos = query.getResultList();
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw new PersistenceException("Ocorreu algum erro ao tentar recuperar os servicos.", e);
		}
		return contratos;
	}

	@PermitAll
	public List<ContratoServico> listarPorProfissional(ContratoFilter filter) throws PersistenciaException {
		List<ContratoServico> contratos = new ArrayList<>();

		try {
			String jpql = "SELECT c FROM ContratoServico c WHERE 1 = 1 and c.servico.usuario = " + filter.getId() + " ";

			// Descricao
			if (notEmpty(filter.getDescricao()))
				jpql += "AND c.descricaoServico LIKE :descricao ";

			// Servico
			if (notEmpty(filter.getNomeServico()))
				jpql += "AND c.servico.nome LIKE :nomeServico ";

			// Status
			if (notEmpty(filter.getStatus()))
				jpql += "AND c.statusContrato = :status ";

			// DataInicio
			if (notEmpty(filter.getDataInicio()))
				jpql += "AND c.dataInicio = :dataInicio ";

			// DataFinal
			if (notEmpty(filter.getDataFinal()))
				jpql += "AND c.dataFinal = :dataFinal ";

			TypedQuery<ContratoServico> query = manager.createQuery(jpql, ContratoServico.class);

			// Descricao
			if (notEmpty(filter.getDescricao()))
				query.setParameter("descricao", "%" + filter.getDescricao() + "%");

			// Servico
			if (notEmpty(filter.getNomeServico()))
				query.setParameter("nomeServico", "%" + filter.getNomeServico() + "%");

			// Status
			if (notEmpty(filter.getStatus()))
				query.setParameter("status", filter.getStatus());

			// DataInicio
			if (notEmpty(filter.getDataInicio()))
				query.setParameter("dataInicio", filter.getDataInicio());

			// DataFinal
			if (notEmpty(filter.getDataFinal()))
				query.setParameter("dataFinal", filter.getDataFinal());

			contratos = query.getResultList();
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw new PersistenceException("Ocorreu algum erro ao tentar recuperar os servicos.", e);
		}
		return contratos;
	}

	protected boolean notEmpty(Object obj) {
		return obj != null;
	}

	protected boolean notEmpty(String obj) {
		return obj != null && !obj.trim().isEmpty();
	}

	protected boolean empty(Object obj) {
		return obj == null;
	}
}
