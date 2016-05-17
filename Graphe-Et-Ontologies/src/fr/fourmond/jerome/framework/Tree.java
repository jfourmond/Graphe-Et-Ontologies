package fr.fourmond.jerome.framework;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * {@link Tree} représente un arbre/graphe (non orienté).
 * Il est composé d'une {@link List} de {@link Vertex}, 
 * et d'une {@link List} de {@link Relation}.
 * @author jfourmond
 */
public class Tree {
	private File file;
	
	private List<Vertex> vertices;
	private List<Relation> relations;
	
	//	CONSTRUCTEURS
	public Tree() {
		file = null;
		
		vertices = new ArrayList<>();
		relations = new ArrayList<>();
	}
	
	//	GETTERS
	public File getFile() { return file; }
	
	public List<Vertex> getVertices() { return vertices; }
	
	public List<Relation> getRelations() { return relations; }
	
	
	//	SETTERS
	public void setFile(File file) { this.file = file; }
	
	public void setVertices(List<Vertex> vertices) { this.vertices = vertices; }
	
	public void setRelations(List<Relation> relations) { this.relations = relations; }
	
	
	//	METHODES
	/**
	 * Crée un sommet (sans attribut)
	 * @param vertexID : identifiant unique du sommet
	 * @throws TreeException si le sommet existe déjà
	 * @throws VertexException si l'identifiant est <code>null</code> ou vide
	 * @return le sommet crée
	 */
	public Vertex createVertex(String vertexID) throws TreeException, VertexException {
		if(getVertex(vertexID) == null) {
			Vertex vertex = new Vertex(vertexID);
			vertices.add(vertex);
			return vertex;
		} else throw new TreeException("Le sommet existe déjà.");
	}
	
	/**
	 * Crée un sommet
	 * @param vertex : le sommet à ajouter
	 * @throws TreeException si le sommet existe déjà
	 * @return le sommet crée
	 */
	public Vertex createVertex(Vertex vertex) throws TreeException {
		if(getVertex(vertex.getID()) == null) {
			vertices.add(vertex);
			return vertex;
		} else throw new TreeException("Le sommet existe déjà.");
	}
	
	/**
	 * Retourne les attributs du sommet
	 * @param vertexID : identifiant unique du sommet
	 * @return les attributs du sommet
	 */
	public Vertex getVertex(String vertexID) {
		for(Vertex vertex : vertices) {
			if(vertex.getID().equals(vertexID))
				return vertex;
		}
		return null;
	}
	
	/**
	 * Retourne l'identifiant unique du sommet ayant pour valeur d'attribut
	 * @param value : la valeur d'attribut à rechercher
	 * @return l'identifiant unique du sommet, ou null
	 */
	public String getVertexID(String value) {
		for(Vertex vertex : vertices) {
			if(vertex.isValueOfAttribute(value))
				return vertex.getID();
		}
		return null;
	}
	
	/**
	 * Supprime le sommet 
	 * @param vertex : sommet à supprimer
	 * @throws TreeException si le sommet n'existe pas
	 */
	public void removeVertex(Vertex vertex) throws TreeException {
		if(!vertices.remove(vertex)) throw new TreeException("Le sommet n'existe pas.");
		else removeRelatedPair(vertex);
	}
	
	/**
	 * Retourne le nombre de sommet
	 * @return le nombre de sommet
	 */
	public int nbVertices() { return vertices.size(); }
	
	/**
	 * Teste si l'identifiant est l'identifiant d'un {@link Vertex} de l'arbre
	 * @param id : identifiant
	 * @return <code>true</code> si l'identifiant correspond à un {@link Vertex}, <code>false</code> sinon
	 */
	public boolean isID(String id) {
		for(Vertex vertex : vertices) {
			if(vertex.getID().equals(id))
				return true;
		}
		return false;
	}
	
	/**
	 * Teste s'il n'existe pas des sommets dans l'arbre
	 * @return : <code>true</code> si il n'y a pas de sommet, <code>false</code> sinon
	 */
	public boolean isVerticesEmpty() {
		return vertices.isEmpty();
	}
	
	/**
	 * Crée un attribut au sommet
	 * @param vertexID : identifiant unique du sommet
	 * @param attributeID : identifiant unique de l'attribut
	 * @throws TreeException si le sommet n'existe pas
	 * @throws VertexException si l'attribut existe déjà pour ce sommet
	 */
	public void createAttribute(String vertexID, String attributeID) throws TreeException, VertexException {
		Vertex vertex = getVertex(vertexID);
		if(vertex != null)
			vertex.add(attributeID);
		else throw new TreeException("Le sommet n'existe pas.");
	}
	
	/**
	 * Remplace la valeur de l'attribut associé au sommet
	 * @param vertexID : identifiant unique du sommet
	 * @param attributeID : identifiant unique de l'attribut
	 * @param value : valeur de l'attribut
	 * @throws TreeException si le sommet
	 * @throws VertexException si l'attribut n'existe pas
	 */
	public void setAttribute(String vertexID, String attributeID, String value) throws TreeException, VertexException {
		Vertex vertex = getVertex(vertexID);
		if(vertex != null)
			vertex.set(attributeID, value);
		else throw new TreeException("Le sommet n'existe pas.");
	}
	
	/**
	 * 
	 * @param value : valeur de l'attribut
	 * @throws VertexException 
	 */
	/**
	 * Association d'un attribut et de sa valeur correspondante avec un sommet
	 * @param vertexID : identifiant unique du sommet
	 * @param attributeID : identifiant unique de l'attribut
	 * @throws TreeException si le sommet n'existe pas
	 * @throws VertexException si l'attribut n'existe pas
	 */
	public void addAttribute(String vertexID, String attributeID, String value) throws TreeException, VertexException {
		createAttribute(vertexID, attributeID);
		setAttribute(vertexID, attributeID, value);
	}
	
