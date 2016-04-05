package fr.fourmond.jerome.framework;

/**
 * {@link Edge} représente un arc
 * @param <T> Valeur de retour de l'arc
 * @param <Vertex_T> Type des sommets, sous contraintes qu'il etende {@link Vertex}
 * @param <Value_T> Type du libellé de l'arc 
 * @author jfourmond
 */
public class Edge<Vertex_T extends Vertex, Value_T> {
	private Vertex_T firstVertex;
	private Vertex_T secondVertex;
	private Value_T value;
	
	
	//	CONSTRUCTEURS
	public Edge() {};
	
	public Edge(Vertex_T firstVertex, Vertex_T secondVertex) {
		this.firstVertex = firstVertex;
		this.secondVertex = secondVertex;
	}
	
	public Edge(Vertex_T firstVertex, Vertex_T secondVertex, Value_T value) {
		this.firstVertex= firstVertex;
		this.secondVertex = secondVertex;
		this.value = value;
	}

	//	GETTERS
	public Vertex_T getFirstVertex() {
		return firstVertex;
	}

	public Vertex_T getSecondVertex() {
		return secondVertex;
	}

	public Value_T getValue() {
		return value;
	}

	//	SETTERS
	public void setFirstVertex(Vertex_T firstVertex) {
		this.firstVertex = firstVertex;
	}
	
	public void setSecondVertex(Vertex_T secondVertex) {
		this.secondVertex = secondVertex;
	}
	

	public void setValue(Value_T value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "(" + firstVertex.briefData() + " , " + secondVertex.briefData() + " , " + value + ")\n";
	}
}
