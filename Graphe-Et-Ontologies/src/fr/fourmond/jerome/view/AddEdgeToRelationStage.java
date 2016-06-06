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
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

/**
 * {@link AddEdgeToRelationStage} est un {@link Stage} permettant de créer
 * un arc pour une relation précise, qui sera ajouté à l'arbre
 * @author jfourmond
 */
public class AddEdgeToRelationStage extends Stage {

	private static final String TITLE = "Graphe Et Ontologies - Relation ";
	
	private String relationName;
	private Pair<Vertex, Vertex> pair;
	
	private List<Vertex> vertices;
	
	private ObservableList<Vertex> vertex1;
	private ObservableList<Vertex> vertex2;
	
	private VBox vBox;
		private GridPane gridPane;
		private Text title;
			private Text from;
			private ComboBox<Vertex> CB1;
			private Text to;
			private ComboBox<Vertex> CB2;
		private HBox hBox;
			private Button cancel;
			private Button add;
	
	public AddEdgeToRelationStage(List<Vertex> vertices, String relationName) {
		setTitle(TITLE + "\"" + relationName + "\" - Nouvel Arc");
		
		this.vertices = vertices;
		this.relationName = relationName;
		
		buildComposants();
		buildInterface();
		buildEvents();
	}
	
	//	GETTERS
	public String getRelationName() { return relationName; }
	
	public Pair<Vertex, Vertex> getPair() { return pair; }
	
	//	SETTERS
	public void setRelationName(String relationName) { this.relationName = relationName; }
	
	public void setPair(Pair<Vertex, Vertex> pair) { this.pair = pair; }
	
	//	METHODES
	private void buildComposants() {
		getIcons().add(new Image("file:res/gando.png"));
		
		vertex1 = FXCollections.observableArrayList(vertices);
		vertex2 = FXCollections.observableArrayList(vertices);
		
		vBox = new VBox();
		vBox.setPadding(new Insets(10, 10, 10, 10));
			gridPane = new GridPane();
			gridPane.setAlignment(Pos.CENTER);
			gridPane.setHgap(10);
			gridPane.setVgap(10);
			gridPane.setPadding(new Insets(25, 25, 25, 25));
		
		title = new Text("Nouvel arc pour \"" + relationName +"\"");
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
				CB1.getSelectionModel().selectFirst();
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
				CB2.getSelectionModel().select(1);
		hBox = new HBox(10);
		hBox.setAlignment(Pos.BOTTOM_RIGHT);
			cancel = new Button("Annuler");
			add = new Button("Ajouter arc");
	}
	
	private void buildInterface() {
			gridPane.add(title, 0, 0, 2, 1);
			gridPane.add(from, 0, 1);
			gridPane.add(CB1, 1, 1);
			gridPane.add(to, 2, 1);
			gridPane.add(CB2, 3, 1);
			
			hBox.getChildren().addAll(cancel, add);
		
		vBox.getChildren().addAll(gridPane, hBox);
		
		Scene scene = new Scene(vBox);
		this.setScene(scene);
	}
	
	private void buildEvents() {
		add.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				buildPair();
				close();
			}
		});
		cancel.setOnAction(event -> 
			this.fireEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSE_REQUEST)));
		setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				pair = null;
			}
		});
	}
	
	private void buildPair() {
		Vertex vertex1 = CB1.getValue();
		Vertex vertex2 = CB2.getValue();
		
		pair = new Pair<Vertex, Vertex>(vertex1, vertex2);
	}
}
