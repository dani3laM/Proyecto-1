package Logica;

import java.time.LocalDateTime;
import java.util.ArrayList;
import Actividad.Actividad;

public class Progreso {

	private double porcentajeProgreso;
	private LocalDateTime inicio;
	private LocalDateTime fin;
	private double tasaExito;
	private double tasaFracaso;
	private LearningPath learningPath;
	public Progreso(LocalDateTime inicio, LearningPath learningPath) {
		super();
		this.inicio = inicio;
		this.learningPath = learningPath;
		this.tasaExito = 0;
		this.tasaFracaso = 0;
		this.porcentajeProgreso = 0;
	}
	public double getPorcentajeProgreso() {
		return porcentajeProgreso;
	}
	public void setPorcentajeProgreso(double porcentajeProgreso) {
		this.porcentajeProgreso = porcentajeProgreso;
	}
	public LocalDateTime getInicio() {
		return inicio;
	}
	public LocalDateTime getFin() {
		return fin;
	}
	public void setFin(LocalDateTime fin) {
		this.fin = fin;
	}
	public double getTasaExito() {
		return tasaExito;
	}
	public void setTasaExito(double tasaExito) {
		this.tasaExito = tasaExito;
	}
	public double getTasaFracaso() {
		return tasaFracaso;
	}
	public void setTasaFracaso(double tasaFracaso) {
		this.tasaFracaso = tasaFracaso;
	}
	public LearningPath getLearningPath() {
		return learningPath;
	}
	public void calcularPorcentaje() {
		int n = 0;
		int c = 0;
		for (Actividad a : learningPath.getActividades().values()) {
			if (a.getObligatoriedad()) {
				n++;
				c++;
				}
			}
		if (n!=0) {
			this.porcentajeProgreso = (double) c/n;
		} else {
			this.porcentajeProgreso = 0;
		}
	}
	public void calcularExito() {
		//ArrayList<Actividad> actividades = (ArrayList<Actividad>) learningPath.getActividades().values();
		int e = 0;
		int n = 0;
		for (Actividad a : learningPath.getActividades().values()) {
			if (a.getDuracion()!=0){
				if (!a.getResultado().equals("Incompleto")&&!a.getResultado().equals("No exitoso")) {
					e++;
				}
				n++;
			}
		}
		if (n!=0) {
			this.tasaExito = e/n;
			System.out.println("si");
		} else {
			this.tasaExito = 0;
		}
	}
	public void calcularFracaso() {
		
		ArrayList<Actividad> actividades = (ArrayList<Actividad>) learningPath.getActividades().values();
		int f = 0;
		int n = 0;
		for (Actividad a:actividades) {
			if (a.getDuracion()!=0){
				if (a.getResultado().equals("Incompleto")||a.getResultado().equals("No exitoso")) {
					f++;
				}
				n++;
			}
		}
		if (n!=0) {
			this.tasaExito = f/n;
		} else {
			this.tasaExito = 0;
		}
	}	
	
}
