package it.polito.tdp.yelp.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.yelp.db.YelpDao;

public class Model {

	YelpDao dao;
	private SimpleDirectedWeightedGraph<Review,DefaultWeightedEdge> grafo;
	private List<Review> recensioniCrescenti;
	
	private List<Review> percorsoMigliore;
	
	public Model() {
		this.dao = new YelpDao();
	}
	
	
	public void creaGrafo(Business locale) {
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		//aggiungo vertici
		Graphs.addAllVertices(this.grafo, dao.getReviewsByBusiness(locale));
		//System.out.println("Numero di vertici aggiunti :"+this.grafo.vertexSet().size());
		//System.out.println(this.grafo.vertexSet());
		this.recensioniCrescenti = new LinkedList<>(this.dao.getReviewsByBusiness(locale));
		for (int i=recensioniCrescenti.size()-1; i>=0; i--) {
			LocalDate data2 = recensioniCrescenti.get(i).getDate();
			for (int j = 0; j<i; j++) {
				LocalDate data1 = recensioniCrescenti.get(j).getDate();
				int peso = (int) ChronoUnit.DAYS.between(data1, data2);
				Graphs.addEdge(this.grafo, recensioniCrescenti.get(j), recensioniCrescenti.get(i), peso);
				Adiacenze prova = new Adiacenze (recensioniCrescenti.get(j), recensioniCrescenti.get(i), peso);
				
				
			}
			
		}
		
		System.out.println("# vertici : "+this.grafo.vertexSet().size());
		System.out.println("# archi : "+this.grafo.edgeSet().size());
		
	}
	
	public  String getRecensionePesante() {
		List<Review> result = new LinkedList<>();
		int piuArchi =0;
		for (Review recensione : this.grafo.vertexSet()) {
			if (piuArchi<=this.grafo.outDegreeOf(recensione)) {
				piuArchi = this.grafo.outDegreeOf(recensione);
				result.add(recensione);
			}	
		}
		for (Review recensione : result) {
			if(this.grafo.outDegreeOf(recensione)!= piuArchi) {
				result.remove(recensione);
			}
		}
		System.out.println(result+"		#ARCHI: "+piuArchi);
		return result+"		#ARCHI: "+piuArchi;
		
	}

	
	public List<String> getCities() {
		// TODO Auto-generated method stub
		return this.dao.getAllCities();
	}

	public List<Business> getBusinessPerCity(String city) {
		// TODO Auto-generated method stub
		return this.dao.getBusinessPerCity(city);
	}


	public String getVertici() {
		// TODO Auto-generated method stub
		return ""+this.grafo.vertexSet().size();
	}


	public String getArchi() {
		// TODO Auto-generated method stub
		return ""+this.grafo.vertexSet().size();
	}
	
	public List<Review> trovaPercorso() {
		
		this.percorsoMigliore = new ArrayList<>();
		List<Review> parziale = new ArrayList<>();
		int menoStars = 5;
		Review recensioneMinima = null;
		for (Review recensione : recensioniCrescenti) {
			if (menoStars>recensione.getStars()) {
				recensioneMinima = recensione;
			}
		}
		System.out.println(recensioneMinima);
		parziale.add(recensioneMinima);
		
		cerca(parziale);
		
		
		
		
		return this.percorsoMigliore;
	}
	
	
	private void cerca(List<Review> parziale) {
		//caso in cui non ci sono più nodi
		if(parziale.size()==recensioniCrescenti.size()) {
			this.percorsoMigliore=new LinkedList<>(parziale);
			return;
		}
		for (Review vicino : Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1))) {
			//Se hanno le stesse stelle
			if (//!parziale.contains(vicino) &&
				vicino.getStars()==parziale.get(parziale.size()-1).getStars()) {
				parziale.add(vicino);				
				cerca(parziale);		
				parziale.remove(parziale.size()-1);
			}
			//Se ha almeno una stella in piu
			if (!parziale.contains(vicino) &&
					vicino.getStars()>parziale.get(parziale.size()-1).getStars()) {
				parziale.add(vicino);				
				cerca(parziale);		
				parziale.remove(parziale.size()-1);
		}
	}
}
}
