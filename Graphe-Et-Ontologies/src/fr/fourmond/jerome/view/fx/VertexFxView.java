package fr.fourmond.jerome.view.fx;


import fr.fourmond.jerome.framework.Vertex;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class VertexFxView<T extends Vertex> extends Circle {
	private static int diameter = 25;
	
	private T vertex;
	
	public VertexFxView(T vertex, int x, int y) {
		super(x, y, diameter, Color.TRANSPARENT);
		super.setStroke(Color.BLACK);
		
		this.vertex = vertex;
	}
	
	//	GETTERS
	public T getVertex() { return vertex; }
	
	public static int getDiameter() { return diameter; }
	
	//	SETTERS
	public void setVertex(T vertex) { this.vertex = vertex; }
	
}
