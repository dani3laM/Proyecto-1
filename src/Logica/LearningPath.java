package Logica;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map.Entry;

import Actividad.Actividad;
import Actividad.Feedback;
import Usuario.Estudiante;


public class LearningPath {

	private String titulo;
	private String descripcion;
	private String objetivos;
	private String nivelDificultad;
	private int duracion;
	private int rating;
	private LocalDateTime fechaCreacion;
	private LocalDateTime fechaModificacion;
	private double version;
	private HashMap<String, Actividad> actividades;
	private LinkedList<Actividad> secuenciaActividades;
	private HashMap<String, Actividad> actividadesFinalizadas;
	private HashMap<String, Estudiante> inscritos;
	public LearningPath(String titulo, String descripcion, String objetivos, String nivelDificultad,
			LocalDateTime fechaCreacion) throws Exception {
		super();
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.objetivos = objetivos;
		setNivelDificultad(nivelDificultad);
		this.duracion = 0;
		this.fechaCreacion = fechaCreacion;
		this.version = 1;
		this.actividades = new HashMap<String, Actividad>();
		this.actividadesFinalizadas = new HashMap<String,Actividad>();
		this.inscritos = new HashMap<String, Estudiante>();
		this.rating = 0;
		this.setSecuenciaActividades(new LinkedList<Actividad>());
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getObjetivos() {
		return objetivos;
	}
	public void setObjetivos(String objetivos) {
		this.objetivos = objetivos;
	}
	public String getNivelDificultad() {
		return nivelDificultad;
	}
	public void setNivelDificultad(String nivelDificultad) throws Exception {
		if (nivelDificultad.equals("Principiante")||nivelDificultad.equals("intermedio")||nivelDificultad.equals("Avanzado")){
			this.nivelDificultad = nivelDificultad;
			
		} else {
			throw new Exception("Ese nivel de dificultad no se maneja.");
		}
	}
	public int getDuracion() {
		return duracion;
	}
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}
	public LocalDateTime getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(LocalDateTime fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public double getVersion() {
		return version;
	}
	public void setVersion(double version) {
		this.version = version;
	}
	public HashMap<String, Actividad> getActividades() {
		return actividades;
	}
	public void setActividades(HashMap<String, Actividad> actividades) {
		this.actividades = actividades;
	}
	public void agregarActividad(Actividad actividad) {
		actividades.put(actividad.getTitulo(), actividad);
		secuenciaActividades.add(actividad);
	}
	public HashMap<String, Actividad> getActividadesFinalizadas() {
		return actividadesFinalizadas;
	}
	public void setActividadesFinalizadas(HashMap<String,Actividad> actividadesFinalizadas) {
		this.actividadesFinalizadas = actividadesFinalizadas;
	}
	public void agregarActividadFinalizada(Actividad actividad) {
		actividadesFinalizadas.put(actividad.getTitulo(), actividad);
	}
	public HashMap<String, Estudiante> getInscritos() {
		return inscritos;
	}
	public void setInscritos(HashMap<String, Estudiante> inscritos) {
		this.inscritos = inscritos;
	}
	public void agregarInscrito(Estudiante inscrito) {
		inscritos.put(inscrito.getLogin(), inscrito);
	}
	public void calcularRating() {
		int n = 0;
		int r = 0;
		for (Actividad a:actividades.values()) {
			for (Feedback f:a.getFeedbacks()) {
				if (f.getRating()>0) {
					n++;
					r=r+f.getRating();
				}
			}
		}
		if (n==0) {
			this.rating = 0;
		} else {
		this.rating = r/n;
		}
		
	}
	public void actualizarFechaModificacion() {
		this.fechaModificacion = LocalDateTime.now();
	}
	public void actualizarVersion() {
		this.version=this.version+0.1;
	}
	public void calcularDuracion() {
		int duracion = 0;
		for (Entry<String, Actividad> a : actividades.entrySet()) {
	        duracion += a.getValue().getDuracion();
	        }
		setDuracion(duracion);
	}
	public LinkedList<Actividad> getSecuenciaActividades() {
		return secuenciaActividades;
	}
	public void setSecuenciaActividades(LinkedList<Actividad> secuenciaActividades) {
		this.secuenciaActividades = secuenciaActividades;
	}
	public void establecerFechaLimite(Actividad actual) {
		ListIterator<Actividad> iterador = secuenciaActividades.listIterator();
		Boolean seguir = true;
		while (iterador.hasNext()&seguir) {
			Actividad presente = iterador.next();
			if (presente.equals(actual)) {
				if (iterador.hasNext()) {
					Actividad siguiente = iterador.next();
					LocalDateTime finAnterior = presente.getHoraFin();
					if (!finAnterior.equals(null)) {
					LocalDateTime limite = finAnterior.plusHours(1);
					siguiente.setFechaLimite(limite);
					}
					seguir = false;
				} else {
					seguir = false;
				}
			}
		}
	}
}
