package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.imdb.model.Event.EventType;

public class Simulator {
	
// Dati in ingresso (da parametro o che non cambiano)
	private Graph<Actor, DefaultWeightedEdge> grafo;
	private int nGiorni;
	private List<Actor> vertici;
	
// Dati in uscita
	private List<IntervistaAttore> attoriIntervistati;
	private int nGiorniPausa;
	
// Modello del mondo
	private int giornoCorrente;
	private int nIntervistati;
	
// Coda degli eventi
	private PriorityQueue<Event> queue;
	
	public Simulator(Graph<Actor, DefaultWeightedEdge> grafo, List<Actor> vertici)
	{
		this.grafo = grafo;
		this.vertici = vertici;
	}
	
	public void init(int nGiorni)
	{
		this.attoriIntervistati = new ArrayList<IntervistaAttore>();
		for(Actor a : vertici)
			this.attoriIntervistati.add(new IntervistaAttore(a));
		this.queue = new PriorityQueue<Event>();
		this.nGiorni = nGiorni;
		
// Inizializzazione degli output (dati in uscita)
		this.nGiorniPausa = 0;
		
// Inizializzazione del mondo
		this.giornoCorrente = 1;
		this.nIntervistati = 0;
		
// Caricamento iniziale della coda (primo giorno)
		int casuale = (int)(Math.random()*attoriIntervistati.size());
		this.attoriIntervistati.get(casuale).setIntervistato(true);
		IntervistaAttore aTemp = attoriIntervistati.get(casuale);
		queue.add(new Event(EventType.INTERVISTA, aTemp, giornoCorrente, aTemp.getActor().getGender(), false, false));
	}
	
	public void run()
	{
		while(!this.queue.isEmpty())
		{
			Event e = this.queue.poll();
			processEvent(e);
		}
	}

	private void processEvent(Event e) 
	{
// Prendere le variabili dell'evento
		EventType type = e.getType();
		IntervistaAttore attoreInterivstato = e.getAttoreIntervistato();
		int giorno = e.getGiorno();
		String gender = e.getGenere();
		boolean NovantaPerCento = e.isNovantaPerc();
		boolean fineP = e.isFinePausa();
		
		if(giorno<this.nGiorni)
		{
			
			switch(type)
			{
			case INTERVISTA:
				this.nIntervistati++;
	// Prima intervista OPPURE torna dalla pausa
				if(this.nIntervistati==1 || e.isFinePausa())
					{
					double probabilita = (int)(Math.random());
					
			// 60% probabilità scelgo RANDOM
					if(probabilita < 0.6)
					{
						int casuale = (int)(Math.random()*attoriIntervistati.size());
						while(this.attoriIntervistati.get(casuale).isIntervistato())
							casuale = (int)(Math.random()*attoriIntervistati.size());
						
						this.attoriIntervistati.get(casuale).setIntervistato(true);
						IntervistaAttore aTemp = attoriIntervistati.get(casuale);
						queue.add(new Event(EventType.INTERVISTA, aTemp, giornoCorrente+1, aTemp.getActor().getGender(), false, false));
					}
					
			// 40% probabilità scelgo tra i vicini
					else
					{
				// NO vicini -> scelgo RANDOM
						if(getVicino(e.getAttoreIntervistato().getActor())==null)
						{
							int casuale = (int)(Math.random()*attoriIntervistati.size());
							while(this.attoriIntervistati.get(casuale).isIntervistato())
								casuale = (int)(Math.random()*attoriIntervistati.size());
							
							this.attoriIntervistati.get(casuale).setIntervistato(true);
							IntervistaAttore aTemp = attoriIntervistati.get(casuale);
							queue.add(new Event(EventType.INTERVISTA, aTemp, giornoCorrente+1, aTemp.getActor().getGender(), false, false));
						}
				// Sì vicini
						else
						{
							IntervistaAttore aTemp = new IntervistaAttore(getVicino(e.getAttoreIntervistato().getActor()));
							queue.add(new Event(EventType.INTERVISTA, aTemp, giornoCorrente+1, aTemp.getActor().getGender(), false, false));
						}
					}
				}
				
	// DALLA SECONDA INTERVISTA IN POI (controllo su gender)
	// DALLA SECONDA INTERVISTA IN POI (controllo su gender)
	// DALLA SECONDA INTERVISTA IN POI (controllo su gender)
				
				if(this.nIntervistati>1)
				{
					double probabilita = (int)(Math.random());
					
		// Controllo 90%
					if(e.isNovantaPerc() && probabilita< 0.9)
						queue.add(new Event(EventType.PAUSA, null, giornoCorrente+1, null, false, false));
					else
					{
				// 60% probabilità scelgo RANDOM
						if(probabilita < 0.6)
						{
							int casuale = (int)(Math.random()*attoriIntervistati.size());
							while(this.attoriIntervistati.get(casuale).isIntervistato())
								casuale = (int)(Math.random()*attoriIntervistati.size());
							
							this.attoriIntervistati.get(casuale).setIntervistato(true);
							IntervistaAttore aTemp = attoriIntervistati.get(casuale);
					// Controllo genere
							if(aTemp.getActor().getGender().compareTo(e.getGenere())==0)
								queue.add(new Event(EventType.INTERVISTA, aTemp, giornoCorrente+1, aTemp.getActor().getGender(), true, false));
							
							else queue.add(new Event(EventType.INTERVISTA, aTemp, giornoCorrente+1, aTemp.getActor().getGender(), false, false));
						}
						
				// 40% probabilità scelgo tra i vicini
						else
						{
					// NO vicini -> scelgo RANDOM
							if(getVicino(e.getAttoreIntervistato().getActor())==null)
							{
								int casuale = (int)(Math.random()*attoriIntervistati.size());
								while(this.attoriIntervistati.get(casuale).isIntervistato())
									casuale = (int)(Math.random()*attoriIntervistati.size());
								
								this.attoriIntervistati.get(casuale).setIntervistato(true);
								IntervistaAttore aTemp = attoriIntervistati.get(casuale);
								queue.add(new Event(EventType.INTERVISTA, aTemp, giornoCorrente+1, aTemp.getActor().getGender(), false, false));
							}
					// Sì vicini
							else
							{
								IntervistaAttore aTemp = new IntervistaAttore(getVicino(e.getAttoreIntervistato().getActor()));
								queue.add(new Event(EventType.INTERVISTA, aTemp, giornoCorrente+1, aTemp.getActor().getGender(), false, false));
							}
						}
					}
				}
				break;
				
			case PAUSA:
				
	// scelgo RANDOM
				this.nGiorniPausa++;
				double probabilita = (int)(Math.random());
				
						if(probabilita < 0.6)
						{
							int casuale = (int)(Math.random()*attoriIntervistati.size());
							while(this.attoriIntervistati.get(casuale).isIntervistato())
								casuale = (int)(Math.random()*attoriIntervistati.size());
							
							this.attoriIntervistati.get(casuale).setIntervistato(true);
							IntervistaAttore aTemp = attoriIntervistati.get(casuale);
							queue.add(new Event(EventType.INTERVISTA, aTemp, giornoCorrente+1, aTemp.getActor().getGender(), false, true));
						}
			break;	
			}
		}
	}
	
