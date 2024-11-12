package tests;

import Autenticacion.Autenticador;
import Usuario.Estudiante;
import Usuario.Usuario;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;



public class PruebaRegistro {
    
    private HashMap<String, Usuario> registros;
    private Autenticador autenticador;
    private Estudiante estudiante1;
    
    @BeforeEach
    public void setUp() throws Exception {
    	estudiante1 = new Estudiante("j.perez", "12345","software,diseno,matematicas");
    	registros = new HashMap<String, Usuario>();
    	autenticador = new Autenticador(registros);
    	
    }
    @Test
    public void registrarUnUsuario() throws Exception {
        // Registrar un estudiante
        autenticador.registrarUsuario("j.perez", "12345", "Estudiante", "software,diseno,matematicas");
        assertTrue(autenticador.verificarRegistro("j.perez"), "El estudiante no se registró");
        
        // Registrar un profesor
        autenticador.registrarUsuario("ProfesorLinares", "54321", "PROFESOR", "");
        assertTrue(autenticador.verificarRegistro("ProfesorLinares"), "El profesor no se registró");
    }
    @Test
    public void verificarNoExistencia() throws Exception {
    	
        assertFalse(autenticador.verificarRegistro("a.mendez"), "Existe un usuario no registrado");
    }
    @Test
    public void verificarIngresos() throws Exception {
        assertTrue(autenticador.verificarIngreso("j.perez", "12345"), "Falla al ingresar con una contraseña correcta");
        assertFalse(autenticador.verificarIngreso("j.perez", "54321"), "Se permite el ingreso con una contraseña incorrecta");
    }
    @Test
    public void verificarTipo() {
        
        assertEquals("Estudiante", estudiante1.getRol(), "El tipo de usuario no es el esperado");
    }
}







