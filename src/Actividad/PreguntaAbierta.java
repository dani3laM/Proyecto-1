package Actividad;

public class PreguntaAbierta {

	private String descripcion;
	private String respuesta;
	public PreguntaAbierta(String descripcion) {
		super();
		this.descripcion = descripcion;
		this.respuesta = "";
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	
}
