package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Actividad.Actividad;
import Actividad.Examen;
import Actividad.Feedback;
import Actividad.Quiz;
import Logica.LearningPath;
import Logica.Progreso;
import Logica.Sistema;
import Usuario.Estudiante;
import Usuario.Profesor;


public class PruebasProfesores {

    Sistema sistema = new Sistema();
    private Estudiante estudiante;
    private Profesor profesor;
   
    
    @BeforeEach
    public void setUp() throws Exception {
        // Inicializamos el profesor y el estudiante
        profesor = new Profesor("ProfesorLinares","54321");
        estudiante = new Estudiante("j.perez","12345","software,diseno,matematicas");
        // Inicializamos un Learning Path
        String titulo = "Matemáticas Avanzadas";
        String descripcion = "Curso para matemáticas de grado más alto";
        String objetivos = "Aprender conceptos avanzados de cálculo";
        String nivelDificultad = "Avanzado";
        sistema.crearLearningPath(profesor,titulo, descripcion, objetivos, nivelDificultad);
        sistema.crearExamen("Integrales",true,"Estudio de las integrales definidas e indefinidas", 
            "Aplicar el concepto de integración en problemas matemáticos", 
            "Avanzado", 
            20, 
            "ProfesorLinares");
        sistema.crearQuiz("Antiderivadas",
            true, 
            "Estudio de las antiderivadas y su relación con la integral", 
            "Aplicar las antiderivadas en problemas matemáticos", 
            "Avanzado", 
            15, 
            "ProfesorLinares",
            3);
        
    }
    
    @Test
    public void testProfesorCreaLearningPath() throws Exception {
    	profesor = new Profesor("ProfesorLinares","54321");
    	String titulo = "Matemáticas Avanzadas";
    	LearningPath learningpath = sistema.getLearningPaths().get(titulo);

        // Verificamos que el Learning Path fue creado correctamente
        assertNotNull(learningpath, "El Learning Path no se creó correctamente");
        assertEquals("Matemáticas Avanzadas", learningpath.getTitulo(), "El nombre del Learning Path no es correcto");


        // Verificamos que la descripción fue añadida correctamente
        assertEquals("Curso para matemáticas de grado más alto", learningpath.getDescripcion(), "La descripción no se añadió correctamente");

    }
    
    
    @Test
    public void testEditarLearningPath() throws Exception {
    	Actividad actividadIntegrales = sistema.getActividades().get("Integrales");
    	Actividad actividadAntiderivadas = sistema.getActividades().get("Antiderivadas");
        // Se le asigna una fecha límite de 20 de Noviembre
        //se debe cambiar por la calculada
        LocalDateTime fechaLimite = LocalDateTime.of(2024, Month.NOVEMBER, 20, 23, 59, 59, 0);
        ((Examen) actividadIntegrales).setFechaLimite(fechaLimite);
        ((Examen) actividadIntegrales).agregarPrerrequisito(actividadAntiderivadas);


        // Verificamos que la actividad "Integrales" fue añadida correctamente
        String titulo = "Matemáticas Avanzadas";
    	LearningPath learningpath = sistema.getLearningPaths().get(titulo);
    	learningpath.agregarActividad(actividadIntegrales);
        assertTrue(learningpath.getActividades().containsValue(actividadIntegrales), "La actividad 'Integrales' no fue añadida");

        // Verificamos que la fecha límite es correcta
        assertEquals(fechaLimite, ((Examen) actividadIntegrales).getFechaLimite(), "La fecha límite de la actividad 'Integrales' no es correcta");

        // Verificamos que el requisito previo es "Antiderivadas"
        assertTrue(((Examen) actividadIntegrales).getPrerrequisitos().containsValue(actividadAntiderivadas), "El requisito previo de la actividad 'Integrales' no es correcto");        
    }
    
    @Test
    public void testAgregarFeedbackProfesor() throws Exception {
    	
        // El profesor agrega un feedback a la actividad
        String contenidoFeedbackProfesor = "La actividad está bien estructurada, pero no cubre todos los matices del tema";
        Feedback feedbackProfesor = new Feedback(contenidoFeedbackProfesor, 4);

        sistema.getActividades().get(feedbackProfesor).agregarFeedback(feedbackProfesor);

        // Verificamos que el feedback fue agregado correctamente
        assertTrue(sistema.getActividades().get("Integrales").getFeedbacks().contains(feedbackProfesor), "El feedback del profesor no fue agregado correctamente");
        assertEquals(contenidoFeedbackProfesor, sistema.getActividades().get("Integrales").getFeedbacks().get(0).getResena(), "El contenido del feedback del profesor no es correcto");
    }
    
    @Test
    public void testProfesorRevisaActividadesYDejaRetroalimentacion() throws Exception {

        String titulo = "Matemáticas Avanzadas";
        LearningPath learningPath = sistema.getLearningPaths().get(titulo);

        Examen actividadExamen = new Examen(
            "Derivadas",
            true,
            "Examen sobre derivadas",
            "Aplicar los conceptos de derivadas",
            "Avanzado",
            30,
            "ProfesorLinares"
        );
        
        learningPath.agregarActividad(actividadExamen);

        
        actividadExamen.enviar(); 
        actividadExamen.setCalificacion(3.6); 

        assertTrue(actividadExamen.getRevision(), "El examen no fue marcado como revisado");
        assertEquals(3.6, actividadExamen.getCalificacion(), "La calificación no es correcta");

        Feedback feedbackProfesor = new Feedback("Buen trabajo, pero revisa los errores en el cálculo." + String.valueOf(actividadExamen.getCalificacion()));
        actividadExamen.agregarFeedback(feedbackProfesor);

        assertTrue(actividadExamen.getFeedbacks().contains(feedbackProfesor), "El feedback del profesor no fue agregado correctamente");
        assertEquals("Buen trabajo, pero revisa los errores en el cálculo.", feedbackProfesor.getResena(), "El contenido del feedback no es correcto");

        Estudiante estudianteJperez = (Estudiante) sistema.getUsuarios().get("j.perez");
        assertTrue(estudianteJperez.getActividadesEntregadas().contains(actividadExamen), "El estudiante no puede ver la actividad entregada");
        assertTrue(actividadExamen.getFeedbacks().contains(feedbackProfesor), "El feedback no es visible para el estudiante");
    }
    
    @Test
    public void testVerProgresoEstudiante() {
    	String titulo = "Matemáticas Avanzadas";
    	LearningPath learningpath = sistema.getLearningPaths().get(titulo);

        // Aquí el profesor revisa el progreso de j.perez
        Progreso progresoEstudiante = sistema.crearProgreso(learningpath,LocalDateTime.now());

        // Verificamos que se muestre la información correcta
        assertNotNull(progresoEstudiante, "El progreso del estudiante no se encontró.");
        assertEquals(estudiante.getLogin(), "j.perez", "El nombre del estudiante no coincide.");
        assertEquals("Matemáticas Avanzadas", progresoEstudiante.getLearningPath().getTitulo(), "El Learning Path no coincide.");
        assertTrue(progresoEstudiante.getPorcentajeProgreso() >= 0, "El puntaje total no es válido.");
        assertTrue(progresoEstudiante.getTasaExito() >= 0 && progresoEstudiante.getTasaExito() <= 100, "La tasa de éxito debe estar entre 0 y 100.");
        
        // Verificar las fechas de inicio y fin del curso
        assertNotNull(progresoEstudiante.getInicio(), "La fecha de inicio no debe ser nula.");
        assertNotNull(progresoEstudiante.getFin(), "La fecha de fin no debe ser nula.");
        
        
    }


    
}

