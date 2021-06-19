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

import br.edu.ifpb.dac.getservices.model.Servico;
import br.edu.ifpb.dac.getservices.model.Usuario;
import filters.ServicoFilter;

@Stateless
public class ServiceServicos {

	@PersistenceContext
	private EntityManager manager;

	@PostConstruct
	void aposCriacao() {
		System.out.println("[INFO] ServicoDao foi criado.");
	}

	@RolesAllowed({ "PROFISSIONAL" })
	public void salvar(Servico servico) throws PersistenciaException {
		System.out.println("[INFO] Salvando o Servico " + servico.getNome());

		try {
			manager.persist(servico);
			System.out.println("[INFO] Salvou o Servico " + servico.getNome());
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException("Ocorreu algum erro ao tentar salvar o serviço.", pe);
		}
	}

	@RolesAllowed({ "PROFISSIONAL" })
	public void editar(Servico servico) throws PersistenciaException {
		try {
			manager.merge(servico);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException("Ocorreu algum erro ao tentar atualizar o serviço.", pe);
		}
	}

	@RolesAllowed({ "PROFISSIONAL" })
	public void deletar(Servico servico) throws PersistenciaException {
		try {
			servico = manager.find(Servico.class, servico.getId());
			manager.remove(servico);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException("Ocorreu algum erro ao tentar remover o serviço.", pe);
		}
	}

	@PermitAll
	public Servico buscaPeloId(Integer id) throws PersistenciaException {
		Servico servico = null;
		try {
			servico = manager.find(Servico.class, id);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException("Ocorreu algum erro ao tentar recuperar o serviço com base no ID.", pe);
		}
		return servico;
	}

	@RolesAllowed({ "PROFISSIONAL", "CLIENTE" })
	public List<Servico> listar(ServicoFilter filter) throws PersistenciaException {
		List<Servico> servicos = new ArrayList<>();

		try {
			String jpql = "SELECT s FROM Servico s WHERE 1 = 1 ";
			// Nome
			if (notEmpty(filter.getNome()))
				jpql += "AND s.nome LIKE :nome ";

			// Descricao
			if (notEmpty(filter.getDescricao()))
				jpql += "AND s.descricao LIKE :descricao ";

			// TipoServico
			if (notEmpty(filter.getTipoServico()))
				jpql += "AND s.tipoServico = :tipoServico ";

			// TipoServico
			if (notEmpty(filter.getProfissional()))
				jpql += "AND s.usuario.nome LIKE :profissional ";

			// TipoServico
			if (notEmpty(filter.getStatus()))
				jpql += "AND s.statusservico = :status ";

			TypedQuery<Servico> query = manager.createQuery(jpql, Servico.class);

			// Nome
			if (notEmpty(filter.getNome()))
				query.setParameter("nome", "%" + filter.getNome() + "%");

			// Descricao
			if (notEmpty(filter.getDescricao()))
				query.setParameter("descricao", "%" + filter.getDescricao() + "%");

			// TipoServico
			if (notEmpty(filter.getTipoServico()))
				query.setParameter("tipoServico", filter.getTipoServico());

			// Profissinal
			if (notEmpty(filter.getProfissional()))
				query.setParameter("profissional", "%" + filter.getProfissional() + "%");

			// Status
			if (notEmpty(filter.getStatus()))
				query.setParameter("status", filter.getStatus());

			servicos = query.getResultList();
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw new PersistenceException("Ocorreu algum erro ao tentar recuperar os servicos.", e);
		}
		return servicos;
	}

	@RolesAllowed({ "PROFISSIONAL", "CLIENTE" })
	public List<Servico> listarPorUsuario(ServicoFilter filter) throws PersistenciaException {
		List<Servico> servicos = new ArrayList<>();

		try {
			String jpql = "SELECT s FROM Servico s WHERE 1 = 1 and s.usuario = " + filter.getId() + " ";
			// Nome
			if (notEmpty(filter.getNome()))
				jpql += "AND s.nome LIKE :nome ";

			// Descricao
			if (notEmpty(filter.getDescricao()))
				jpql += "AND s.descricao LIKE :descricao ";

			// TipoServico
			if (notEmpty(filter.getTipoServico()))
				jpql += "AND s.tipoServico = :tipoServico ";

			// TipoServico
			if (notEmpty(filter.getProfissional()))
				jpql += "AND s.usuario.nome LIKE :profissional ";

			// TipoServico
			if (notEmpty(filter.getStatus()))
				jpql += "AND s.statusservico = :status ";

			TypedQuery<Servico> query = manager.createQuery(jpql, Servico.class);

			// Nome
			if (notEmpty(filter.getNome()))
				query.setParameter("nome", "%" + filter.getNome() + "%");

			// Descricao
			if (notEmpty(filter.getDescricao()))
				query.setParameter("descricao", "%" + filter.getDescricao() + "%");

			// TipoServico
			if (notEmpty(filter.getTipoServico()))
				query.setParameter("tipoServico", filter.getTipoServico());

			// Profissinal
			if (notEmpty(filter.getProfissional()))
				query.setParameter("profissional", "%" + filter.getProfissional() + "%");

			// Status
			if (notEmpty(filter.getStatus()))
				query.setParameter("status", filter.getStatus());

			servicos = query.getResultList();
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw new PersistenceException("Ocorreu algum erro ao tentar recuperar os servicos.", e);
		}
		return servicos;
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
