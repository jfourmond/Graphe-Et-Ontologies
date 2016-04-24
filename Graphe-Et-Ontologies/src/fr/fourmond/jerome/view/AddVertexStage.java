package fr.fourmond.jerome.view;

import java.util.Map;

import fr.fourmond.jerome.framework.Tree;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddVertexStage extends Stage {
	
	private Tree tree;
	
	private GridPane gridPane;
	private Text title;
	private Label ID;
		private TextField IDField;
	private Button addAttribute;
	private Button add;
		
	private Map<String, String> attributes;
	
	private int currentRow;
	private int currentCol;
	
	public AddVertexStage(Tree tree) {
		this.setTitle("Graphe Et Ontologies - Nouveau sommet");
		this.tree = tree;
		
		gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(25, 25, 25, 25));
		
		title = new Text("Nouveau sommet");
		// sceneTitle.setId("welcome-text");
		gridPane.add(title, 0, 0, 2, 1);	// Ajouter dans la colonne 0 et ligne 0, avec un column span de 2 et un row span de 1
		
		ID = new Label("Identifiant");
		gridPane.add(ID, 0, 1);

		IDField = new TextField();
		gridPane.add(IDField, 1, 1);

		currentRow = 2;
		currentCol = 0;
		
		addAttribute = new Button("Ajouter attribut");
		gridPane.add(addAttribute, 0, currentRow+1);

		add = new Button("Ajouter sommet");
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(add);
		
		gridPane.add(add, 1, currentRow+2);
		
		Scene scene = new Scene(gridPane, 300, 275);
		this.setScene(scene);
		
		this.show();
	}
}
