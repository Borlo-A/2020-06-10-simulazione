package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	private ImdbDAO dao;
	private Map<Integer, Actor> idMap;
	private Graph<Actor, DefaultWeightedEdge> grafo;
	private List<Actor> vertici;
//	private List<Ruolo> ruoli;
	private List<Arco> archi;
//	private List<Integer> id;
	
	Simulator sim;
	private int giorniPausa;
	private List<IntervistaAttore> attoriIntervistati;
	
	public Model()
	{
		dao = new ImdbDAO();
		idMap = new HashMap<Integer, Actor>();
		this.dao.listAllActors(idMap);
//		ruoli = new ArrayList<Ruolo>(this.dao.getRoles());
		
	}
	
	public void creaGrafo(String genre)
	{
		archi = new ArrayList<Arco>();
		vertici = new ArrayList<Actor>();
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
// Aggiungere i vertici
//		id = new ArrayList<Integer>(this.dao.getActorsByGenre(genre));
//		for(int i=0; i<id.size(); i++)
//		{
//			Actor a = idMap.get(id.get(i));
//			vertici.add(a);
//		}
		vertici = new ArrayList<Actor>(this.dao.getActorsByGenre(genre, idMap));
		Graphs.addAllVertices(this.grafo, vertici);
		
// Aggiungere gli archi
		archi = new ArrayList<Arco>(this.dao.getArchi(genre, idMap));
	
// FUNZIONE RIMUOVI DOPPIONI
		for(int i=0; i<archi.size(); i++)
		{
			for(int j=0; j<archi.size(); j++)
			{
				
					if(archi.get(i).getA1().equals(archi.get(j).getA2()) && archi.get(i).getA2().equals(archi.get(j).getA1()))
					{
// Rimuovo archi doppioni
						archi.remove(archi.get(j));
					}
			}
		}
		
		for (Arco a : archi)
		{
			if(this.grafo.containsVertex(a.getA1()) && this.grafo.containsVertex((a.getA2())))
			{
				DefaultWeightedEdge edge = this.grafo.getEdge(a.getA1(), a.getA2());
				if(edge==null)
				{
					Graphs.addEdgeWithVertices(this.grafo, a.getA1(), a.getA2(), 1);
				}
				else 
				{
					double peso = this.grafo.getEdgeWeight(edge)+1 ;
					this.grafo.setEdgeWeight(edge, peso);
				}
			}
		}	
	}
	
	public List<Actor> getAttoriConnessi(Actor actor) 
	{
		ConnectivityInspector<Actor, DefaultWeightedEdge> ci = new ConnectivityInspector<Actor, DefaultWeightedEdge>(this.grafo);
		List<Actor> attoriConnessi = new ArrayList<Actor>(ci.connectedSetOf(actor));
		Collections.sort(attoriConnessi);
		return attoriConnessi;
	}
	
	public void simula(int nGiorni)
	{
		sim = new Simulator(this.grafo, vertici);
		sim.init(nGiorni);
		sim.run();
		this.giorniPausa = sim.getGiorniPausa();
		this.attoriIntervistati = new ArrayList<IntervistaAttore>(sim.getAttoriIntervistati());
	}
	
	public int getGiorniPausa()
	{
		return this.giorniPausa;
	}
	
	public List<IntervistaAttore> getAttoriIntervistati()
	{
		return this.attoriIntervistati;
	}
	
	public List<String> getGenres()
	{
		return this.dao.getGenres();
	}
	
	public List<Actor> getVertici()
	{
		Collections.sort(vertici);
		return this.vertici;
	}
	
	public int getVerticiSize()
	{
		return this.vertici.size();
	}

	public int getArchiSize()
	{
		return this.grafo.edgeSet().size();
	}
}
