package fr.fourmond.jerome.view.fx;

import fr.fourmond.jerome.framework.Edge;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;

/**
 * {@link EdgeFxView} est une {@link Group} représentant
 * un arc du graphe
 * @param <T> : type de la valeur de {@link Edge}
 * @author jfourmond
 */
public class EdgeFxView<T> extends Group {
	private T value;
	
	private Line line;
	private Label label;
	
	public EdgeFxView(T value, double startx, double starty, double endx, double endy) {
		super();
		this.value = value;
		
		double middleX = (startx + endx) / 2;
		double middleY = (starty + endy) / 2;
		
		line = new Line(startx, starty, endx, endy);
		label = new Label("" + value);
			label.setTranslateX(middleX);
			label.setTranslateY(middleY);
		
		this.getChildren().add(line);
		this.getChildren().add(label);
	}

	//	GETTERS
	public T getValue() { return value; }
	
	public Line getLine() { return line; }

	public Label getLabel() { return label; }
	
	//	SETTERS
	public void setValue(T value) { this.value = value; }
	
	public void setLine(Line line) { this.line = line; }
	
	public void setLabel(Label label) { this.label = label; }
}
