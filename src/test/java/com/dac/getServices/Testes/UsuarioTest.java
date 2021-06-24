package com.dac.getServices.Testes;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.ejb.EJB;
import javax.persistence.PersistenceException;

import org.junit.Test;

import br.edu.ifpb.dac.getservices.model.Usuario;
import br.edu.ifpb.dac.getservices.service.PersistenciaException;
import br.edu.ifpb.dac.getservices.service.ServiceUsuario;
import exception.DacException;

public class UsuarioTest {

	@EJB
	private ServiceUsuario usuarioService;

	@Test
	public void CadastrarUsuarioTest() throws PersistenciaException {
		Usuario usuario = new Usuario();
		usuario.setLogin("teste1@gmail.com");
		usuario.setNome("teste1");
		usuario.setSenha("teste123");
		usuarioService.salvar(usuario);
		Usuario usuarioSalvo = usuarioService.buscaPeloId(usuario.getId());
		assertNotNull(usuarioSalvo);
	}
	
	@Test(expected = java.lang.NullPointerException.class)
	public void CadastrarUsuarioComCampoVazioTest() throws PersistenciaException {
		Usuario usuario = new Usuario();
		usuario.setLogin("teste1@gmail.com");
		usuario.setSenha("teste123");
		usuarioService.salvar(usuario);
		usuarioService.buscaPeloId(usuario.getId());
	}

	@Test
	public void atualizarUsuarioTest() throws PersistenceException, DacException {
		Usuario usuario = usuarioService.buscaPeloId(1);
		String nome = usuario.getNome();
		usuario.setNome("Flavianny Diniz");
		usuarioService.editar(usuario, false);
		assertNotEquals(nome, usuario.getNome());
	}

	@Test
	public void ExcluirUsuarioTest() throws PersistenciaException {
		Usuario usuario = usuarioService.buscaPeloId(1);
		usuarioService.deletar(usuario);
		Usuario usuarioRemovido = usuarioService.buscaPeloId(1);
		assertNull(usuarioRemovido.getId());
	}

}
