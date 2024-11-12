package Actividad;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Examen extends Actividad{

	private static final String TIPO = "Examen";
	private Boolean revision;
	private double calificacion;
	private ArrayList<PreguntaAbierta> preguntas;
	public Examen(String titulo, Boolean obligatoriedad, String descripcion, String objetivo,
			String nivelDificultad, int duracion, String creador) throws Exception {
		super(titulo, TIPO, obligatoriedad, descripcion, objetivo, nivelDificultad, duracion, creador);
		this.revision = false;
		this.calificacion = 0;
	}
	public Boolean getRevision() {
		return revision;
	}
	public void setRevision(Boolean revision) {
		this.revision = revision;
	}
	public double getCalificacion() {
		return calificacion;
	}
	public void setCalificacion(double calificacion) {
		setRevision(true);
		this.calificacion = calificacion;
		actualizacionResultado();
	}
	public ArrayList<PreguntaAbierta> getPreguntas() {
		return preguntas;
	}
	public void setPreguntas(ArrayList<PreguntaAbierta> preguntas) {
		this.preguntas = preguntas;
	}
	public void enviar() {
		setResultado("Enviado");
		LocalDateTime hora = LocalDateTime.now();
		setHoraFin(hora);
		calcularTiempoDedicado(horaInicio, horaFin);

	}
	public String revisarRespuestas() {
		String completa = "";
		for (PreguntaAbierta p: preguntas) {
			String descripcion = p.getDescripcion();
			String opc = p.getRespuesta();
				completa = completa + descripcion + "\n" +opc;
			}
			return completa;
	}
	public void actualizacionResultado() {
		String r;
		if (this.calificacion >= 3) {
			r = "Exitoso";
		} else {
			r = "No exitoso";
		}
		this.setResultado(r);
	}
	public Examen copiarActividad(String usuario) throws Exception {
		Examen copia;
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
		copia = new Examen(titulo, obligatoriedad, descripcion, objetivo, nivel, duracion, creador);
		copia.setFeedbacks(feedbacks);;
		copia.setPrerrequisitos(prerresuisitos);
		copia.setRecomendacionesAprobado(recoAprobado);
		copia.setRecomendacionesNoAprobado(recoNoAprobado);
		copia.setPreguntas(getPreguntas());
		return copia;	}

}