	/**
	 * Crée une relation
	 * @param relation : nom de la relation
	 * @throws TreeException si la relation existe déjà
	 * @throws RelationException si le nom de la relation est <code>null</code> ou vide
	 */
	public void createRelation(String relation) throws TreeException, RelationException {
		if(getRelation(relation) == null)
			relations.add(new Relation(relation));
		else throw new TreeException("La relation existe déjà.");
	}
	
	/**
	 * Crée une relation
	 * @param relation : la relation à ajouter
	 * @throws TreeException si la relation existe déjà
	 */
	public void createRelation(Relation relation) throws TreeException {
		if(getRelation(relation.getName()) == null) {
			relations.add(relation);
		} else throw new TreeException("La relation existe déjà.");
	}
	
	/**
	 * Retourne la relation portant le nom
	 * @param name : nom de la relation
	 * @return la relation portant le nom
	 */
	public Relation getRelation(String name) {
		for(Relation relation : relations) {
			if(relation.getName().equals(name))
				return relation;
		}
		return null;
	}
	
	/**
	 * Teste si la relation portant le nom existe
	 * @param name : nom de la relation à rechercher
	 * @return <code>true</code> si la relation existe, <code>false</code> sinon
	 */
	public boolean containsRelation(String name) {
		return (getRelation(name)) != null ? true : false;
	}
	
	/**
	 * Suppression de la relation portant le nom
	 * @param name : nom de la relation à supprimer
	 * @throws TreeException si la relation n'existe pas.
	 */
	public void removeRelation(String name) throws TreeException {
		if(!relations.remove(name))
			throw new TreeException("La relation n'existe pas.");
	}
	
	/**
	 * Ajoute une paire à la relation
	 * @param name : nom de la relation
	 * @param vertex1ID : identifiant unique du premier sommet
	 * @param vertex2ID : identifiant unique du second sommet
	 * @throws TreeException si la relation n'existe pas
	 * @throws RelationException si la paire, dans cet ordre précis, existe déjà
	 */
	public void addPair(String name, String vertex1ID, String vertex2ID) throws TreeException, RelationException {
		Relation relation = getRelation(name);
		if(relation != null) {
			Vertex vertex1 = getVertex(vertex1ID);
			Vertex vertex2 = getVertex(vertex2ID);
			relation.add(new Pair<>(vertex1, vertex2));
		} else throw new TreeException("La relation n'existe pas.");
	}
	
	/**
	 * Ajoute une paire à la relation
	 * @param name : nom de la relation
	 * @param vertex1 : premier sommet
	 * @param vertex2 : second sommet
	 * @throws RelationException si la paire, dans cet ordre précis, existe déjà
	 * @throws TreeException si la relation n'existe pas
	 */
	public void addPair(String name, Vertex vertex1, Vertex vertex2) throws RelationException, TreeException {
		Relation relation = getRelation(name);
		if(relation != null)
			relation.add(new Pair<>(vertex1, vertex2));
		else throw new TreeException("La relation n'existe pas.");
	}
	
	/**
	 * Ajoute une paire à la relation
	 * @param name : nom de la relation
	 * @param pair : paire à ajouter
	 * @throws RelationException si la paire, dans cet ordre précis, existe déjà
	 * @throws TreeException si la relation n'existe pas
	 */
	public void addPair(String name, Pair<Vertex, Vertex> pair) throws RelationException, TreeException {
		Relation relation = getRelation(name);
		if(relation != null) {
			relation.add(pair);
		} else throw new TreeException("La relation n'existe pas.");
	}
	
	/**
	 * Supprime la paire correspondante dans la relation
	 * @param name : nom de la relation
	 * @param pair : paire à supprimer et à rechercher
	 * @throws TreeException si la relation n'existe pas, ou si la paire n'existe pas
	 * @throws RelationException si la paire n'existe pas
	 */
	public void removePair(String name, Pair<Vertex, Vertex> pair) throws TreeException, RelationException {
		Relation relation = getRelation(name);
		if(relation != null) {
			relation.remove(pair);
		} else throw new TreeException("La relation n'existe pas.");
	}
	
	/**
	 * Supprime les paires des relations lié au sommet
	 * @param vertex : sommet à rechercher
	 */
	public void removeRelatedPair(Vertex vertex) {
		for(Relation relation : relations) {
			relation.removeRelatedPair(vertex);
		}
	}
	
	/**
	 * Retourne le nombre de relation
	 * @return le nombre de relation
	 */
	public int nbRelations() { return relations.size(); }
	
	/**
	 * Vide l'arbre courant
	 */
	public void clear() {
		vertices.clear();
		relations.clear();
	}
	
	/**
	 * Retourne un ensemble des attributs utilisés
	 * @return un ensemble des attributs utilisés
	 */
	public Set<String> getAttributes() {
		Set<String> attributes = new HashSet<>();
		attributes.add("ID");
		for(Vertex vertex : vertices) {
			attributes.addAll(vertex.getKey());
		}
		return attributes;
	}
	
	@Override
	public String toString() {
		String ch = "Sommets (" + vertices.size() + ") : \n";
		for(Vertex vertex : vertices) {
			ch += "\t" + vertex + "\n";
		}
		
		ch += "Relations (" + relations.size() + ") : \n";
		for(Relation relation : relations) {
			ch += "\t" + relation;
		}
		return ch;
	}
}
