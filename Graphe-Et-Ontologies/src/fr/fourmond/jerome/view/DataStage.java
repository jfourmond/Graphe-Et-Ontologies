package fr.fourmond.jerome.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.framework.Vertex;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class DataStage extends Stage {

	private final static String TITLE = "Graphe Et Ontologies - Données";
	
	private Tree tree;
		private List<Vertex> vertices;
	
	
	private VBox vBox;
		private Label labelVertex;
		private TableView<Vertex> tableView;
			private List<TableColumn<Vertex, String>> tableColumns;
			
	public DataStage(Tree tree) {
		setTitle(TITLE);
		
		this.tree = tree;
		
		buildComposants();
		buildInterface();
		buildEvents();
	}

	private void buildComposants() {
		vertices = tree.getVertices();
		tableColumns = new ArrayList<>();
		
		ObservableList<Vertex> items = FXCollections.observableArrayList(vertices);
		
			labelVertex = new Label("Sommet");
			tableView = new TableView<>(items);
		vBox = new VBox(10);
		
		Set<String> attributes = tree.getAttributes();
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
		
		for(Vertex vertex : vertices) {
			for(String attribute : attributes) {
				String value = vertex.get(attribute);
				if(value != null) {
					
				}
			}
		}
		
		
		
		
		tableView.getColumns().addAll(tableColumns);
		
		vBox.getChildren().addAll(labelVertex, tableView);
	}

	private void buildInterface() {
		Scene scene = new Scene(vBox, 300, 200);
		this.setScene(scene);
	}

	private void buildEvents() {
		// TODO Auto-generated method stub
		
	}
	
}
