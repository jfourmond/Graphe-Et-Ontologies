package fr.fourmond.jerome.view.fx.ontology;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class EdgeOntologyFxView extends Group {
	private String relation;
	private String value;
	
	private Color color;
	
	private Line line;
	private Label label;
	
	public EdgeOntologyFxView(String relation, String value, double startx, double starty, double endx, double endy) {
		super();
		this.relation = relation;
		this.value = value;
		
		double middleX = (startx + endx) / 2;
		double middleY = (starty + endy) / 2;
		
		color = Color.BLACK;
		
		line = new Line(startx, starty, endx, endy);
		line.setStroke(color);
		label = new Label("" + value);
			label.setTranslateX(middleX);
			label.setTranslateY(middleY);
		
		this.getChildren().add(line);
		this.getChildren().add(label);
	}
	
	public EdgeOntologyFxView(String relation, String value, double startx, double starty, double endx, double endy, Color color) {
		super();
		this.relation = relation;
		this.value = value;
		
		double middleX = (startx + endx) / 2;
		double middleY = (starty + endy) / 2;
		
		this.color = color;
		
		line = new Line(startx, starty, endx, endy);
		line.setStroke(color);
		label = new Label("" + value);
			label.setTranslateX(middleX);
			label.setTranslateY(middleY);
		
		this.getChildren().add(line);
		this.getChildren().add(label);
	}

	//	GETTERS
	public String getRelation() { return relation; }
	
	public String getValue() { return value; }
	
	public Color getColor() { return color; }
	
	public Line getLine() { return line; }

	public Label getLabel() { return label; }
	
	//	SETTERS
	public void setRelation(String relation) { this.relation = relation; }
	
	public void setColor(Color color) { this.color = color; }
	
	public void setValue(String value) { this.value = value; }
	
	public void setLine(Line line) { this.line = line; }
	
	public void setLabel(Label label) { this.label = label; }
}
