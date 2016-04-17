package fr.fourmond.jerome.view.fx;

import fr.fourmond.jerome.framework.Edge;
import javafx.scene.shape.Line;

/**
 * {@link EdgeFxView} est une {@link Line} représentant
 * un arc du graphe
 * @param <T> : type de la valeur de {@link Edge}
 * @author jfourmond
 */
public class EdgeFxView<T> extends Line {
	private T value;
	
	public EdgeFxView(T value, double startx, double starty, double endx, double endy) {
		super(startx, starty, endx, endy);
		this.value = value;
	}

	//	GETTERS
	public T getValue() { return value; }
	
	//	SETTERS
	public void setValue(T value) { this.value = value; }
}
