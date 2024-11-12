package Actividad;

import java.util.ArrayList;

public class PreguntaCerrada {

	private String descripcion;
	private Boolean seleccionada;
	private ArrayList<Opcion> opciones;
	public PreguntaCerrada(String descripcion) {
		super();
		this.descripcion = descripcion;
		this.opciones = new ArrayList<Opcion>();
		this.setSeleccionada(false);
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public ArrayList<Opcion> getOpciones() {
		return opciones;
	}	
	public void agregarOpcion(Opcion opcion) throws Exception {
		if (opciones.size()>=4) {
			throw new Exception("Un quiz solo puede tener 4 opciones.");
		}
		else if (opcion.getCorrecto() && contarCorrectas() > 0) {
			throw new Exception("Ya hay una opción correcta.");
		} else {
			opciones.add(opcion);
		}
	}
	public int contarCorrectas() {
		//Excepcion
		int contador = 0;
		for (Opcion o:opciones) {
			if (o.getCorrecto()) {
				contador++;
			}
		}
		return contador;
	}
	public Boolean verificarRespuesta() {
		for (Opcion o: opciones) {
			if (o.getCorrecto() && o.getSeleccion()) {
				return true;
			}
		}
		return false;
	}
	public String explicaciones() {
		String completa = "";
		for (Opcion o: opciones) {
			String op = "Opción: "+o.getDescripcion();
			String exp = " -> "+o.getExplicacion();
			completa = completa + op + exp + "\n";
		}
		return completa;
	}
	public Boolean getSeleccionada() {
		return seleccionada;
	}
	public void setSeleccionada(Boolean seleccionada) {
		this.seleccionada = seleccionada;
	}
}
