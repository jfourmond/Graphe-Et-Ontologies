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
	private static int radius = 20;
	
	private Circle circle;
	private Label id;
	private Label name;
	
	private Vertex vertex;
	private boolean selected;
	
	private boolean showID = true;	// VRAI si l'ID est affiché, FAUX  si le nom est affiché
	
	public VertexView(Vertex vertex, int x, int y) {
		super();
		this.vertex = vertex;
		
		circle = new Circle(x, y, radius, Color.TRANSPARENT);
		
		buildComposants();
		buildInterface();
	}
	
	public VertexView(Vertex vertex, Point p) {
		super();
		this.vertex = vertex;
		
		circle = new Circle(p.x, p.y, radius, Color.TRANSPARENT);

		buildComposants();
		buildInterface();
	}
	
	public VertexView(Vertex vertex, Pair<Double, Double> pair) {
		super();
		this.vertex = vertex;
		
		circle = new Circle(pair.getFirst(), pair.getSecond(), radius, Color.TRANSPARENT);
		
		buildComposants();
		buildInterface();
	}
	
	//	GETTERS
	
	public Vertex getVertex() { return vertex; }
	
	public Circle getCircle() { return circle; }
	
	public static int getRadius() { return radius; }
	
	public boolean isSelected() { return selected; }
	
	//	SETTERS
	public void setVertex(Vertex vertex) { this.vertex = vertex; }
	
	public void setCircle(Circle circle) { this.circle = circle; }
	
	public void setSelected(boolean selected) {
		this.selected = selected;
		if(this.selected) {
			circle.setStroke(Color.RED);
			circle.setStrokeWidth(2);
			id.setTextFill(Color.RED);
			id.setStyle("-fx-font-weight: bold");
			name.setTextFill(Color.RED);
			name.setStyle("-fx-font-weight: bold");
		} else {
			circle.setStrokeWidth(1);
			circle.setStroke(Color.BLACK);
			id.setTextFill(Color.BLACK);
			id.setStyle(null);
			name.setTextFill(Color.BLACK);
			name.setStyle(null);
		}
	}
	
	public static void setRadius(int diameter) { VertexView.radius = diameter; }
	
	//	METHODES
	public double getCenterX() { return circle.getCenterX(); }
	
	public double getCenterY() { return circle.getCenterY(); }
	
	public void setCenterX(double value) {
		circle.setCenterX(value);
		id.setTranslateX(value);
		name.setTranslateX(value);
	}
	
	public void setCenterY(double value) {
		circle.setCenterY(value);
		id.setTranslateY(value);
		name.setTranslateY(value);
	}
	
	public void updateDiameter() { circle.setRadius(radius); }
	
	public void showID(boolean b) {
		showID = b;
		id.setVisible(b);
		name.setVisible(!b);
	}
	
	public void showName(boolean b) {
		showID = !b;
		id.setVisible(!b);
		name.setVisible(b);
	}
	
	public void switchShow() {
		showID = !showID;
		id.setVisible(showID);
		name.setVisible(!showID);	
	}
	
	private void buildComposants() {
		circle.setStroke(Color.BLACK);
		
		id = new Label(vertex.getID());
			id.setTranslateX(circle.getCenterX());
			id.setTranslateY(circle.getCenterY());
			id.setVisible(showID);
		name = new Label(vertex.getName());
			name.setTranslateX(circle.getCenterX());
			name.setTranslateY(circle.getCenterY());
			name.setVisible(!showID);
		
		selected = false;
	}
	
	private void buildInterface() {
		getChildren().add(circle);
		getChildren().add(id);
		getChildren().add(name);
	}
}
