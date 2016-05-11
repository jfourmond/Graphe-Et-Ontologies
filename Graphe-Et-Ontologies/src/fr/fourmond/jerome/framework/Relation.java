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
	 * Retourne la première occurence de la paire composée du sommet
	 * @param vertex : sommet à recherche
	 * @return la première occurence de la paire composée du sommet ou <code>null</code>
	 */
	public Pair<Vertex, Vertex> getFirstPair(Vertex vertex) {
		for(Pair<Vertex, Vertex> pair : pairs) {
			if(pair.getFirst() == vertex || pair.getSecond() == vertex)
				return pair;
		}
		return null;
	}
	
	/**
	 * Retourne une liste de {@link Pair} où le sommet passé en paramètre
	 * est l'origine de l'arc
	 * @param vertex : sommet à rechercher
	 * @return une liste de {@link Pair} dont le sommet passé en paramètre est l'origine de l'arc
	 */
	public List<Pair<Vertex, Vertex>> getPairs(Vertex vertex) {
		List<Pair<Vertex, Vertex>> list = new ArrayList<>();
		for(Pair<Vertex, Vertex> pair : pairs) {
			if(pair.getFirst().equals(vertex))
				list.add(pair);
		}
		return list;
	}
	
	/**
	 * Teste si la paire est présente dans la relation
	 * @param pair : paire à rechercher
	 * @return <code>true</code> si la paire est présente dans la relation, <code>false</code> sinon
	 */
	public boolean contains(Pair<Vertex, Vertex> pair) { return pairs.contains(pair); }
	
	/**
	 * Teste s'il existe une paire contenant ce sommet
	 * @param vertex : sommet à rechercher
	 * @return <code>true</code> s'il existe une paire contenant ce sommet, <code>false</code> sinon
	 */
	public boolean containsVertex(Vertex vertex) {
		for(Pair<Vertex, Vertex> pair : pairs) {
			if(pair.getFirst().equals(vertex) || pair.getSecond().equals(vertex)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Supprime la paire correspondante
	 * @param pair : la paire à rechercher et à supprimer
	 * @return <code>true</code> si la relation contenait la paire
	 */
	public boolean remove(Pair<Vertex, Vertex> pair) { return pairs.remove(pair); }
	
	/**
	 * Supprime les paires ayant ce sommet
	 * @param vertex : sommet à rechercher
	 */
	public void removeRelatedPair(Vertex vertex) {
		while(containsVertex(vertex)) {
			Pair<Vertex, Vertex> pair = getFirstPair(vertex);
			pairs.remove(pair);
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
		if(obj == null || getClass() == obj.getClass()) {
			return false;
		} else {
			Relation relation = (Relation) obj;
			return this.name.equals(relation.name);
		}
	}
}
