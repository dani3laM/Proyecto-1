package Usuario;

import java.util.HashMap;

import Logica.LearningPath;

public class Profesor extends Usuario{

	private HashMap<String, LearningPath> creaciones;
	private static final String ROL = "Profesor";
	public Profesor(String login, String contraseña) {
		super(login, contraseña);
	}

	public HashMap<String, LearningPath> getCreaciones() {
		return creaciones;
	}

	public void setCreaciones(HashMap<String, LearningPath> creaciones) {
		this.creaciones = creaciones;
	}

	public String getRol() {
		return ROL;
	}

}
