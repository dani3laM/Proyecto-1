package Actividad;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Tarea extends Actividad{
	
	private static final String TIPO = "Tarea";
	private Boolean finalizacion;
	private String medio;
	private Boolean revision;
	
	public Tarea(String titulo, Boolean obligatoriedad, String descripcion, String objetivo,
			String nivelDificultad, int duracion, String creador) throws Exception {
		super(titulo, TIPO, obligatoriedad, descripcion, objetivo, nivelDificultad, duracion, creador);
		this.finalizacion = false;
		this.medio = "";
		this.revision = false;
	}
	public Boolean getFinalizacion() {
		return finalizacion;
	}
	public void setFinalizacion(Boolean finalizacion) {
		this.finalizacion = finalizacion;
	}
	public String getMedio() {
		return medio;
	}
	public void setMedio(String medio) {
		this.medio = medio;
	}
	public Boolean getRevision() {
		return revision;
	}
	public void setRevision(Boolean revision) {
		this.revision = revision;
	}
	public void enviar(String medio) {
		setResultado("Enviado");
		LocalDateTime hora = LocalDateTime.now();
		setHoraFin(hora);
		calcularTiempoDedicado(horaInicio, horaFin);
		setMedio(medio);
		setFinalizacion(true);
		

	}
	public Tarea copiarActividad(String usuario) throws Exception {
		Tarea copia;
		String titulo = getTitulo();
		titulo = "Copia de "+titulo+" de "+creador;
		Boolean obligatoriedad = getObligatoriedad();
		String descripcion = getDescripcion();
		String objetivo = getObjetivo();
		String nivel = getNivelDificultad();
		int duracion = getDuracion();
		String creador = getCreador();
		if (!creador.equalsIgnoreCase(usuario)) {
			creador = usuario;
		}
		HashMap<String, Actividad> recoAprobado = getRecomendacionesAprobado();
		HashMap<String, Actividad> recoNoAprobado = getRecomendacionesNoAprobado();
		HashMap<String, Actividad> prerresuisitos = getPrerrequisitos();
		ArrayList<Feedback> feedbacks = getFeedbacks();
		copia = new Tarea(titulo, obligatoriedad, descripcion, objetivo, nivel, duracion, creador);
		copia.setFeedbacks(feedbacks);;
		copia.setPrerrequisitos(prerresuisitos);
		copia.setRecomendacionesAprobado(recoAprobado);
		copia.setRecomendacionesNoAprobado(recoNoAprobado);
		return copia;
	}
	
}
