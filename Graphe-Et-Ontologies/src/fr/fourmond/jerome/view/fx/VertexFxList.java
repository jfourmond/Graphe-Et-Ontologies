package fr.fourmond.jerome.view.fx;

import fr.fourmond.jerome.framework.Vertex;
import javafx.scene.control.ListCell;

public class VertexFxList<T extends Vertex> extends ListCell<VertexFxView<T>> {
	@Override
	protected void updateItem(VertexFxView<T> item, boolean empty) {
		super.updateItem(item, empty);
		if(item != null)
			setText(item.getVertex().briefData());
	}
}
