package it.polito.tdp.imdb.model;

public class IntervistaAttore {

	Actor actor;
	boolean intervistato;

	public IntervistaAttore(Actor actor) {
		super();
		this.actor = actor;
		this.intervistato = false;
	}
	
	public Actor getActor() {
		return actor;
	}
	public void setActor(Actor actor) {
		this.actor = actor;
	}
	public boolean isIntervistato() {
		return intervistato;
	}
	public void setIntervistato(boolean intervistato) {
		this.intervistato = intervistato;
	}

	@Override
	public String toString() {
		return "IntervistaAttore [actor=" + actor + "]";
	}
	
}
