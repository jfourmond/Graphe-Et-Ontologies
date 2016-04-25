package fr.fourmond.jerome.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import fr.fourmond.jerome.framework.Pair;
import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.framework.TreeException;
import fr.fourmond.jerome.framework.Vertex;
import fr.fourmond.jerome.framework.VertexException;
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

/**
 * {@link AddVertexStage} est un {@link Stage} permettant de créer
 * un {@link Vertex}, et ses différents attributs, qui sera ajouter à l'arbre
 * @author jfourmond
 */
public class AddVertexStage extends Stage {
	
	private final static String TITLE = "Graphe Et Ontologies - Nouveau sommet";
	
	private Tree tree;
	
	private GridPane gridPane;
	private Text title;
	private Label ID;
		private TextField IDField;
	private Button addAttribute;
	private Button add;
		
	private Set<String> attributes;
	private List<Pair<Label, TextField>> attributesView;
	
	private String text_id;
	private Map<String, String> text_attributes;
	
	private int currentRow;
	private int currentCol;
	
	public AddVertexStage(Tree tree) {
		this.setTitle(TITLE);
		this.tree = tree;
		
		attributes = new HashSet<>();
		attributesView = new ArrayList<>();
		text_attributes = new HashMap<>();
		
		buildComposants();
		buildInterface();
		buildEvents();
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
		IDField = new TextField(text_id);
		addAttribute = new Button("Ajouter attribut");
		add = new Button("Ajouter sommet");
		
		// Construction des attributs
		for(String attribute : attributes) {
			Label label = new Label(attribute);
			String value = getValue(attribute);
			TextField textField = new TextField(value);
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
			Label label = pair.getFirst();
			TextField textField = pair.getSecond();
			Button button = new Button("Supprimer");
			button.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					String attribute = label.getText();
					attributes.remove(attribute);
					text_attributes.remove(attribute);
					gridPane.getChildren().removeAll(label, textField, button);
					currentRow--;
				}
			});
			textField.requestFocus();
			gridPane.add(label, currentCol++, currentRow);
			gridPane.add(textField, currentCol++, currentRow);
			gridPane.add(button, currentCol++, currentRow);
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
		
		// For focus
		if(!attributesView.isEmpty()) {
			Pair<Label, TextField> p = attributesView.get(attributesView.size()-1);
			if(p != null)
				p.getSecond().requestFocus();
		}
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
						saveBeforeBuild();
						buildComposants();
						buildInterface();
						buildEvents();
					}
				}
			}
		});
		add.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				saveBeforeBuild();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setHeaderText("Ajout du sommet impossible.");
				try {
					Vertex vertex = getVertex();
					tree.createVertex(vertex);
					close();
				} catch (Exception e) {
					alert.setContentText(e.getMessage());
					alert.showAndWait();
				}
			}
		});
	}
	
	private void saveBeforeBuild() {
		text_id = IDField.getText();
		for(Pair<Label, TextField> pair : attributesView) {
			Label label = pair.getFirst();
			TextField textField = pair.getSecond();
			System.out.println(label.getText() + " = " + textField.getText());
			text_attributes.put(label.getText(), textField.getText());
		}
	}
	
	private String getValue(String attribute) {
		String ch = "";
		if(text_attributes.containsKey(attribute))
			ch = text_attributes.get(attribute);
		return ch;
	}
	
	private Vertex getVertex() throws VertexException {
		Vertex vertex = new Vertex(text_id);
		for(Entry<String, String> entry : text_attributes.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			vertex.add(key);
			vertex.set(key, value);
		}
		return vertex;
	}
}
