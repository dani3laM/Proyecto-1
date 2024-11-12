package Actividad;

public class Opcion {

	private Boolean correcto;
	private String explicacion;
	private Boolean seleccion;
	private String descripcion;
	public Opcion(Boolean correcto, String explicacion, String descripcion) {
		super();
		this.correcto = correcto;
		this.explicacion = explicacion;
		this.setSeleccion(false);
		this.descripcion = descripcion;
	}
	public Boolean getCorrecto() {
		return correcto;
	}
	public void setCorrecto(Boolean correcto) {
		this.correcto = correcto;
	}
	public String getExplicacion() {
		return explicacion;
	}
	public void setExplicacion(String explicacion) {
		this.explicacion = explicacion;
	}
	public void seleccionar() {
		this.setSeleccion(true);
	}
	public Boolean getSeleccion() {
		return seleccion;
	}
	public void setSeleccion(Boolean seleccion) {
		this.seleccion = seleccion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
}
