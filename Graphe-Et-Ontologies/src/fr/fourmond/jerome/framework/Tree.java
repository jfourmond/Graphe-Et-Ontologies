package fr.fourmond.jerome.framework;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
	 * @throws TreeException si le sommet est déjà dans l'arbre
	 */
	public void addVertex(T_Vertex vertex) throws TreeException{
		if(vertices.contains(vertex))
			throw new TreeException("Vertex already in the Tree");
		else
			vertices.add(vertex);
	}
	
	/**
	 * Ajoute un arc dans l'arbre
	 * @param edge : le {@link Edge} à ajouter
	 * @throws TreeException si l'arc est déjà dans l'arbre
	 */
	public void addEdge(Edge<T_Vertex, T_Edge> edge) throws TreeException {
		if(!isVertexExist(edge.getFirstVertex()) || !isVertexExist(edge.getSecondVertex())) {
			throw new TreeException("Vertex undefined in the Tree");
		} else if(edges.contains(edge) || isEdgeExistBetween(edge.getFirstVertex(), edge.getSecondVertex()))
			throw new TreeException("Edge already in the Tree");
		else 
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
	
	/**
	 * Teste s'il existe un arc entre le premier sommet et le second
	 * @param firstVertex : le premier sommet
	 * @param secondVertex : le second sommet
	 * @return <code>true</code> si un arc existe, <code>false</code> sinon
	 */
	public boolean isEdgeExistBetween(T_Vertex firstVertex, T_Vertex secondVertex) {
		for(Edge<T_Vertex, T_Edge> edge : edges) {
			if(edge.getFirstVertex() == firstVertex && edge.getSecondVertex() == secondVertex)
				return true;
		}
		return false;
	}
	
	/**
	 * Teste si le sommet existe dans l'arbre
	 * @param vertex : le sommet à rechercher
	 * @return <code>true</code> si le sommet existe, <code>false</code> sinon
	 */
	public boolean isVertexExist(T_Vertex vertex) {
		for(T_Vertex v : vertices) {
			if(v == vertex) return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		String ch = "Sommets (" + vertices.size() + ") : \n";
		for(T_Vertex vertex : vertices) {
			ch += "\t" + vertex.fullData();
		}
		ch += "Arcs (" + edges.size() + ") : \n";
		for(Edge<T_Vertex, T_Edge> edge : edges) {
			ch += "\t" + edge + "\n";
		}
		return ch;
	}
}
