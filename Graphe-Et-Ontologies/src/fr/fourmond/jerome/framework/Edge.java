package fr.fourmond.jerome.framework;

/**
 * {@link Edge} représente un arc
 * @param <T> Valeur de retour de l'arc
 * @param <T_Vertex> Type des sommets, sous contraintes qu'il etende {@link Vertex}
 * @param <T_Value> Type du libellé de l'arc 
 * @author jfourmond
 */
public class Edge<T_Vertex extends Vertex, T_Value> {
	private T_Vertex firstVertex;
	private T_Vertex secondVertex;
	private T_Value value;
	
	
	//	CONSTRUCTEURS
	public Edge() {};
	
	public Edge(T_Vertex firstVertex, T_Vertex secondVertex) {
		this.firstVertex = firstVertex;
		this.secondVertex = secondVertex;
	}
	
	public Edge(T_Vertex firstVertex, T_Vertex secondVertex, T_Value value) {
		this.firstVertex= firstVertex;
		this.secondVertex = secondVertex;
		this.value = value;
	}

	//	GETTERS
	public T_Vertex getFirstVertex() {
		return firstVertex;
	}

	public T_Vertex getSecondVertex() {
		return secondVertex;
	}

	public T_Value getValue() {
		return value;
	}

	//	SETTERS
	public void setFirstVertex(T_Vertex firstVertex) {
		this.firstVertex = firstVertex;
	}
	
	public void setSecondVertex(T_Vertex secondVertex) {
		this.secondVertex = secondVertex;
	}
	

	public void setValue(T_Value value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "(" + firstVertex.briefData() + " , " + secondVertex.briefData() + " , " + value + ")\n";
	}
}
