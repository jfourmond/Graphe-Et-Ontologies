package fr.fourmond.jerome.view;

import java.awt.Point;

import fr.fourmond.jerome.framework.Pair;
import fr.fourmond.jerome.framework.Vertex;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * {@link VertexView} est un {@link Group} représentant
 * un sommet du graphe
 * @author jfourmond
 */
public class VertexView extends Group {
	private static int diameter = 25;
	
	private Circle circle;
	private Label label;
	
	private Vertex vertex;
	private boolean selected;
	
	public VertexView(Vertex vertex, int x, int y) {
		super();
		this.vertex = vertex;
		
		circle = new Circle(x, y, diameter, Color.TRANSPARENT);
		circle.setStroke(Color.BLACK);
		
		label = new Label(vertex.getID());
		label.setTranslateX(circle.getCenterX());
		label.setTranslateY(circle.getCenterY());
		
		getChildren().add(circle);
		getChildren().add(label);
		
		selected = false;
	}
	
	public VertexView(Vertex vertex, Point p) {
		super();
		this.vertex = vertex;
		
		circle = new Circle(p.x, p.y, diameter, Color.TRANSPARENT);
		circle.setStroke(Color.BLACK);
		
		label = new Label(vertex.getID());
		label.setTranslateX(circle.getCenterX());
		label.setTranslateY(circle.getCenterY());
		
		getChildren().add(circle);
		getChildren().add(label);
		
		selected = false;
	}
	
	public VertexView(Vertex vertex, Pair<Double, Double> pair) {
		super();
		this.vertex = vertex;
		
		circle = new Circle(pair.getFirst(), pair.getSecond(), diameter, Color.TRANSPARENT);
		circle.setStroke(Color.BLACK);
		
		label = new Label(vertex.getID());
		label.setTranslateX(circle.getCenterX());
		label.setTranslateY(circle.getCenterY());
		
		getChildren().add(circle);
		getChildren().add(label);
	}
	
	//	GETTERS
	public Vertex getVertex() { return vertex; }
	
	public Circle getCircle() { return circle; }
	
	public static int getDiameter() { return diameter; }
	
	public boolean isSelected() { return selected; }
	
	//	SETTERS
	public void setVertex(Vertex vertex) { this.vertex = vertex; }
	
	public void setCircle(Circle circle) { this.circle = circle; }
	
	public void setSelected(boolean selected) {
		this.selected = selected;
		if(this.selected) circle.setStroke(Color.RED);
		else circle.setStroke(Color.BLACK);
	}
	
	//	METHODES
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
