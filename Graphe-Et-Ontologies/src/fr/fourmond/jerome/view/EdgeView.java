package fr.fourmond.jerome.view;

import fr.fourmond.jerome.framework.Pair;
import fr.fourmond.jerome.framework.Vertex;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
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
	
	private VertexView start;
	private VertexView end;
	
	private Line line;
	private Label label;
	
	public EdgeView(String relationName, VertexView start, VertexView end) {
		super();
		this.relationName = relationName;
		this.start = start;
		this.end = end;
		
		double middleX = (start.getCenterX() + end.getCenterX()) / 2;
		double middleY = (start.getCenterY() + end.getCenterY()) / 2;
		
		color = Color.BLACK;
		
		line = new Line(start.getCenterX(), start.getCenterY(), end.getCenterX(), end.getCenterY());
		line.setStroke(color);
		label = new Label("" + relationName);
			label.setTranslateX(middleX);
			label.setTranslateY(middleY);

		start.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				line.setStartX(event.getX());
				line.setStartY(event.getY());
			}
		});
		
		end.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				line.setEndX(event.getX());
				line.setEndY(event.getY());
			}
		});
		
		this.getChildren().add(line);
		this.getChildren().add(label);
	}
	
	public EdgeView(String relationName, VertexView start, VertexView end, Color color) {
		super();
		this.relationName = relationName;
		this.start = start;
		this.end = end;
		
		double middleX = (start.getCenterX() + end.getCenterX()) / 2;
		double middleY = (start.getCenterY() + end.getCenterY()) / 2;
		
		this.color = color;
		
		line = new Line(start.getCenterX(), start.getCenterY(), end.getCenterX(), end.getCenterY());
		line.setStroke(color);
		label = new Label("" + relationName);
			label.setTranslateX(middleX);
			label.setTranslateY(middleY);
		
		start.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				line.setStartX(event.getX());
				line.setStartY(event.getY());
			}
		});
		
		end.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				line.setEndX(event.getX());
				line.setEndY(event.getY());
			}
		});
		
		this.getChildren().add(line);
		this.getChildren().add(label);
	}

	//	GETTERS
	public String getRelation() { return relationName; }
	
	public VertexView getStart() { return start; }
	
	public VertexView getEnd() { return end; }
	
	public Color getColor() { return color; }
	
	public Line getLine() { return line; }

	public Label getLabel() { return label; }
	
	//	SETTERS
	public void setRelation(String relation) { this.relationName = relation; }
	
	public void setStart(VertexView start) { this.start = start; }
	
	public void setEnd(VertexView end) { this.end = end; }
	
	public void setColor(Color color) { this.color = color; }
	
	public void setLine(Line line) { this.line = line; }
	
	public void setLabel(Label label) { this.label = label; }
	
	//	METHODES
	public Vertex getVertexStart() { return start.getVertex(); }
	
	public Vertex getVertexEnd() { return end.getVertex(); }
	
	public Pair<Vertex, Vertex> getPair() { return new Pair<Vertex, Vertex>(getVertexStart(), getVertexEnd()); }
}
