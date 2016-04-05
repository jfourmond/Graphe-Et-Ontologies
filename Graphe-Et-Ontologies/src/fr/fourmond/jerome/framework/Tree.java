package fr.fourmond.jerome.framework;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link Tree} représente un arbre/graphe non orienté (Théorie des graphes).
 * Plus précisément un graphe orienté dont les sommets sont
 * basés sur une classe implémentant {@link Vertex}
 * @param <V_T> Le type d'un sommet, sous contrainte qu'il étende {@link Vertex}
 * @param <E_T> Le type de valeur d'un arc
 * @author jfourmond
 */
public class Tree<V_T extends Vertex, E_T > {

	private List<V_T> vertices;
	private List<Edge<E_T>> edges;
	
	//	CONSTRUCTEURS
	public Tree() {
		vertices = new ArrayList<>();
		edges = new ArrayList<>();
	}
	
	public Tree(List<V_T> vertices) { this.vertices = vertices; }
	
	public Tree(List<V_T> vertices, List<Edge<E_T>> edges) {
		this.vertices = vertices;
		this.edges = edges;
	}
	
	//	GETTERS
	public List<V_T> getVertices() { return vertices; }
	
	public List<Edge<E_T>> getEdges() { return edges; }
	
	//	SETTERS
	public void setVertices(List<V_T> vertices) { this.vertices = vertices; }
	
	public void setEdges(List<Edge<E_T>> edges) { this.edges = edges; }
	
	/**
	 * Ajout d'un sommet dans l'arbre
	 * @param vertex : le {@link Vertex} à ajouter
	 */
	public void addVertex(V_T vertex) {
		// TODO Vérifier que le sommet n'existe pas déjà
		vertices.add(vertex);
	}
	
	/**
	 * Ajout d'un arc dans l'arbre
	 * @param edge : le {@link Edge} à ajouter
	 */
	public void addEdges(Edge<E_T> edge) {
		// TODO vérifier que les sommets de l'arc existe
		edges.add(edge);
	}
	
	@Override
	public String toString() {
		String ch = "Sommets : \n";
		for(V_T vertex : vertices) {
			ch += vertex.fullData();
		}
		ch += "Edges : \n";
		for(Edge<E_T> edge : edges) {
			ch += "\t" + edge + "\n";
		}
		return ch;
	}
}
