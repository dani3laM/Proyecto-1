package Usuario;

public abstract class Usuario {

	protected String login;
	protected String contrasena;
	public Usuario(String login, String contrasena) {
		super();
		this.login = login;
		this.contrasena = contrasena;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getContrasena() {
		return contrasena;
	}
	public void setContrasena(String contraseña) {
		this.contrasena = contraseña;
	}
	public abstract String getRol();
}
