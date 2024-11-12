package Actividad;


public class Feedback {

	private String resena;
	private int rating;
	
	public Feedback(String resena) {
		super();
		this.resena = resena;
		this.rating = -1;
	}
	public Feedback(String resena, int rating) throws Exception {
		super();
		this.resena = resena;
		setRating(rating);
	}
	public String getResena() {
		return resena;
	}
	public void setResena(String resena) {
		this.resena = resena;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) throws Exception {
		if (rating < 0 || rating > 10) {
			throw new Exception("El rating debe estar entre 0 y 10.");
		} else {
		this.rating = rating;
		}
	}
	
}
