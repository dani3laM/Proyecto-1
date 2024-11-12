package Logica;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map.Entry;

import Actividad.Actividad;
import Actividad.Encuesta;
import Actividad.Examen;
import Actividad.Feedback;
import Actividad.Opcion;
import Actividad.PreguntaAbierta;
import Actividad.PreguntaCerrada;
import Actividad.Quiz;
import Actividad.RecursoEducativo;
import Actividad.Tarea;
import Usuario.Estudiante;
import Usuario.Profesor;
import Usuario.Usuario;

public class Sistema {

	private HashMap<String, Usuario> usuarios;
	private HashMap<String, LearningPath> learningPaths;
	private HashMap<String, Actividad> actividades;
	public Sistema() {
		super();
		this.actividades = new HashMap<String, Actividad>();
		this.learningPaths = new HashMap<String, LearningPath>();
		this.usuarios = new HashMap<String, Usuario>();
	}
	public HashMap<String, Usuario> getUsuarios() {
		return usuarios;
	}
	public void setUsuarios(HashMap<String, Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	public HashMap<String, LearningPath> getLearningPaths() {
		return learningPaths;
	}
	public void setLearningPaths(HashMap<String, LearningPath> learningPaths) {
		this.learningPaths = learningPaths;
	}
	public HashMap<String, Actividad> getActividades() {
		return actividades;
	}
	public void setActividades(HashMap<String, Actividad> actividades) {
		this.actividades = actividades;
	}
	public void crearLearningPath(String creador, String titulo, String descripcion, String objetivos, String novelDificultad) throws Exception {
		if (!learningPaths.containsKey(titulo)) {
			LocalDateTime fechaCreacion = LocalDateTime.now();
			LearningPath lp = new LearningPath(titulo, descripcion, objetivos, novelDificultad, fechaCreacion);
			Profesor profesor = (Profesor) usuarios.get(creador);
			HashMap<String, LearningPath> creaciones = profesor.getCreaciones();
			creaciones.put(titulo, lp);
			learningPaths.put(titulo, lp);
		} else {
			throw new Exception("Ya existe un LearningPath con ese titulo.");
		}
	}
	public Opcion crearOpcion(Boolean correcto, String explicacion, String descripcion) {
		Opcion nueva = new Opcion(correcto, explicacion, descripcion);
		return nueva;
	}
	private void editarCorrectoOpcion(Opcion opcion, Boolean correcto) {
		opcion.setCorrecto(correcto);
	}
	private void editarExplicacionOpcion(Opcion opcion, String explicacion) {
		opcion.setExplicacion(explicacion);
	}
	private void editarDescripcionOpcion(Opcion opcion, String descripcion) {
		opcion.setExplicacion(descripcion);
	}
	public void editarOpcion(Opcion opcion, Boolean correcto, String explicacion, String descripcion) {
		if (!correcto.equals(null)) {
			editarCorrectoOpcion(opcion, correcto);
		}
		if (!explicacion.equals(null)) {
			editarExplicacionOpcion(opcion, explicacion);
		}
		if (!descripcion.equals(null)) {
			editarDescripcionOpcion(opcion, descripcion);
		}
	}
	public PreguntaCerrada crearPreguntaCerrada(String descripcion) {
		PreguntaCerrada nueva = new PreguntaCerrada(descripcion);
		return nueva;
	}
	public void agregarOpcion(PreguntaCerrada pregunta, Opcion opcion) throws Exception {
		pregunta.agregarOpcion(opcion);
	}
	public void editarPreguntaCerrada(PreguntaCerrada pregunta, String descripcion) {
		pregunta.setDescripcion(descripcion);
	}
	
	public Quiz crearQuiz(String titulo, Boolean obligatoriedad, String descripcion, String objetivo,
			String nivelDificultad, int duracion, String creador, double calificacionMinima) throws Exception{
		if (!actividades.containsKey(titulo)) {
			Quiz nuevo;
			nuevo = new Quiz(titulo, obligatoriedad, descripcion, objetivo, nivelDificultad, duracion, creador, calificacionMinima);
			actividades.put(titulo, nuevo);
			return nuevo;
		} else { 
			throw new Exception("Ya existe una actividad con ese título.");
		}
			
	}
	public void agregarPreguntaQuiz(Quiz quiz, PreguntaCerrada pregunta) {
		quiz.agregarPregunta(pregunta);
	}
	private void editarTituloActividad(Actividad actividad, String titulo) {
		actividad.setTitulo(titulo);
	}
	private void editarObligatoriedadActividad(Actividad actividad, Boolean obligatoriedad) {
		actividad.setObligatoriedad(obligatoriedad);
	}
	private void editarDescripcionActividad(Actividad actividad, String descripcion) {
		actividad.setDescripcion(descripcion);
	}
	private void editarObjetivoActividad(Actividad actividad, String objetivo) {
		actividad.setObjetivo(objetivo);
	}
	private void editarNivelActividad(Actividad actividad, String nivel) {
		try {
			actividad.setNivelDificultad(nivel);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void editarDuracionActividad(Actividad actividad, int duracion) {
		actividad.setDuracion(duracion);;
	}
	private void editarCalificacionMinimaQuiz(Quiz quiz, double calificacion) {
		quiz.setCalificacionMinima(calificacion);
	}
	public void editarQuiz(Quiz quiz, String titulo, Boolean obligatoriedad, String descripcion, String objetivo,
			String nivelDificultad, int duracion, double calificacionMinima) {
		if (!titulo.equals(null)) {
			editarTituloActividad(quiz, titulo);
		}
		if (!obligatoriedad.equals(null)) {
			editarObligatoriedadActividad(quiz, obligatoriedad);
		}
		if (!descripcion.equals(null)) {
			editarDescripcionActividad(quiz, descripcion);
		}
		if (!objetivo.equals(null)) {
			editarObjetivoActividad(quiz, objetivo);
		}
		if (!nivelDificultad.equals(null)) {
			editarNivelActividad(quiz, nivelDificultad);
		}
		if (duracion>0) {
			editarDuracionActividad(quiz, duracion);
		}
		if (calificacionMinima>0) {
			editarCalificacionMinimaQuiz(quiz, calificacionMinima);		
		}
	}
	public Quiz copiarQuiz(Quiz original, Profesor profesor) throws Exception {
		String login = profesor.getLogin();
		Quiz copia = original.copiarActividad(login);
		actividades.put(copia.getTitulo(), copia);
		return copia;
	}
	public Quiz obtenerQuiz(String titulo) {
		return (Quiz) actividades.get(titulo);
	}
	public PreguntaAbierta crearPreguntaAbierta(String descripcion) {
		PreguntaAbierta nueva = new PreguntaAbierta(descripcion);
		return nueva;
	}
	public void responderPreguntaAbierta(PreguntaAbierta pregunta, String respuesta) {
		pregunta.setRespuesta(respuesta);
	}
	public void editarPreguntaAbierta(PreguntaAbierta pregunta, String descripcion) {
		pregunta.setDescripcion(descripcion);
	}
	public Encuesta crearEncuesta(String titulo, Boolean obligatoriedad, String descripcion, String objetivo,
			String nivelDificultad, int duracion, String creador) throws Exception {
		if (!actividades.containsKey(titulo)) {
			Encuesta nueva = new Encuesta(titulo, obligatoriedad, descripcion, objetivo, nivelDificultad, duracion, creador);
			actividades.put(titulo, nueva);
			return nueva;
		} else {
			throw new Exception("Ya existe una actividad con ese título.");
		}
		
	}
	public void agregarPreguntaEncuesta(Encuesta encuesta, PreguntaAbierta pregunta) {
		encuesta.agregarPregunta(pregunta);
	}
	public Encuesta copiarEncuesta(Encuesta encuesta,  Profesor profesor) throws Exception {
		String usuario = profesor.getLogin();
		Encuesta copia = encuesta.copiarActividad(usuario);
		actividades.put(copia.getTitulo(), copia);
		return copia;

	}
	public void editarEncuesta(Encuesta encuesta, String titulo, Boolean obligatoriedad, String descripcion, String objetivo,
			String nivelDificultad, int duracion) {
		if(!titulo.equals(null)) {
			editarTituloActividad(encuesta, titulo);
		}
		if (!obligatoriedad.equals(null)) {
			editarObligatoriedadActividad(encuesta, obligatoriedad);
		}
		if (!descripcion.equals(null)) {
			editarDescripcionActividad(encuesta, descripcion);
		}
		if (!objetivo .equals(null)) {
			editarObjetivoActividad(encuesta, objetivo);
		}
		if (!nivelDificultad.equals(null)) {
			editarNivelActividad(encuesta, nivelDificultad);
		}
		if (duracion>0) {
			editarDuracionActividad(encuesta, duracion);
		}
	}
	public Encuesta obtenerEncuesta(String titulo) {
		return (Encuesta)actividades.get(titulo);
	}
	public Examen crearExamen(String titulo, Boolean obligatoriedad, String descripcion, String objetivo,
			String nivelDificultad, int duracion, String creador) throws Exception {
		if (!actividades.containsKey(titulo)) {
			Examen nuevo = new Examen(titulo, obligatoriedad, descripcion, objetivo, nivelDificultad, duracion, creador);
			actividades.put(titulo, nuevo);
			return nuevo;
		} else {
			throw new Exception("Ya existe una actividad con ese titulo.");
		}
		
	}
	public Examen copiarExamen(Examen examen, Profesor profesor) throws Exception {
		String usuario = profesor.getLogin();
		Examen copia = examen.copiarActividad(usuario);
		actividades.put(copia.getTitulo(), copia);
		return copia;

	}
	public void editarExamen(Examen examen, String titulo, Boolean obligatoriedad, String descripcion, String objetivo,
			String nivelDificultad, int duracion) {
		if(!titulo.equals(null)) {
			editarTituloActividad(examen, titulo);
		}
		if (!obligatoriedad.equals(null)) {
			editarObligatoriedadActividad(examen, obligatoriedad);
		}
		if (!descripcion.equals(null)) {
			editarDescripcionActividad(examen, descripcion);
		}
		if (!objetivo .equals(null)) {
			editarObjetivoActividad(examen, objetivo);
		}
		if (!nivelDificultad.equals(null)) {
			editarNivelActividad(examen, nivelDificultad);
		}
		if (duracion>0) {
			editarDuracionActividad(examen, duracion);
		}
	}
	public Examen obtenerExamen(String titulo) {
		return (Examen) actividades.get(titulo);
	}
	public void calificarExamen(Examen examen, double calificacion) throws Exception {
		if (calificacion>=0 && calificacion<=5) {
			examen.setCalificacion(calificacion);
		} else {
			throw new Exception("La nota introducida no es válida");
		}
	}
	public RecursoEducativo crearRecurso(String titulo, String tipo, Boolean obligatoriedad, String descripcion, String objetivo,
			String nivelDificultad, int duracion, String creador) throws Exception {
		if (!actividades.containsKey(titulo)) {
			RecursoEducativo nuevo = new RecursoEducativo(titulo, tipo, obligatoriedad, descripcion, objetivo, nivelDificultad, duracion, creador);
			actividades.put(titulo, nuevo);
			return nuevo;
		} else {
			throw new Exception("Ya existe una actividad con ese nombre.");
		}
		
	}
	private void editarTipoRecurso(RecursoEducativo recurso, String tipo) {
		recurso.setTipo(tipo);
	}
	public void editarRecurso(RecursoEducativo recurso, String titulo, String tipo, Boolean obligatoriedad, String descripcion, String objetivo,
			String nivelDificultad, int duracion) {
		if(!titulo.equals(null)) {
			editarTituloActividad(recurso, titulo);
		}
		if (!obligatoriedad.equals(null)) {
			editarObligatoriedadActividad(recurso, obligatoriedad);
		}
		if (!descripcion.equals(null)) {
			editarDescripcionActividad(recurso, descripcion);
		}
		if (!objetivo .equals(null)) {
			editarObjetivoActividad(recurso, objetivo);
		}
		if (!nivelDificultad.equals(null)) {
			editarNivelActividad(recurso, nivelDificultad);
		}
		if (duracion>0) {
			editarDuracionActividad(recurso, duracion);
		}
		if (!tipo.equals(null)) {
			editarTipoRecurso(recurso, tipo);
		}
	}
	public RecursoEducativo copiarRecursoEducativo(RecursoEducativo recurso, Profesor profesor) throws Exception {
		String usuario = profesor.getLogin();
		RecursoEducativo copia = recurso.copiarActividad(usuario);
		actividades.put(copia.getTitulo(), copia);
		return copia;
	}
	public RecursoEducativo obtenerRecurso(String titulo) {
		return (RecursoEducativo) actividades.get(titulo);
	}
	public Tarea crearTarea(String titulo, Boolean obligatoriedad, String descripcion, String objetivo,
			String nivelDificultad, int duracion, String creador) throws Exception {
		if (!actividades.containsKey(titulo)) {
			Tarea nueva = new Tarea(titulo, obligatoriedad, descripcion, objetivo, nivelDificultad, duracion, creador);
			actividades.put(titulo, nueva);
			return nueva;
		} else {
			throw new Exception("Ya existe una actividad con ese título.");
		}				
	}
	public void enviarTarea(String medio, Tarea tarea) {
		tarea.enviar(medio);
	}
	public Tarea copiarTarea(Tarea tarea, Profesor profesor) throws Exception {
		String usuario = profesor.getLogin();
		Tarea copia = tarea.copiarActividad(usuario);
		actividades.put(copia.getTitulo(), copia);
		return copia;

	}
	public void revisarTarea(Tarea tarea, String resultado) throws Exception {
		if (resultado.equals("Exitoso")||resultado.equals("No exitoso")) {
			tarea.setRevision(true);
			tarea.setResultado(resultado);
		} else {
			throw new Exception("El resultado de la tarea no es válido.");
		}
	}
	public Tarea obtenerTarea(String titulo) {
		return (Tarea) actividades.get(titulo);
	}
	public void editarTarea(Tarea tarea, String titulo, Boolean obligatoriedad, String descripcion, String objetivo,
			String nivelDificultad, int duracion) {
		if(!titulo.equals(null)) {
			editarTituloActividad(tarea, titulo);
		}
		if (!obligatoriedad.equals(null)) {
			editarObligatoriedadActividad(tarea, obligatoriedad);
		}
		if (!descripcion.equals(null)) {
			editarDescripcionActividad(tarea, descripcion);
		}
		if (!objetivo .equals(null)) {
			editarObjetivoActividad(tarea, objetivo);
		}
		if (!nivelDificultad.equals(null)) {
			editarNivelActividad(tarea, nivelDificultad);
		}
		if (duracion>0) {
			editarDuracionActividad(tarea, duracion);
		}
	}
	public Feedback crearFeedback(String resena, int rating) throws Exception {
		if (rating>0) {
			Feedback nuevo = new Feedback(resena, rating);
			return nuevo;
		} else {
			Feedback nuevo = new Feedback(resena);
			return nuevo;
		}
	}
	private void editarResenaFB(Feedback feedback, String resena) {
		feedback.setResena(resena);
	}
	private void editarRatingFB(Feedback feedback, int rating) throws Exception {
		feedback.setRating(rating);
	}
	public void editarFB(Feedback feedback, String resena, int rating) throws Exception{
		if (!resena.equals(null)) {
			editarResenaFB(feedback, resena);
		}
		if (rating>0) {
			editarRatingFB(feedback, rating);
		}
	}
	public void agregarFeedbackActividad(Actividad actividad, Feedback feedback) {
		actividad.agregarFeedback(feedback);
	}
	public void agregarRecomendacionNAActividad(Actividad actividad, Actividad recomendacion) {
		actividad.agregarRecomendacionNoAprobado(recomendacion);
	}
	public void agregarRecomendacionAActividad(Actividad actividad, Actividad recomendacion) {
		actividad.agregarRecomendacionAprobado(actividad);
	}
	public void agregarPrerrequisitoActividad(Actividad actividad, Actividad prerrequisito) {
		actividad.agregarPrerrequisito(prerrequisito);
	}
	public LearningPath crearLearningPath(Profesor profesor, String titulo, String descripcion, String objetivos, String nivelDificultad) throws Exception {
		LocalDateTime fechaCreacion = LocalDateTime.now();
		LearningPath nuevo = new LearningPath(titulo, descripcion, objetivos, nivelDificultad, fechaCreacion);
		profesor.getCreaciones().put(titulo, nuevo);
		learningPaths.put(titulo, nuevo);
		return nuevo;
	}
	private void editarTituloLP(LearningPath lp, String titulo) {
		lp.setTitulo(titulo);
	}
	private void editarDescripcionLP(LearningPath lp, String descripcion) {
		lp.setDescripcion(descripcion);
	}
	private void editarObjetivosLP(LearningPath lp, String objetivos) {
		lp.setObjetivos(objetivos);
	}
	private void editarNivelLP(LearningPath lp, String nivel) throws Exception {
		lp.setNivelDificultad(nivel);
	}
	public void editarLP(LearningPath lp, String titulo, String descripcion, String objetivos, String nivelDificultad) throws Exception {
		lp.actualizarFechaModificacion();
		lp.actualizarVersion();
		if (!titulo.equals(null)) {
			editarTituloLP(lp, titulo);
		}
		if (!descripcion.equals(descripcion)) {
			editarDescripcionLP(lp, descripcion);
		}
		if (!objetivos.equals(null)) {
			editarObjetivosLP(lp, objetivos);
		}
		if (!nivelDificultad.equals(null)) {
			editarNivelLP(lp,nivelDificultad);
		}
	}
	public void agregarActividadLP(LearningPath lp, Actividad actividad) {
		lp.agregarActividad(actividad);
	}
	public void agregarActividadFinalizadaLP(LearningPath lp, Actividad actividad) {
		lp.agregarActividadFinalizada(actividad);
		lp.establecerFechaLimite(actividad);
	}
	public void agregarInscritoLP(LearningPath lp, Estudiante inscrito) {
		lp.agregarInscrito(inscrito);
		LocalDateTime inicio = LocalDateTime.now();
		Progreso nuevo = crearProgreso(lp, inicio);
		inscrito.agregarProgreso(nuevo);
	}
	public int obtenerRatingLP(LearningPath lp) {
		lp.calcularRating();
		return lp.getRating();
	}
	public LearningPath obtenerLP(String titulo) {
		return learningPaths.get(titulo);
	}
	public Progreso crearProgreso(LearningPath lp, LocalDateTime inicio) {
		Progreso nuevo = new Progreso(inicio, lp);
		return nuevo;
	}
	public double obtenerPorcentajeProgreso(Estudiante estudiante, String titulo) {
		Progreso progreso = estudiante.getProgresos().get(titulo);
		progreso.calcularPorcentaje();
		return progreso.getPorcentajeProgreso();
	}
	public double obtenerTasaExito(Estudiante estudiante, String titulo) {
		Progreso progreso = estudiante.getProgresos().get(titulo);
		progreso.calcularExito();
		return progreso.getTasaExito();
	}
	public double obtenerTasaFracaso(Estudiante estudiante, String titulo) {
		Progreso progreso = estudiante.getProgresos().get(titulo);
		progreso.calcularFracaso();
		return progreso.getTasaFracaso();
	}
	public void finalizarLP(Estudiante estudiante, String titulo) {
		Progreso progreso = estudiante.getProgresos().get(titulo);
		LearningPath lp = progreso.getLearningPath();
		HashMap<String, Actividad> actividades = lp.getActividades();
		Boolean fin = true;
		for (Entry<String, Actividad> a: actividades.entrySet()) {
			Actividad act = a.getValue();
			if (act.getObligatoriedad() && act.getHoraFin().equals(null)) {
				fin = false;
			}
		}
		if (fin) {
			LocalDateTime fecha = LocalDateTime.now();
			progreso.setFin(fecha);
		} 
	}
	public void editarInteresesEstudiante(Estudiante estudiante, String intereses) {
		estudiante.setIntereses(intereses);
	}
	public Estudiante obtenerEstudiante(String login) {
		return (Estudiante) usuarios.get(login);
	}
	public Profesor obtenerProfesor(String login) {
		return (Profesor) usuarios.get(login);
	}	
	public void iniciarActividad(Actividad actividad, Estudiante estudiante) {
		if (estudiante.getActividadEnProgreso().equals(false)) {
			actividad.iniciar();
		} else {
			System.out.println("No se puede empezar una actividd, porque ya tiene una actividad en proceso");
		}
	}
}
