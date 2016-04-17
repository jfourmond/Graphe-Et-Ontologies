package fr.fourmond.jerome.view.fx;

import javafx.scene.shape.Line;

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
