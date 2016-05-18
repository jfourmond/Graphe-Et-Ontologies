package fr.fourmond.jerome.view;

import fr.fourmond.jerome.framework.Pair;
import fr.fourmond.jerome.framework.Vertex;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
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
		
		color = Color.BLACK;

		buildComposants();
		buildInterface();
		buildEvents();
	}
	
	public EdgeView(String relationName, VertexView start, VertexView end, Color color) {
		super();
		this.relationName = relationName;
		this.start = start;
		this.end = end;
		
		this.color = color;
		
		buildComposants();
		buildInterface();
		buildEvents();
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
	
	public void setColor(Color color) {
		this.color = color;
		line.setStroke(color);
		label.setTextFill(color);
	}
	
	public void setLine(Line line) { this.line = line; }
	
	public void setLabel(Label label) { this.label = label; }
	
	//	METHODES
	public Vertex getVertexStart() { return start.getVertex(); }
	
	public Vertex getVertexEnd() { return end.getVertex(); }
	
	public Pair<Vertex, Vertex> getPair() { return new Pair<Vertex, Vertex>(getVertexStart(), getVertexEnd()); }
	
	public void setWordingVisible(boolean bool) { label.setVisible(bool); }
	
	private void buildComposants() {
		double middleX = (start.getCenterX() + end.getCenterX()) / 2;
		double middleY = (start.getCenterY() + end.getCenterY()) / 2;
		
		line = new Line(start.getCenterX(), start.getCenterY(), end.getCenterX(), end.getCenterY());
			line.setStroke(color);
		label = new Label("" + relationName);
			label.setVisible(false);
			label.setTextFill(color);
			label.setTranslateX(middleX);
			label.setTranslateY(middleY);
	}
	
	private void buildInterface() {
		this.getChildren().add(line);
		this.getChildren().add(label);
	}
	
	private void buildEvents() {
		start.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton() == MouseButton.PRIMARY) {
					double middleX = (start.getCenterX() + end.getCenterX()) / 2;
					double middleY = (start.getCenterY() + end.getCenterY()) / 2;
					
					line.setStartX(event.getX());
					line.setStartY(event.getY());
					
					label.setTranslateX(middleX);
					label.setTranslateY(middleY);
				}
			}
		});
		
		end.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getButton() == MouseButton.PRIMARY) {
					double middleX = (start.getCenterX() + end.getCenterX()) / 2;
					double middleY = (start.getCenterY() + end.getCenterY()) / 2;
					
					line.setEndX(event.getX());
					line.setEndY(event.getY());
					
					label.setTranslateX(middleX);
					label.setTranslateY(middleY);
				}
			}
		});
	}
	
	public String info() {
		String ch = relationName + "\n";
		ch += "\t" + start.getVertex() + " -> " + end.getVertex();
		return ch;
	}
}
