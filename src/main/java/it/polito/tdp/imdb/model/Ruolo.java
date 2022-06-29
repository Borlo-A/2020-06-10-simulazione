package it.polito.tdp.imdb.model;

public class Ruolo {
	
	private int actor_id;
	private int movie_id;
	private String role;
	
	public Ruolo(int actor_id, int movie_id, String role) {
		super();
		this.actor_id = actor_id;
		this.movie_id = movie_id;
		this.role = role;
	}
	public int getActor_id() {
		return actor_id;
	}
	public void setActor_id(int actor_id) {
		this.actor_id = actor_id;
	}
	public int getMovie_id() {
		return movie_id;
	}
	public void setMovie_id(int movie_id) {
		this.movie_id = movie_id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

}
