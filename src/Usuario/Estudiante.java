package Usuario;

import java.util.HashMap;

import Logica.Progreso;

public class Estudiante extends Usuario {

	private Boolean actividadEnProgreso;
	private String intereses;
	private HashMap<String,Progreso> progresos;
	private static final String ROL = "Estudiante";
	public Estudiante(String login, String contraseña, String intereses) {
		super(login, contraseña);
		this.intereses = intereses;
		this.actividadEnProgreso=false;
	}
	public Boolean getActividadEnProgreso() {
		return actividadEnProgreso;
	}
	public void setActividadEnProgreso(Boolean actividadEnProgreso) {
		this.actividadEnProgreso = actividadEnProgreso;
	}
	public String getIntereses() {
		return intereses;
	}
	public void setIntereses(String intereses) {
		this.intereses = intereses;
	}
	public HashMap<String, Progreso> getProgresos() {
		return progresos;
	}
	public void setProgresos(HashMap<String, Progreso> progresos) {
		this.progresos = progresos;
	}
	public void agregarProgreso(Progreso progreso) {
		this.progresos.put(progreso.getLearningPath().getTitulo(), progreso);
	}
	public String getRol() {
		return ROL;
	}
	
}
