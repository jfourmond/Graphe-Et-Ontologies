package fr.fourmond.jerome.framework;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link Relation} représente une relation.
 * Elle est composée d'une nom, d'un ensemble de {@link Pair}
 * @author jfourmond
 */
public class Relation {
	private String name;
	private List<Pair<Vertex, Vertex>> pairs;
	
	//	CONSTRUCTEURS
	public Relation(String name) throws RelationException {
		if(name == null || name.isEmpty())
			throw new RelationException("Aucun nom spécifié.");
		this.name = name;
		this.pairs = new ArrayList<>();
	}
	
	//	GETTERS
	public String getName() { return name; }
	
	public List<Pair<Vertex, Vertex>> getPairs() { return pairs; }
	
	//	SETTERS
	public void setName(String name) throws RelationException {
		if(name == null || name.isEmpty())
			throw new RelationException("Aucun nom spécifié.");
		else this.name = name;
	}
	
	public void setPairs(List<Pair<Vertex, Vertex>> pairs) { this.pairs = pairs; }
	
	//	METHODES
	/**
	 * Ajoute une paire
	 * @param pair : paire à ajouter
	 * @throws RelationException si la pair, dans cet ordre précis, existe déjà dans la relation
	 */
	public void add(Pair<Vertex, Vertex> pair) throws RelationException {
		boolean b = pairs.add(pair);
		if(!b) throw new RelationException("La pair existe déjà.");
	}
	
	/**
	 * Supprime les paires ayant ce sommet =
	 * @param vertex : sommet à rechercher
	 */
	public void removeRelatedPair(Vertex vertex) {
		for(Pair<Vertex, Vertex> pair : pairs) {
			if(pair.getFirst().equals(vertex) || pair.getSecond().equals(vertex)) {
				pairs.remove(pair);
			}
		}
	}
	
	@Override
	public String toString() {
		String ch = name + " : \n";
		for(Pair<Vertex, Vertex> pair : pairs) {
			ch += "\t\t" + pair.getFirst().getID() + " -> " + pair.getSecond().getID() + "\n";
		}
		return ch;
	}
	
	@Override
	public boolean equals(Object obj) {
		Relation relation = (Relation) obj;
		return this.name.equals(relation.name);
	}
}
