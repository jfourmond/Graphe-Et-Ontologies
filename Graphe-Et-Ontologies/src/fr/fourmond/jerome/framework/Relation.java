package fr.fourmond.jerome.framework;

import java.util.Set;
import java.util.TreeSet;

/**
 * {@link Relation} représente une relation.
 * Elle est composée d'une nom, d'un ensemble de {@link Pair}
 * @author jfourmond
 */
public class Relation {
	private String name;
	private Set<Pair<Vertex, Vertex>> pairs;
	
	//	CONSTRUCTEURS
	public Relation(String name) {
		this.name = name;
		this.pairs = new TreeSet<>();
	}
	
	//	GETTERS
	public String getName() { return name; }
	
	public Set<Pair<Vertex, Vertex>> getPairs() { return pairs; }
	
	//	SETTERS
	public void setName(String name) { this.name = name; }
	
	public void setPairs(Set<Pair<Vertex, Vertex>> pairs) { this.pairs = pairs; }
	
	//	METHODES
	/**
	 * Ajoute une paire
	 * @param pair : paire à ajouter
	 * @throws RelationException si la pair existe déjà dans la relation
	 */
	public void addPair(Pair<Vertex, Vertex> pair) throws RelationException {
		boolean b = pairs.add(pair);
		if(!b) throw new RelationException("La pair existe déjà.");
	}
	
	@Override
	public String toString() {
		String ch = name + " : \n";
		for(Pair<Vertex, Vertex> pair : pairs) {
			ch += pair.getFirst() + " -> " + pair.getSecond();
		}
		return ch;
	}
	
	@Override
	public boolean equals(Object obj) {
		Relation relation = (Relation) obj;
		return this.name.equals(relation.name);
	}
}
