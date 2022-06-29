package it.polito.tdp.imdb.model;

public class Event implements Comparable<Event>{
	
	public enum EventType
	{
		INTERVISTA,
		PAUSA
	}

// Variabili dell'evento
	private EventType type;
	private IntervistaAttore attoreIntervistato;
	private int giorno;
	private String genere;
	private boolean novantaPerc;
	private boolean finePausa;
	
	public Event(EventType type, IntervistaAttore attoreIntervistato, int giorno, String genere, boolean novantaPerc, boolean finePausa) {
		super();
		this.type = type;
		this.attoreIntervistato = attoreIntervistato;
		this.giorno = giorno;
		this.genere = genere;
		this.novantaPerc = novantaPerc;
		this.finePausa = finePausa;
	}
	
	public boolean isFinePausa() {
		return finePausa;
	}

	public void setFinePausa(boolean finePausa) {
		this.finePausa = finePausa;
	}

	public boolean isNovantaPerc() {
		return novantaPerc;
	}

	public void setNovantaPerc(boolean novantaPerc) {
		this.novantaPerc = novantaPerc;
	}

	public String getGenere() {
		return genere;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	public IntervistaAttore getAttoreIntervistato() {
		return attoreIntervistato;
	}
	public void setAttoreIntervistato(IntervistaAttore attoreIntervistato) {
		this.attoreIntervistato = attoreIntervistato;
	}
	public int getGiorno() {
		return giorno;
	}
	public void setGiorno(int giorno) {
		this.giorno = giorno;
	}

	@Override
	public int compareTo(Event o) {
		return this.giorno - o.getGiorno();
	}
	
}
