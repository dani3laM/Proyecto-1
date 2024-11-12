package Actividad;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Quiz extends Actividad {

	private static final String TIPO = "Quiz";
	private double calificacionMinima;
	private double calificacionFinal;
	private ArrayList<PreguntaCerrada> preguntas;
	public Quiz(String titulo, Boolean obligatoriedad, String descripcion, String objetivo,
			String nivelDificultad, int duracion, String creador, double calificacionMinima) throws Exception {
		super(titulo, TIPO, obligatoriedad, descripcion, objetivo, nivelDificultad, duracion, creador);
		this.calificacionMinima = calificacionMinima;
		this.calificacionFinal = 0;
		this.preguntas = new ArrayList<PreguntaCerrada>();
	}
	public double getCalificacionMinima() {
		return calificacionMinima;
	}
	public void setCalificacionMinima(double calificacionMinima) {
		this.calificacionMinima = calificacionMinima;
	}
	public double getCalificacionFinal() {
		return calificacionFinal;
	}
	public void calcularFinal() {
		int correctas = 0;
		int n = preguntas.size();
		for (PreguntaCerrada p: preguntas) {
			if (p.verificarRespuesta()) {
				correctas++;
			}
		}
		calificacionFinal = (double) (correctas/n)*5;
	}
	public ArrayList<PreguntaCerrada> getPreguntas() {
		return preguntas;
	}
	public void setPreguntas(ArrayList<PreguntaCerrada> preguntas) {
		this.preguntas = preguntas;
	}
	public void agregarPregunta(PreguntaCerrada pregunta) {
		preguntas.add(pregunta);
	}
	public String mostrarExplicaciones() {
		String completa = "";
		for (PreguntaCerrada p: preguntas) {
			String descripcion = p.getDescripcion();
			String opc = p.explicaciones();
			completa = completa + descripcion + "\n" +opc;
		}
		return completa;
	}
	public void enviar() {
		LocalDateTime hora = LocalDateTime.now();
		setHoraFin(hora);
		calcularTiempoDedicado(horaInicio, horaFin);
		calcularFinal();
		actualizacionResultado();

	}
	public void actualizacionResultado() {
		if (calificacionFinal>=calificacionMinima) {
			this.setResultado("Exitoso");
		}
		else {
			this.setResultado("No exitoso");
		}
	}
	public Quiz copiarActividad(String usuario) throws Exception {
		Quiz copia;
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
		double minimo = getCalificacionMinima();
		copia = new Quiz (titulo, obligatoriedad, descripcion, objetivo, nivel, duracion, creador, minimo);
		copia.setFeedbacks(feedbacks);;
		copia.setPrerrequisitos(prerresuisitos);
		copia.setRecomendacionesAprobado(recoAprobado);
		copia.setRecomendacionesNoAprobado(recoNoAprobado);
		copia.setPreguntas(getPreguntas());
		return copia;
	}	
}
