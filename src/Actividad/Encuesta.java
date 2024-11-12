package Actividad;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Encuesta extends Actividad {

	private static final String TIPO = "Encuesta";
	private ArrayList<PreguntaAbierta> preguntas;
	
	public Encuesta(String titulo, Boolean obligatoriedad, String descripcion, String objetivo,
			String nivelDificultad, int duracion, String creador) throws Exception {
		super(titulo, TIPO, obligatoriedad, descripcion, objetivo, nivelDificultad, duracion, creador);
		this.preguntas = new ArrayList<PreguntaAbierta>();
	}
	public ArrayList<PreguntaAbierta> getPreguntas() {
		return preguntas;
	}
	public void setPreguntas(ArrayList<PreguntaAbierta> preguntas) {
		this.preguntas = preguntas;
	}
	public void agregarPregunta(PreguntaAbierta pregunta) {
		this.preguntas.add(pregunta);
	}
	public void enviar() {
		actualizacionResultado();
		LocalDateTime hora = LocalDateTime.now();
		setHoraFin(hora);
		calcularTiempoDedicado(horaInicio, horaFin);
	}
	public void actualizacionResultado() {
		String r = "Completado";
		this.setResultado(r);
	}
	public Encuesta copiarActividad(String usuario) throws Exception {
		Encuesta copia;
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
		copia = new Encuesta(titulo, obligatoriedad, descripcion, objetivo, nivel, duracion, creador);
		copia.setFeedbacks(feedbacks);;
		copia.setPrerrequisitos(prerresuisitos);
		copia.setRecomendacionesAprobado(recoAprobado);
		copia.setRecomendacionesNoAprobado(recoNoAprobado);
		copia.setPreguntas(getPreguntas());
		return copia;
	}

}
