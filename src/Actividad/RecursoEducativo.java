package Actividad;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

public class RecursoEducativo extends Actividad{

	private Boolean finalizacion;


	public RecursoEducativo(String titulo, String tipo, Boolean obligatoriedad, String descripcion, String objetivo,
			String nivelDificultad, int duracion, String creador) throws Exception {
		super(titulo, tipo, obligatoriedad, descripcion, objetivo, nivelDificultad, duracion, creador);
		this.finalizacion = false;
	}
	public Boolean getFinalizacion() {
		return finalizacion;
	}
	public void setFinalizacion(Boolean finalizacion) {
		this.finalizacion = finalizacion;
	}
	public void finalizar() {
		actualizacionResultado();
		setFinalizacion(true);
		LocalDateTime hora = LocalDateTime.now();
		setHoraFin(hora);
		calcularTiempoDedicado(horaInicio, horaFin);

	}
	public void actualizacionResultado() {
		String r = "Exitoso";
		this.setResultado(r);
	}
	public RecursoEducativo copiarActividad(String usuario) throws Exception {
		RecursoEducativo copia;
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
		String tipo = getTipo();
		HashMap<String, Actividad> recoAprobado = getRecomendacionesAprobado();
		HashMap<String, Actividad> recoNoAprobado = getRecomendacionesNoAprobado();
		HashMap<String, Actividad> prerresuisitos = getPrerrequisitos();
		ArrayList<Feedback> feedbacks = getFeedbacks();
		copia = new RecursoEducativo(titulo,tipo, obligatoriedad, descripcion, objetivo, nivel, duracion, creador);
		copia.setFeedbacks(feedbacks);;
		copia.setPrerrequisitos(prerresuisitos);
		copia.setRecomendacionesAprobado(recoAprobado);
		copia.setRecomendacionesNoAprobado(recoNoAprobado);
		return copia;
	}
	
}
