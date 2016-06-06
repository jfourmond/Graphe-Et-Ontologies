package fr.fourmond.jerome.view;

import java.util.ArrayList;
import java.util.List;

import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.framework.Vertex;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.Image;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class DataStage extends Stage {

	private final static String TITLE = "Graphe Et Ontologies - Données";
	
	private Tree tree;
		private List<Vertex> vertices;
	
	
	private VBox vBox;
		private Text text;
		private TableView<Vertex> tableView;
			private List<TableColumn<Vertex, String>> tableColumns;
			
	public DataStage(Tree tree) {
		setTitle(TITLE);
		
		this.tree = tree;
		
		buildComposants();
		buildInterface();
	}

	private void buildComposants() {
		getIcons().add(new Image("file:gando.png"));
		
		vertices = tree.getVertices();
		tableColumns = new ArrayList<>();
		
		ObservableList<Vertex> items = FXCollections.observableArrayList(vertices);
		
			text = new Text("Liste des sommet");
			text.setFont(Font.font("Verdana", 15));
			tableView = new TableView<>(items);
		vBox = new VBox(10);
		
		List<String> attributes = tree.getAttributes();
		for(String attribute : attributes) {
			TableColumn<Vertex, String> tableColumn = new TableColumn<>(attribute);
			tableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Vertex,String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<Vertex, String> param) {
					Vertex vertex = param.getValue();
					return new SimpleStringProperty(vertex.get(attribute));
				}
			});
			tableColumns.add(tableColumn);
		}
		
		tableView.getColumns().addAll(tableColumns);
		
		vBox.getChildren().addAll(text, tableView);
	}

	private void buildInterface() {
		Scene scene = new Scene(vBox);
		this.setScene(scene);
	}
}
