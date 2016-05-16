package fr.fourmond.jerome.view;

import javafx.scene.control.ListCell;

public class VertexViewList extends ListCell<VertexView> {
	@Override
	protected void updateItem(VertexView item, boolean empty) {
		super.updateItem(item, empty);
		if(item == null || empty)
			setText(null);
		else if(item != null)
			setText(item.getVertex().getID());
	}
}
