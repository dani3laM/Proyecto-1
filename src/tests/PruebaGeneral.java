package tests;


import java.time.LocalDateTime;

import java.util.HashMap;

import Actividad.Examen;
import Actividad.Feedback;
import Actividad.RecursoEducativo;
import Autenticacion.Autenticador;
import Logica.LearningPath;
import Logica.Progreso;
import Logica.Sistema;
import Persistencia.ManejadorDePersistencia;
import Usuario.Estudiante;
import Usuario.Profesor;

public class PruebaGeneral {
	
	
	public static void main(String[] args) throws Exception {
		
		
	    Autenticador autenticador;
	    
	    
	    HashMap<String, LearningPath> creaciones;
    	Sistema sistema = new Sistema();
    	autenticador = new Autenticador(sistema.getUsuarios());
	    creaciones = new HashMap<String,LearningPath>();
	    
	    
	    sistema.crearExamen("Integrales",true,"Estudio de las integrales definidas e indefinidas", 
	            "Aplicar el concepto de integración en problemas matemáticos", 
	            "Avanzado", 
	            20, 
	            "ProfesorLinares");
	    sistema.crearRecurso("Antiderivadas","Recurso Educativo",
	            true, 
	            "Estudio de las antiderivadas y su relación con la integral", 
	            "Aplicar las antiderivadas en problemas matemáticos", 
	            "Avanzado", 
	            15, 
	            "ProfesorLinares");
	     
	    Examen examen = (Examen) sistema.getActividades().get("Integrales");
	    RecursoEducativo recurso = (RecursoEducativo) sistema.getActividades().get("Antiderivadas");
	    
		// pruebas registrar usuario
		autenticador.registrarUsuario("j.perez", "12345", "Estudiante", "software,diseno,Matematicas");
		autenticador.registrarUsuario("ProfesorLinares", "54321", "PROFESOR", "");
		
		System.out.println("resultado de el ingreso con contrasena correcta: " + autenticador.verificarIngreso("j.perez", "12345"));
		
		// prueba crear learningpath
		((Profesor)sistema.getUsuarios().get("ProfesorLinares")).setCreaciones(creaciones);
    	String titulo = "Matematicas Avanzadas";
    	sistema.crearLearningPath(((Profesor)sistema.getUsuarios().get("ProfesorLinares")),titulo, "Curso para matemáticas de grado más alto", "Aprender conceptos avanzados de cálculo", "Avanzado");
    	LearningPath learningpath = sistema.getLearningPaths().get(titulo);
    	System.out.println("Learningpath creado por el profesor:"+ ((Profesor)sistema.getUsuarios().get("ProfesorLinares")).getCreaciones().get(titulo).getTitulo());
    	
    	// prueba editar learningpath
    	//LocalDateTime fecha = LocalDateTime.now();
        examen.agregarPrerrequisito(recurso);
        learningpath.agregarActividad(recurso);
        learningpath.agregarActividad(examen);
        System.out.println("Las actividades en el learningpath son: " + learningpath.getActividades().get("Antiderivadas").getTitulo() + " y " + learningpath.getActividades().get("Integrales").getTitulo());
    	
        // prueba recomendaciones para estudiante
        String recomendacion = "";
        String[] intereses = ((Estudiante)sistema.getUsuarios().get("j.perez")).getIntereses().split(",");
        for (String elemento : sistema.getLearningPaths().keySet()) {
        	for (String interes : intereses)  {
        		if (elemento.contains(interes)) {
        			recomendacion += "Se le recomienda al estudiante explorar el Datapath: " + elemento;
        		}
            	
            }
        }
        if (recomendacion.equals("")) {
        	recomendacion = "No se encontraron Learningpaths que se alinien con los intereses del estudiante";
        }
        System.out.println(recomendacion);
        
        //prueba progreso estudiante
        
        Estudiante estudiante = ((Estudiante)sistema.getUsuarios().get("j.perez"));
        HashMap<String, Progreso> progresos = new HashMap<String, Progreso>();
        estudiante.setProgresos(progresos);
        Progreso progreso = new Progreso(LocalDateTime.of(2024, 10, 25, 8, 30, 2),learningpath);
        learningpath.agregarInscrito(estudiante);
        estudiante.agregarProgreso(progreso);
        
        RecursoEducativo RecursoEstudiante = (RecursoEducativo) estudiante.getProgresos().get(titulo).getLearningPath().getActividades().get("Antiderivadas");
        RecursoEstudiante.iniciar();
        
        Examen ExamenEstudiante = (Examen) estudiante.getProgresos().get(titulo).getLearningPath().getActividades().get("Integrales");
        ExamenEstudiante.iniciar();
        
        
        RecursoEstudiante.finalizar();
        ExamenEstudiante.iniciar();
        ExamenEstudiante.enviar();
        progreso.calcularPorcentaje();
        
        System.out.println(progreso.getPorcentajeProgreso());
        
        
        //prueba feedback
        
        String contenidoFeedbackEstudiante = "La actividad es muy desafiante y educativa.";
        Feedback feedbackEstudiante = new Feedback(contenidoFeedbackEstudiante, 4);

        recurso.agregarFeedback(feedbackEstudiante);
        
        String contenidoFeedbackProfesor = "La actividad está bien estructurada, pero no cubre todos los matices del tema";
        Feedback feedbackProfesor = new Feedback(contenidoFeedbackProfesor, 4);

        recurso.agregarFeedback(feedbackProfesor);
        
        System.out.println("feedback actividad antiderivadas: " + recurso.getFeedbacks().getFirst().getResena() + ", " + recurso.getFeedbacks().getLast().getResena());
        
        //prueba calificar
        
        Profesor profesor =(Profesor) sistema.getUsuarios().get("ProfesorLinares");
        LearningPath pathgeneral = profesor.getCreaciones().get("Matematicas Avanzadas");
        Progreso prog = pathgeneral.getInscritos().get("j.perez").getProgresos().get("Matematicas Avanzadas");
        Examen exam = (Examen) prog.getLearningPath().getActividades().get("Integrales");
        exam.setCalificacion(3.5);
        prog.calcularExito();
        prog.calcularPorcentaje();
        if (exam == ExamenEstudiante) {
        	System.out.println("El examen del estudiante se edito con exito");
        }
        
        System.out.println("El porcentaje de exito del estudiante en sus examenes es: " + estudiante.getProgresos().get("Matematicas Avanzadas").getTasaExito());
        System.out.println("La calificacion obtenida en el ultimo examen es: " + exam.getCalificacion());
        
        
        //prueba ver progreso profesor
        System.out.println("La fecha de inicio vista por el profesor es: " + exam.getHoraInicio());
        System.out.println("La fecha de finalizacion vista por el profesor es: " + exam.getHoraFin());
        
        //probar persistencia
        ManejadorDePersistencia persistencia = new ManejadorDePersistencia(sistema);
        persistencia.guardarLearningPaths();
        persistencia.guardarUsuarios();

	}


}
