package filters;

public class UsuarioFilter implements Filter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nome;
	private String login;

	public UsuarioFilter() {
		// TODO Auto-generated constructor stub

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public boolean isEmpty() {
		if (nome != null)
			return false;
		if (login != null)
			return false;
		return true;
	}

}
