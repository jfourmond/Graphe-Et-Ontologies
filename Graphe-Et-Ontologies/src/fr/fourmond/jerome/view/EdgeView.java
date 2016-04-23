package fr.fourmond.jerome.view;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * {@link EdgeView} est un {@link Group} représentant 
 * un arc du graphe
 * @author jfourmond
 */
public class EdgeView extends Group {
	private String relationName;
	
	private Color color;
	
	private Line line;
	private Label label;
	
	public EdgeView(String relationName, double startx, double starty, double endx, double endy) {
		super();
		this.relationName = relationName;
		
		double middleX = (startx + endx) / 2;
		double middleY = (starty + endy) / 2;
		
		color = Color.BLACK;
		
		line = new Line(startx, starty, endx, endy);
		line.setStroke(color);
		label = new Label("" + relationName);
			label.setTranslateX(middleX);
			label.setTranslateY(middleY);
		
		this.getChildren().add(line);
		this.getChildren().add(label);
	}
	
	public EdgeView(String relationName, double startx, double starty, double endx, double endy, Color color) {
		super();
		this.relationName = relationName;
		
		double middleX = (startx + endx) / 2;
		double middleY = (starty + endy) / 2;
		
		this.color = color;
		
		line = new Line(startx, starty, endx, endy);
		line.setStroke(color);
		label = new Label("" + relationName);
			label.setTranslateX(middleX);
			label.setTranslateY(middleY);
		
		this.getChildren().add(line);
		this.getChildren().add(label);
	}

	//	GETTERS
	public String getRelation() { return relationName; }
	
	public Color getColor() { return color; }
	
	public Line getLine() { return line; }

	public Label getLabel() { return label; }
	
	//	SETTERS
	public void setRelation(String relation) { this.relationName = relation; }
	
	public void setColor(Color color) { this.color = color; }
	
	public void setLine(Line line) { this.line = line; }
	
	public void setLabel(Label label) { this.label = label; }
}
