package fr.fourmond.jerome.framework;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link Tree} représente un arbre/graphe non orienté (Théorie des graphes).
 * Plus précisément un graphe orienté dont les sommets sont
 * basés sur une classe implémentant {@link Vertex}
 * @param <T_Vertex> Le type d'un sommet, sous contrainte qu'il étende {@link Vertex}
 * @param <T_Edge> Le type de valeur d'un arc
 * @author jfourmond
 */
public class Tree<T_Vertex extends Vertex, T_Edge > {

	private List<T_Vertex> vertices;
	private List<Edge<T_Vertex, T_Edge>> edges;
	
	//	CONSTRUCTEURS
	public Tree() {
		vertices = new ArrayList<>();
		edges = new ArrayList<>();
	}
	
	public Tree(List<T_Vertex> vertices) { this.vertices = vertices; }
	
	public Tree(List<T_Vertex> vertices, List<Edge<T_Vertex, T_Edge>> edges) {
		this.vertices = vertices;
		this.edges = edges;
	}
	
	//	GETTERS
	public List<T_Vertex> getVertices() { return vertices; }
	
	public List<Edge<T_Vertex, T_Edge>> getEdges() { return edges; }
	
	//	SETTERS
	public void setVertices(List<T_Vertex> vertices) { this.vertices = vertices; }
	
	public void setEdges(List<Edge<T_Vertex, T_Edge>> edges) { this.edges = edges; }
	
	/**
	 * Ajoute un sommet dans l'arbre
	 * @param vertex : le {@link Vertex} à ajouter
	 */
	public void addVertex(T_Vertex vertex) {
		if(!vertices.contains(vertex))
			vertices.add(vertex);
	}
	
	/**
	 * Ajoute un arc dans l'arbre
	 * @param edge : le {@link Edge} à ajouter
	 */
	public void addEdges(Edge<T_Vertex, T_Edge> edge) {
		if(!edges.contains(edge))
			edges.add(edge);
	}
	
	/**
	 * Réinitialise/Vide l'arbre
	 */
	public void clear() {
		vertices.clear();
		edges.clear();
	}
	
	/**
	 * @return le nombre de sommet de l'arbre
	 */
	public int getVertexNumber() { return vertices.size(); }
	
	/**
	 * @return le nombre d'arc de l'arbre
	 */
	public int getEdgeNumber() { return edges.size(); }
	
	@Override
	public String toString() {
		String ch = "Sommets : \n";
		for(T_Vertex vertex : vertices) {
			ch += vertex.fullData();
		}
		ch += "Edges : \n";
		for(Edge<T_Vertex, T_Edge> edge : edges) {
			ch += "\t" + edge + "\n";
		}
		return ch;
	}
}
