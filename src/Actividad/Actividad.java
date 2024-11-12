package Actividad;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public abstract class Actividad {

	protected String titulo;
	protected String tipo;
	protected Boolean obligatoriedad;
	protected String descripcion;
	protected String objetivo;
	protected String nivelDificultad;
	protected int duracion;
	protected LocalDateTime fechaLimite;
	protected String resultado;
	protected int tiempoDedicado;
	protected LocalDateTime horaInicio;
	protected LocalDateTime horaFin;
	protected String creador;
	protected HashMap<String, Actividad> recomendacionesAprobado;
	protected HashMap<String, Actividad> prerrequisitos;
	protected HashMap<String, Actividad> recomendacionesNoAprobado;
	protected ArrayList<Feedback> feedbacks;
	public Actividad(String titulo, String tipo, Boolean obligatoriedad, String descripcion, String objetivo,
			String nivelDificultad, int duracion, String creador) throws Exception {
		super();
		this.titulo = titulo;
		this.tipo = tipo;
		this.obligatoriedad = obligatoriedad;
		this.descripcion = descripcion;
		this.objetivo = objetivo;
		setNivelDificultad(nivelDificultad);
		this.duracion = duracion;
		this.creador = creador;
		this.resultado = "Incompleto";
		this.recomendacionesAprobado = new HashMap<String, Actividad>();
		this.prerrequisitos = new HashMap<String, Actividad>();
		this.recomendacionesNoAprobado = new HashMap<String, Actividad>();
		this.feedbacks = new ArrayList<Feedback>();
		
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public Boolean getObligatoriedad() {
		return obligatoriedad;
	}
	public void setObligatoriedad(Boolean obligatoriedad) {
		this.obligatoriedad = obligatoriedad;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getObjetivo() {
		return objetivo;
	}
	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}
	public String getNivelDificultad() {
		return nivelDificultad;
	}
	public void setNivelDificultad(String nivelDificultad) throws Exception {
		if (nivelDificultad.equals("Principiante")||nivelDificultad.equals("intermedio")||nivelDificultad.equals("Avanzado")){
			this.nivelDificultad = nivelDificultad;
		} else {
			throw new Exception("No existe ese nivel de dificultad.");
		}
	}
	public int getDuracion() {
		return duracion;
	}
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	public LocalDateTime getFechaLimite() {
		return fechaLimite;
	}
	public void setFechaLimite(LocalDateTime fechaLimite) {
		this.fechaLimite = fechaLimite;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public int getTiempoDedicado() {
		return tiempoDedicado;
	}
	public void setTiempoDedicado(int tiempoDedicado) {
		this.tiempoDedicado = tiempoDedicado;
	}
	public LocalDateTime getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(LocalDateTime horaInicio) {
		this.horaInicio = horaInicio;
	}
	public LocalDateTime getHoraFin() {
		return horaFin;
	}
	public void setHoraFin(LocalDateTime horaFin) {
		this.horaFin = horaFin;
	}
	public String getCreador() {
		return creador;
	}
	public void setCreador(String creador) {
		this.creador = creador;
	}
	public HashMap<String, Actividad> getRecomendacionesAprobado() {
		return recomendacionesAprobado;
	}
	public void setRecomendacionesAprobado(HashMap<String, Actividad> recomendacionesAprobado) {
		this.recomendacionesAprobado = recomendacionesAprobado;
	}
	public void agregarRecomendacionAprobado(Actividad actividad) {
		recomendacionesAprobado.put(actividad.getTitulo(), actividad);
	}
	public HashMap<String, Actividad> getPrerrequisitos() {
		return prerrequisitos;
	}
	public void setPrerrequisitos(HashMap<String, Actividad> prerrequisitos) {
		this.prerrequisitos = prerrequisitos;
	}
	public void agregarPrerrequisito(Actividad actividad) {
		prerrequisitos.put(actividad.getTitulo(), actividad);
	}
	public HashMap<String, Actividad> getRecomendacionesNoAprobado() {
		return recomendacionesNoAprobado;
	}
	public void setRecomendacionesNoAprobado(HashMap<String, Actividad> recomendacionesNoAprobado) {
		this.recomendacionesNoAprobado = recomendacionesNoAprobado;
	}
	public void agregarRecomendacionNoAprobado(Actividad actividad) {
		recomendacionesNoAprobado.put(actividad.getTitulo(), actividad);
	}
	public ArrayList<Feedback> getFeedbacks() {
		return feedbacks;
	}
	public void setFeedbacks(ArrayList<Feedback> feedbacks) {
		this.feedbacks = feedbacks;
	}
	public void agregarFeedback(Feedback feedback) {
		feedbacks.add(feedback);
	}
	public void calcularTiempoDedicado(LocalDateTime inicio, LocalDateTime fin) {
		Duration d;
		d = Duration.between(inicio, fin);
		long duracion = d.toMinutes();
		this.duracion = (int) duracion;
	}
	public Boolean revisarCreador(String usuario) {
		if (this.creador.equalsIgnoreCase(usuario)) {
			return true;
		} else {
			return false;
		}
	}
	public void iniciar() {
		Boolean incompletos = false;
		for (Entry<String, Actividad> a: prerrequisitos.entrySet()) {
			Actividad valor = a.getValue();
			if (valor.getResultado().equals("Incompleto")) {
				incompletos = true;
			}
		}
		if (incompletos) {
			System.out.println("No has completado todos los prerrequisitos, por lo que puede que no cuentes con las bases necesarias para realizar la actividad todavía.");
		}
		LocalDateTime hora = LocalDateTime.now();
		setHoraInicio(hora);
	}
}
