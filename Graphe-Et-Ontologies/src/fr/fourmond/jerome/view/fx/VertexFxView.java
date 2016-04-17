package fr.fourmond.jerome.view.fx;


import fr.fourmond.jerome.framework.Vertex;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * {@link VertexFxView} est un {@link Group} représentant
 * un sommet du graphe
 * @param <T> : le type implémentant l'interface {@link Vertex}
 * @author jfourmond
 */
public class VertexFxView<T extends Vertex> extends Group {
	private static int diameter = 25;
	
	private Circle circle;
	private Label label;
	private T vertex;
	
	public VertexFxView(T vertex, int x, int y) {
		super();
		this.vertex = vertex;
		
		circle = new Circle(x, y, diameter, Color.TRANSPARENT);
		circle.setStroke(Color.BLACK);
		
		label = new Label(vertex.briefData());
		label.setTranslateX(circle.getCenterX());
		label.setTranslateY(circle.getCenterY());
		
		getChildren().add(circle);
		getChildren().add(label);
	}
	
	//	GETTERS
	public T getVertex() { return vertex; }
	
	public Circle getCircle() { return circle; }
	
	public static int getDiameter() { return diameter; }
	
	//	SETTERS
	public void setVertex(T vertex) { this.vertex = vertex; }
	
	public void setCircle(Circle circle) { this.circle = circle; }
	
	public double getCenterX() { return circle.getCenterX(); }
	
	public double getCenterY() { return circle.getCenterY(); }
	
	public void setCenterX(double value) {
		circle.setCenterX(value);
		label.setTranslateX(value);
	}
	
	public void setCenterY(double value) {
		circle.setCenterY(value);
		label.setTranslateY(value);
	}
	
	
}