	public Actor getVicino(Actor a)
	{
		List<Actor> vicini = new ArrayList<Actor>(Graphs.neighborListOf(this.grafo, a));
		List<Actor> daRestituire = new ArrayList<Actor>();
		int max=0;
		for(int i=0; i<vicini.size(); i++)
		{		
			Actor vicino = vicini.get(i);
			for(int j=0; j<this.attoriIntervistati.size(); j++)
			{
				if(this.attoriIntervistati.get(j).isIntervistato()==false && vicini.get(i).equals(this.attoriIntervistati.get(j).getActor()))
				{
					DefaultWeightedEdge edge = this.grafo.getEdge(a, vicino);
				
					if(this.grafo.getEdgeWeight(edge)>max)
					max=(int)(this.grafo.getEdgeWeight(edge));
				}
			}
		}
		
		for(int i=0; i<vicini.size(); i++)
		{
			Actor vicino = vicini.get(i);
			DefaultWeightedEdge edge = this.grafo.getEdge(a, vicino);
			
			if(this.grafo.getEdgeWeight(edge)==max)
				daRestituire.add(vicino);
		}
		
		if(daRestituire.size()>1)
		{
			int randomico = (int)(Math.random()*daRestituire.size());
			return daRestituire.get(randomico);
		}
		else if(daRestituire.size()==1)
			return daRestituire.get(0);
		
		else return null;
	}
	
	public List<IntervistaAttore> getAttoriIntervistati()
	{
		List<IntervistaAttore> daStampare = new ArrayList<IntervistaAttore>();
		for(IntervistaAttore ia : attoriIntervistati)
		{
			if(ia.isIntervistato())
				daStampare.add(ia);					
		}
		return daStampare;
	}
	
	public int getGiorniPausa()
	{
		return this.nGiorniPausa;
	}
}
