package br.edu.ifpb.dac.getservices.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.mindrot.jbcrypt.BCrypt;
import br.edu.ifpb.dac.getservices.model.Usuario;
import exception.DacException;
import filters.UsuarioFilter;

@Stateless
public class ServiceUsuario {

	@PersistenceContext
	private EntityManager manager;

	@PostConstruct
	void aposCriacao() {
		System.out.println("[INFO] UsuarioDao foi criado.");
	}

	public void salvar(Usuario usuario) throws PersistenciaException {
		System.out.println("[INFO] Salvando o Usuario " + usuario.getNome());

		try {
			usuario.setSenha(transformaSenhaEmHash(usuario.getSenha()));
			manager.persist(usuario);
			System.out.println("[INFO] Salvou o Usuario " + usuario.getNome());
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException("Ocorreu algum erro ao tentar salvar o usuario.", pe);
		}
	}

	public void editar(Usuario usuario, boolean senhaAlterada) throws DacException, PersistenceException {
		try {
			validarLogin(usuario);
			if (senhaAlterada)
				usuario.setSenha(transformaSenhaEmHash(usuario.getSenha()));
			manager.merge(usuario);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException("Ocorreu algum erro ao tentar atualizar o usuario.", pe);
		}
	}

	public void deletar(Usuario usuario) throws PersistenciaException {
		try {
			usuario = manager.find(Usuario.class, usuario.getId());
			manager.remove(usuario);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException("Ocorreu algum erro ao tentar remover o usuario.", pe);
		}
	}
	
	public Usuario buscaPeloId(Integer id) throws PersistenciaException {
		Usuario usuario = null;
		try {
			usuario = manager.find(Usuario.class, id);
		} catch (PersistenceException pe) {
			pe.printStackTrace();
			throw new PersistenciaException("Ocorreu algum erro ao tentar recuperar o usuario com base no ID.", pe);
		}
		return usuario;
	}

	public List<Usuario> listar(UsuarioFilter filter) throws PersistenciaException {
		List<Usuario> usuarios = new ArrayList<>();

		try {
			String jpql = "SELECT u FROM Usuario u WHERE 1 = 1 ";
			// Nome
			if (notEmpty(filter.getNome()))
				jpql += "AND u.nome LIKE :nome ";

			// Login
			if (notEmpty(filter.getLogin()))
				jpql += "AND u.login LIKE :login ";

			TypedQuery<Usuario> query = manager.createQuery(jpql, Usuario.class);

			// Nome
			if (notEmpty(filter.getNome()))
				query.setParameter("nome", "%" + filter.getNome() + "%");

			// Login
			if (notEmpty(filter.getLogin()))
				query.setParameter("login", "%" + filter.getLogin() + "%");

			usuarios = query.getResultList();
		} catch (PersistenceException e) {
			e.printStackTrace();
			throw new PersistenceException("Ocorreu algum erro ao tentar recuperar os usuarios.", e);
		}
		return usuarios;
	}

	private String transformaSenhaEmHash(String senhaBruta) {
		System.out.println("Gerando Hash usando Bcrypt");
		String senhaHashed = null;
		String salto = null;

		salto = BCrypt.gensalt();
		senhaHashed = BCrypt.hashpw(senhaBruta, salto);

		System.out.println("Bcrypt - senhaHash: " + senhaHashed);

		return senhaHashed;
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

	public Usuario buscaPeloLoginESenha(String login, String senha) {
		System.out.println("[INFO] Consultando o usuario pelo e-mail: " + login);

		Usuario usuarioRecuperado = null;
		try {

			usuarioRecuperado = manager.createQuery("select u from Usuario u where u.login = :login", Usuario.class)
					.setParameter("login", login).getSingleResult();

		} catch (NoResultException nre) {
			return null;
		}

		if (usuarioRecuperado != null) {
			if (verificaSenhaHash(senha, usuarioRecuperado.getSenha())) {
				return usuarioRecuperado;
			}

		}

		return null;
	}

	private boolean verificaSenhaHash(String senha, String senhaRecuperada) {
		return (BCrypt.checkpw(senha, senhaRecuperada));
	}

	public boolean existUsuarioLogin(Usuario usuario) throws DacException {
		if (empty(usuario) || empty(usuario.getLogin()))
			return false;

		String jpql = "SELECT COUNT(*) FROM Usuario u WHERE u.login = :login ";
		if (notEmpty(usuario.getId()))
			jpql += "AND u != :usuario ";

		TypedQuery<Long> query = manager.createQuery(jpql, Long.class);

		query.setParameter("login", usuario.getLogin());
		if (notEmpty(usuario.getId()))
			query.setParameter("usuario", usuario);

		Long count = query.getSingleResult();
		return count > 0;
	}

	public boolean senhaConfere(Usuario usuario, String senha2) throws DacException {
		// Recuperar verdadeira senha atual (hash)
		String senhaHash = null;
		try {
			senhaHash = buscaPeloId(usuario.getId()).getSenha();
		} catch (PersistenciaException e) {
			throw new DacException(e.getMessage(), e);
		}

		// Programação defensiva contra NPE
		if (senhaHash == null && senha2 == null) {
			return true;
		}

		if (senhaHash == null || senha2 == null) {
			return false;
		}

		// Comparar hash da suposta senha com o verdadeiro hash da senha
		String supostaSenhaHash = transformaSenhaEmHash(senha2);

		return senhaHash.equals(supostaSenhaHash);
	}

	public void validarLogin(Usuario usuario) throws DacException, PersistenciaException {
		boolean jaExiste = existUsuarioLogin(usuario);
		if (jaExiste) {
			throw new DacException("Login already exists: " + usuario.getLogin());
		}
	}

}
