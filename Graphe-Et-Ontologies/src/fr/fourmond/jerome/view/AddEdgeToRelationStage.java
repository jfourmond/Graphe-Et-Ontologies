package fr.fourmond.jerome.view;

import java.util.ArrayList;
import java.util.List;

import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.framework.Vertex;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * {@link AddEdgeToRelationStage} est un {@link Stage} permettant de créer
 * un arc pour une relation précise, qui sera ajouté à l'arbre
 * @author jfourmond
 */
public class AddEdgeToRelationStage extends Stage {

	private static final String TITLE = "Graphe Et Ontologies - Relation ";
	
	private Tree tree;
	private String relationName;
	
	private List<String> vertices;
	
	private ObservableList<String> vertex1;
	private ObservableList<String> vertex2;
	
	private GridPane gridPane;
	private Text title;
		private Text from;
		private ComboBox<String> CB1;
		private Text to;
		private ComboBox<String> CB2;
	private Button add;
	
	public AddEdgeToRelationStage(Tree tree, String relationName) {
		setTitle(TITLE + relationName + " - Nouvel Arc");
		
		this.tree = tree;
		this.relationName = relationName;
		
		buildComposants();
		buildInterface();
		buildEvents();
	}
	
	private void buildComposants() {
		vertices = new ArrayList<>();
		for(Vertex vertex : tree.getVertices()) {
			vertices.add(vertex.getID());
		}
		
		vertex1 = FXCollections.observableArrayList(vertices);
		vertex2 = FXCollections.observableArrayList(vertices);
		
		gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(25, 25, 25, 25));
		
		title = new Text("Nouvel arc");
			from = new Text(" De ");
				CB1 = new ComboBox<>(vertex1);
				CB1.setValue(vertices.get(0));
			to = new Text(" à ");
				CB2 = new ComboBox<>(vertex2);
				CB2.setValue(vertices.get(1));
		
		add = new Button("Ajouter arc");
	}
	
	private void buildInterface() {
		gridPane.add(title, 0, 0, 2, 1);
		gridPane.add(from, 0, 1);
		gridPane.add(CB1, 1, 1);
		gridPane.add(to, 2, 1);
		gridPane.add(CB2, 3, 1);
		
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(add);
		gridPane.add(add, 1, 3);
		
		Scene scene = new Scene(gridPane, 300, 200);
		this.setScene(scene);
	}
	
	private void buildEvents() {
		add.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setHeaderText("Ajout de l'arc impossible.");
				alert.initStyle(StageStyle.UTILITY);
				try {
					Vertex vertex1 = tree.getVertex(CB1.getValue());
					Vertex vertex2 = tree.getVertex(CB2.getValue());
					tree.addPair(relationName, vertex1, vertex2);
					close();
				} catch (Exception e) {
					alert.setContentText(e.getMessage());
					alert.showAndWait();
				}
			}
		});
	}
}
