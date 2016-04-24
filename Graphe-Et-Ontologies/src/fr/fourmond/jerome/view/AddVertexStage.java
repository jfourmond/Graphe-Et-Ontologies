package fr.fourmond.jerome.view;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import fr.fourmond.jerome.framework.Pair;
import fr.fourmond.jerome.framework.Tree;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddVertexStage extends Stage {
	
	private Tree tree;
	
	private GridPane gridPane;
	private Text title;
	private Label ID;
		private TextField IDField;
	private Button addAttribute;
	private Button add;
		
	private Set<String> attributes;
	private List<Pair<Label, TextField>> attributesView;
		
	private int currentRow;
	private int currentCol;
	
	public AddVertexStage(Tree tree) {
		this.setTitle("Graphe Et Ontologies - Nouveau sommet");
		this.tree = tree;
		
		attributes = new HashSet<>();
		attributesView = new ArrayList<>();
		
		buildComposants();
		buildInterface();
		buildEvents();
		
		this.show();
	}
	
	private void buildComposants() {
		attributesView.clear();
		
		gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(25, 25, 25, 25));
		
		title = new Text("Nouveau sommet");
		ID = new Label("Identifiant");
		IDField = new TextField();
		addAttribute = new Button("Ajouter attribut");
		add = new Button("Ajouter sommet");
		
		// Construction des attributs
		for(String attribute : attributes) {
			Label label = new Label(attribute);
			TextField textField = new TextField();
			Pair<Label, TextField> pair = new Pair<Label, TextField>(label, textField);
			attributesView.add(pair);
		}
	}
	
	private void buildInterface() {
		gridPane.add(title, 0, 0, 2, 1);
		gridPane.add(ID, 0, 1);
		gridPane.add(IDField, 1, 1);
		
		currentRow = 2;
		currentCol = 0;
		
		for(Pair<Label, TextField> pair : attributesView) {
			gridPane.add(pair.getFirst(), currentCol, currentRow);
			currentCol++;
			gridPane.add(pair.getSecond(), currentCol, currentRow);
			currentRow++;
			currentCol = 0;
		}
		
		gridPane.add(addAttribute, 1, currentRow+1);
		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(add);
		gridPane.add(add, 0, currentRow+2);
		
		Scene scene = new Scene(gridPane, 400, 300);
		this.setScene(scene);
	}
	
	private void buildEvents() {
		addAttribute.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TextInputDialog dialog = new TextInputDialog();
				dialog.setTitle("Graphe Et Ontologies - Nouveau sommet - Nouvel attribut");
				dialog.setHeaderText(null);
				dialog.setContentText("Nom de l'attribut : ");
				dialog.initStyle(StageStyle.UTILITY);
				
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent()){
					String s = result.get();
					System.out.println("Attribut : " + s);
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Erreur");
					alert.initStyle(StageStyle.UTILITY);
					alert.setHeaderText(null);
					if(s.isEmpty()) {
						alert.setContentText("Aucun attribut saisi.");
						alert.showAndWait();
					} else if(!attributes.add(s)) {
						alert.setContentText("L'attribut existe déjà.");
						alert.showAndWait();
					} else {
						gridPane.add(new Label(s), currentCol, currentRow);
						currentCol++;
						gridPane.add(new TextField(), currentCol, currentRow);
						currentRow++;
						currentCol = 0;
					}
				}
			}
		});
		add.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Construire le sommet et l'ajouter à l'arbre
				System.err.println("NON IMPLEMENTE");
			}
		});
	}
}
