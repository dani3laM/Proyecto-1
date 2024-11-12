package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map.Entry;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import Actividad.Actividad;
import Actividad.Examen;
import Actividad.Feedback;
import Logica.LearningPath;
import Logica.Progreso;
import Logica.Sistema;
import Usuario.Estudiante;
import Usuario.Profesor;

public class PruebasEstudiantes {
	private Estudiante estudiante;
    private Profesor profesor;
    private LearningPath learningpath;
    private Examen derivadas;
    private Examen integrales;
    private Examen antiderivadas;
    Sistema sistema = new Sistema();

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
        sistema.crearExamen("Antiderivadas",
            true, 
            "Estudio de las antiderivadas y su relación con la integral", 
            "Aplicar las antiderivadas en problemas matemáticos", 
            "Avanzado", 
            15, 
            "ProfesorLinares"
           );
        sistema.crearExamen(
                "Derivadas",
                true,
                "Examen de derivadas",
                "Aplicar derivadas en problemas de cálculo",
                "Avanzado",
                60,
                "ProfesorLinares"
            );
        Actividad derivadas = sistema.getActividades().get("Derivadas");
        Actividad antiderivadas = sistema.getActividades().get("Antiderivadas");
        Actividad integrales = sistema.getActividades().get("Integrales");
        LearningPath learningpath = sistema.getLearningPaths().get(titulo);
        learningpath.agregarActividad(integrales);
        learningpath.agregarActividad(derivadas);
        learningpath.agregarActividad(antiderivadas);
        
        
        
    }
    
	@Test
    public void testAgregarFeedbackEstudiante() throws Exception {
        
        String contenidoFeedbackEstudiante = "La actividad es muy desafiante y educativa.";
        Feedback feedbackEstudiante = new Feedback(contenidoFeedbackEstudiante, 4);

        // Se agrega el feedback
        antiderivadas.agregarFeedback(feedbackEstudiante);

        //Verificamos que el feedback fue agregado correctamente
        assertTrue(antiderivadas.getFeedbacks().contains(feedbackEstudiante), "El feedback del estudiante no fue agregado correctamente");
        assertEquals(contenidoFeedbackEstudiante, antiderivadas.getFeedbacks().get(0).getResena(), "El contenido del feedback del estudiante no es correcto");
    }
	
	@Test
	public void probarProgreso() {
        Estudiante estudiante = (Estudiante) sistema.getUsuarios().get("j.perez");
        LearningPath learningPath = sistema.getLearningPaths().get("Matemáticas Avanzadas");

        if (estudiante == null) {
            System.out.println("Estudiante no encontrado.");
            return;
        }
        if (learningPath == null) {
            System.out.println("Learning Path no encontrado.");
            return;
        }

        Progreso progreso = new Progreso(LocalDateTime.of(2024, Month.NOVEMBER, 20, 23, 59, 59, 0),learningPath);
 
        int actividadesCompletadas = 0;
        int totalActividades = learningPath.getActividades().size();
        int exitos = 0; 
        int fracasos = 0; 

        for (Entry<String, Actividad> entry : learningPath.getActividades().entrySet()) {
            actividadesCompletadas++;
            exitos++; 
        }

        double porcentajeProgreso = (double) actividadesCompletadas / totalActividades * 100;
        progreso.setPorcentajeProgreso(porcentajeProgreso);

        double tasaExito = totalActividades > 0 ? (double) exitos / totalActividades * 100 : 0;
        double tasaFracaso = totalActividades > 0 ? (double) fracasos / totalActividades * 100 : 0;

        progreso.setTasaExito(tasaExito);
        progreso.setTasaFracaso(tasaFracaso);

        System.out.println("Progreso de " + estudiante.getLogin() + " en " + learningPath.getTitulo() + ":");
        System.out.println("Porcentaje de progreso: " + progreso.getPorcentajeProgreso() + "%");
        System.out.println("Tasa de éxito: " + progreso.getTasaExito() + "%");
        System.out.println("Tasa de fracaso: " + progreso.getTasaFracaso() + "%");
    }
	
}
