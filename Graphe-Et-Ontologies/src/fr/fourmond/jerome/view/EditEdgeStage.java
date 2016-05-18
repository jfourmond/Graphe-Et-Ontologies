package fr.fourmond.jerome.view;

import java.util.List;

import fr.fourmond.jerome.framework.Pair;
import fr.fourmond.jerome.framework.Vertex;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class EditEdgeStage extends Stage {

	private static final String TITLE = "Graphe Et Ontologies - Relation ";
	
	private String oldRelationName;
	private Pair<Vertex, Vertex> oldPair;
	
	private List<Vertex> vertices;
	private List<String> relations;
	
	private String newRelationName;
	private Pair<Vertex, Vertex> newPair;
	
	private ObservableList<String> relationList;
	private ObservableList<Vertex> vertex1;
	private ObservableList<Vertex> vertex2;
	
	private GridPane gridPane;
	private Text title;
		private ComboBox<String> CBRelation;
		private Text from;
		private ComboBox<Vertex> CB1;
		private Text to;
		private ComboBox<Vertex> CB2;
	private Button edit;
	
	public EditEdgeStage(Pair<Vertex, Vertex> currentPair, String currentRelation, List<Vertex> vertices, List<String> relations) {
		setTitle(TITLE + currentRelation + " - Edition Arc");
		
		this.oldPair = currentPair;
		this.oldRelationName = currentRelation;
		this.vertices = vertices;
		this.relations = relations;
		
		buildComposants();
		buildInterface();
		buildEvents();
	}
	
	//	GETTERS
	public String getNewRelationName() { return newRelationName; }
	
	public Pair<Vertex, Vertex> getNewPair() { return newPair; }
	
	public String getOldRelationName() { return oldRelationName; }
	
	public Pair<Vertex, Vertex> getOldPair() { return oldPair; }
	
	//	SETTERS
	public void setNewRelationName(String relationName) { this.newRelationName = relationName; }
	
	public void setNewPair(Pair<Vertex, Vertex> pair) { this.newPair = pair; }
	
	public void setOldRelation(String relationName) { this.oldRelationName = relationName; }
	
	public void setOldPair(Pair<Vertex, Vertex> oldPair) { this.oldPair = oldPair; }
	
	//	METHODES
	private void buildComposants() {
		relationList = FXCollections.observableArrayList(relations);
		vertex1 = FXCollections.observableArrayList(vertices);
		vertex2 = FXCollections.observableArrayList(vertices);
		
		gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(25, 25, 25, 25));
		
		title = new Text("Arc");
			CBRelation = new ComboBox<>(relationList);
			CBRelation.getSelectionModel().select(oldRelationName);
			from = new Text(" De ");
				CB1 = new ComboBox<>(vertex1);
				CB1.setCellFactory(new Callback<ListView<Vertex>, ListCell<Vertex>>() {
					@Override
					public ListCell<Vertex> call(ListView<Vertex> param) {
						return new VertexList();
					}
				});
				CB1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Vertex>() {
					@Override
					public void changed(ObservableValue<? extends Vertex> observable, Vertex oldValue, Vertex newValue) {
						if(CB2 != null && newValue == CB2.getValue())
							CB2.getSelectionModel().select(oldValue);
					}
				});
				CB1.getSelectionModel().select(oldPair.getFirst());;
			to = new Text(" à ");
				CB2 = new ComboBox<>(vertex2);
				CB2.setCellFactory(CB1.getCellFactory());
				CB2.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Vertex>() {
					@Override
					public void changed(ObservableValue<? extends Vertex> observable, Vertex oldValue, Vertex newValue) {
						if(newValue == CB1.getValue())
							CB1.getSelectionModel().select(oldValue);
					}
				});
				CB2.getSelectionModel().select(oldPair.getSecond());
		edit = new Button("Modifier");
	}
	
	private void buildInterface() {
		gridPane.add(title, 0, 0, 2, 1);
		gridPane.add(CBRelation, 0, 1);
		gridPane.add(from, 1, 1);
		gridPane.add(CB1, 2, 1);
		gridPane.add(to, 3, 1);
		gridPane.add(CB2, 4, 1);
		
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(edit);
		gridPane.add(edit, 1, 3);
		
		Scene scene = new Scene(gridPane, 300, 200);
		this.setScene(scene);
	}
	
	private void buildEvents() {
		edit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				buildPair();
				close();
			}
		});
	}
	
	private void buildPair() {
		String r = CBRelation.getValue();
		Vertex vertex1 = CB1.getValue();
		Vertex vertex2 = CB2.getValue();
		
		newPair = new Pair<Vertex, Vertex>(vertex1, vertex2);
		newRelationName = r;
		System.out.println(newPair + "\n\t" + newRelationName);
	}
}
