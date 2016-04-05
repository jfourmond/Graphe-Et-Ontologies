package fr.fourmond.jerome.framework;

/**
 * {@link Edge} représente un arc
 * @param <T>	Valeur de retour de l'arc 
 * @author jfourmond
 */
public class Edge<T> {
	private Vertex firstVertex;
	private Vertex secondVertex;
	private T value;
	
	
	//	CONSTRUCTEURS
	public Edge() {};
	
	public Edge(Vertex firstVertex, Vertex secondVertex) {
		this.firstVertex = firstVertex;
		this.secondVertex = secondVertex;
	}
	
	public Edge(Vertex firstVertex, Vertex secondVertex, T value) {
		this.firstVertex= firstVertex;
		this.secondVertex = secondVertex;
		this.value = value;
	}

	//	GETTERS
	public Vertex getFirstVertex() {
		return firstVertex;
	}

	public Vertex getSecondVertex() {
		return secondVertex;
	}

	public T getValue() {
		return value;
	}

	//	SETTERS
	public void setFirstVertex(Vertex firstVertex) {
		this.firstVertex = firstVertex;
	}
	
	public void setSecondVertex(Vertex secondVertex) {
		this.secondVertex = secondVertex;
	}
	

	public void setValue(T value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "(" + firstVertex.briefData() + " , " + secondVertex.briefData() + " , " + value + ")\n";
	}
}
