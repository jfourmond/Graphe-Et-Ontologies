package fr.fourmond.jerome.view;

import fr.fourmond.jerome.framework.Vertex;
import javafx.scene.control.ListCell;

public class VertexList extends ListCell<Vertex> {
	@Override
	protected void updateItem(Vertex item, boolean empty) {
		super.updateItem(item, empty);
		if(item != null)
			setText(item.getID());
		else setText(null);
	}
}
