package Persistencia;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import Logica.LearningPath;
import Actividad.Actividad;
import Actividad.Feedback;
import Logica.Progreso;
import Usuario.Estudiante;
import Usuario.Profesor;
import Usuario.Usuario;
import Logica.Sistema;

public class ManejadorDePersistencia {

    private Sistema sistema;

    public ManejadorDePersistencia(Sistema sistema) {
        this.sistema = sistema;
    }

    public void guardarUsuarios() throws IOException {
        PrintWriter escritor = new PrintWriter(new File("./data/usuarios.txt"));
        HashMap<String, Usuario> usuarios = sistema.getUsuarios();

        for (Usuario u : usuarios.values()) {
            escritor.print(u.getLogin() + ";" + u.getContrasena() + ";" + u.getRol());

            if (u instanceof Estudiante) {
                Estudiante estudiante = (Estudiante) u;
                escritor.print(";" + estudiante.getIntereses() + ";" + estudiante.getActividadEnProgreso());

                HashMap<String, Progreso> progresos = estudiante.getProgresos();
                escritor.print(";" + progresos.size());
                for (String titulo : progresos.keySet()) {
                    escritor.print(";" + titulo);
                }
            } else if (u instanceof Profesor) {
                Profesor profesor = (Profesor) u;
                HashMap<String, LearningPath> creaciones = profesor.getCreaciones();
                escritor.print(";" + creaciones.size());

                for (String titulo : creaciones.keySet()) {
                    escritor.print(";" + titulo);
                }
            }
            escritor.println();
        }
        escritor.close();
    }

    public void leerUsuarios() throws IOException {
        HashMap<String, Usuario> usuarios = new HashMap<>();
        File archivo = new File("./data/usuarios.txt");
        BufferedReader lector = new BufferedReader(new FileReader(archivo));
        String linea = lector.readLine();

        while (linea != null) {
            String[] datos = linea.split(";");
            String login = datos[0];
            String contrasena = datos[1];
            String rol = datos[2];

            Usuario nuevoUsuario;
            if (rol.equalsIgnoreCase("Estudiante")) {
                String intereses = datos[3];
                Boolean actividadEnProgreso = Boolean.parseBoolean(datos[4]);
                int numProgresos = Integer.parseInt(datos[5]);

                Estudiante estudiante = new Estudiante(login, contrasena, intereses);
                
                estudiante.setActividadEnProgreso(actividadEnProgreso);

                HashMap<String, Progreso> progresos = new HashMap<String, Progreso>();
                for (int i = 0; i < numProgresos; i++) {
                    String titulo = datos[6 + i];
                    Progreso progreso = new Progreso(LocalDateTime.now(),sistema.getLearningPaths().get(titulo));
                    if (progreso != null) {
                        progresos.put(titulo, progreso);
                    }
                }
                estudiante.setProgresos(progresos);
                nuevoUsuario = estudiante;
            } else if (rol.equalsIgnoreCase("Profesor")) {
                Profesor profesor = new Profesor(login, contrasena);
                HashMap<String, LearningPath> creaciones = new HashMap<>();
                int numCreaciones = Integer.parseInt(datos[3]);

                for (int i = 0; i < numCreaciones; i++) {
                    String titulo = datos[4 + i];
                    LearningPath lp = sistema.getLearningPaths().get(titulo);
                    if (lp != null) {
                        creaciones.put(titulo, lp);
                    }
                }
                profesor.setCreaciones(creaciones);
                nuevoUsuario = profesor;
            } else {
                nuevoUsuario = new Usuario(login, contrasena) {
                    @Override
                    public String getRol() {
                        return rol;
                    }
                };
            }

            usuarios.put(login, nuevoUsuario);
            linea = lector.readLine();
        }
        lector.close();
        sistema.setUsuarios(usuarios);
    }

    public void guardarLearningPaths() throws IOException {
        PrintWriter escritor = new PrintWriter(new File("./data/learningPaths.txt"));
        HashMap<String, LearningPath> learningPaths = sistema.getLearningPaths();

        for (LearningPath path : learningPaths.values()) {
            escritor.print(path.getTitulo() + ";" + path.getDescripcion() + ";" + path.getObjetivos() + ";" + path.getNivelDificultad());

            HashMap<String, Actividad> actividades = path.getActividades();
            escritor.print(";" + actividades.size());
            for (String titulo : actividades.keySet()) {
                escritor.print(";" + titulo);
            }

            ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();
            for (Actividad a : path.getActividades().values()) {
            	 ArrayList<Feedback> feedbacksDea = a.getFeedbacks();
            	 for (Feedback b : feedbacksDea) {
            		 feedbacks.add(b);
            	 }
            	 
            }
            escritor.print(";" + feedbacks.size());
            for (Feedback fb : feedbacks) {
                escritor.print(";" + fb.getResena() + ";" + fb.getRating());
            }

            //Progreso progreso = path.getProgreso();
            //escritor.println(";" + progreso.getPorcentajeProgreso() + ";" + progreso.getTasaExito() + ";" + progreso.getTasaFracaso());
        }
        escritor.close();
    }

    public void leerLearningPaths() throws Exception {
        HashMap<String, LearningPath> learningPaths = new HashMap<>();
        File archivo = new File("./data/learningPaths.txt");
        BufferedReader lector = new BufferedReader(new FileReader(archivo));
        String linea = lector.readLine();
        LearningPath path = null;

        while (linea != null) {
            String[] datos = linea.split(";");
            String titulo = datos[0];
            String descripcion = datos[1];
            String objetivos = datos[2];
            String nivelDificultad = datos[3];

            
			try {
				path = new LearningPath(titulo, descripcion, objetivos, nivelDificultad, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            int numActividades = Integer.parseInt(datos[4]);
            HashMap<String, Actividad> actividades = new HashMap<>();
            for (int i = 0; i < numActividades; i++) {
                String tituloActividad = datos[5 + i];
                Actividad actividad = sistema.getActividades().get(tituloActividad);
                if (actividad != null) {
                    actividades.put(tituloActividad, actividad);
                }
            }
            path.setActividades(actividades);

            int indexAfterActividades = 5 + numActividades;
            int numFeedbacks = Integer.parseInt(datos[indexAfterActividades]);
            ArrayList<Feedback> feedbacks = new ArrayList<>();
            for (int i = 0; i < numFeedbacks; i++) {
                String resena = datos[indexAfterActividades + 1 + (2 * i)];
                int rating = Integer.parseInt(datos[indexAfterActividades + 2 + (2 * i)]);
                Feedback fb = null;
				try {
					fb = new Feedback(resena, rating);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                feedbacks.add(fb);
            }
            //falta informacion para asignar el feedback a una actividad
            //path.setFeedbacks(feedbacks);
            int progressIndex = indexAfterActividades + 1 + (2 * numFeedbacks);
            double porcentajeProgreso = Double.parseDouble(datos[progressIndex]);
            double tasaExito = Double.parseDouble(datos[progressIndex + 1]);
            double tasaFracaso = Double.parseDouble(datos[progressIndex + 2]);
            Progreso progreso = new Progreso(null, path);
            progreso.setPorcentajeProgreso(porcentajeProgreso);
            progreso.setTasaExito(tasaExito);
            progreso.setTasaFracaso(tasaFracaso);
            

            learningPaths.put(titulo, path);
            linea = lector.readLine();
        }
        lector.close();
        sistema.setLearningPaths(learningPaths);
    }
}
