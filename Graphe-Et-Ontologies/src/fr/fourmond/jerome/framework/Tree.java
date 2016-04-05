package fr.fourmond.jerome.framework;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link Tree} représente un arbre/graphe non orienté (Théorie des graphes).
 * Plus précisément un graphe orienté dont les sommets sont
 * basés sur une classe implémentant {@link Vertex}
 * @param <Vertex_T> Le type d'un sommet, sous contrainte qu'il étende {@link Vertex}
 * @param <Edge_T> Le type de valeur d'un arc
 * @author jfourmond
 */
public class Tree<Vertex_T extends Vertex, Edge_T > {

	private List<Vertex_T> vertices;
	private List<Edge<Vertex_T, Edge_T>> edges;
	
	//	CONSTRUCTEURS
	public Tree() {
		vertices = new ArrayList<>();
		edges = new ArrayList<>();
	}
	
	public Tree(List<Vertex_T> vertices) { this.vertices = vertices; }
	
	public Tree(List<Vertex_T> vertices, List<Edge<Vertex_T, Edge_T>> edges) {
		this.vertices = vertices;
		this.edges = edges;
	}
	
	//	GETTERS
	public List<Vertex_T> getVertices() { return vertices; }
	
	public List<Edge<Vertex_T, Edge_T>> getEdges() { return edges; }
	
	//	SETTERS
	public void setVertices(List<Vertex_T> vertices) { this.vertices = vertices; }
	
	public void setEdges(List<Edge<Vertex_T, Edge_T>> edges) { this.edges = edges; }
	
	/**
	 * Ajout d'un sommet dans l'arbre
	 * @param vertex : le {@link Vertex} à ajouter
	 */
	public void addVertex(Vertex_T vertex) {
		// TODO Vérifier que le sommet n'existe pas déjà
		vertices.add(vertex);
	}
	
	/**
	 * Ajout d'un arc dans l'arbre
	 * @param edge : le {@link Edge} à ajouter
	 */
	public void addEdges(Edge<Vertex_T, Edge_T> edge) {
		// TODO vérifier que les sommets de l'arc existe
		edges.add(edge);
	}
	
	@Override
	public String toString() {
		String ch = "Sommets : \n";
		for(Vertex_T vertex : vertices) {
			ch += vertex.fullData();
		}
		ch += "Edges : \n";
		for(Edge<Vertex_T, Edge_T> edge : edges) {
			ch += "\t" + edge + "\n";
		}
		return ch;
	}
}
