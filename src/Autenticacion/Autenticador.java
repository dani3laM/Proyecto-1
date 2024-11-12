package Autenticacion;

import java.util.HashMap;

import Usuario.Estudiante;
import Usuario.Profesor;
import Usuario.Usuario;

public class Autenticador {

	private HashMap<String, Usuario> registros;
	
	public Autenticador(HashMap<String, Usuario> registros) {
		this.registros= registros;
	}
	public boolean verificarRegistro(String login) {
		if (registros.containsKey(login)) {
			return true;
		} else {
		return false;
		}
	}
	public boolean verificarIngreso(String login, String contrasena) throws Exception {
		if (registros.containsKey(login)) {
			Usuario usuario = registros.get(login);
			String c = usuario.getContrasena();
			if (c.equals(contrasena)) {
				return true;
			} else {
				throw new Exception("Contraseña incorrecta");
			}
		} else {
			throw new Exception("Login incorrecto");
		}
	}
	public void registrarUsuario(String login, String contrasena, String rol, String intereses) throws Exception {
		if (verificarRegistro(login)) {
			throw new Exception("El login escogido no esta disponible.");
		} else {
			if (rol.equalsIgnoreCase("Profesor")) {
				Usuario nuevo = new Profesor(login, contrasena);
				registros.put(login, nuevo);			}
			else {
				Usuario nuevo = new Estudiante(login, contrasena, intereses);
				registros.put(login, nuevo);
			}
		}
	}

}



