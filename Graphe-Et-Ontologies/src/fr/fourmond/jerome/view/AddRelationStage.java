package fr.fourmond.jerome.view;

import fr.fourmond.jerome.framework.Relation;
import fr.fourmond.jerome.framework.RelationException;
import fr.fourmond.jerome.framework.Tree;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * {@link AddRelationStage} est un {@link Stage} permettant de créer 
 * une {@link Relation} qui sera ajoutée à l'arbre
 * @author jfourmond
 */
public class AddRelationStage extends Stage {

	private final static String TITLE = "Graphe Et Ontologies - Nouvelle relation";
	
	private Tree tree;
	
	private GridPane gridPane;
	private Text title;
		private Label label;
		private TextField relationName;
		private Button add;
	
	public AddRelationStage(Tree tree) {
		this.setTitle(TITLE);
		this.tree = tree;

		buildComposants();
		buildInterface();
		buildEvents();
	}
	
	private void buildComposants() {
		gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(25, 25, 25, 25));
		
		title = new Text("Nouvelle relation");
		label = new Label("Nom");
		relationName = new TextField();
		
		add = new Button("Ajouter relation");
	}
	
	private void buildInterface() {
		gridPane.add(title, 0, 0, 2, 1);
		gridPane.add(label, 0, 1);
		gridPane.add(relationName, 1, 1);
		
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
				alert.setHeaderText("Ajout de la relation impossible.");
				alert.initStyle(StageStyle.UTILITY);
				try {
					Relation relation = getRelation();
					tree.createRelation(relation);
					close();
				} catch(Exception e) {
					alert.setContentText(e.getMessage());
					alert.showAndWait();
				}
			}
		});
	}
	
	private Relation getRelation() throws RelationException {
		return new Relation(relationName.getText());
	}
	
}
