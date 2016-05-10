package fr.fourmond.jerome.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import fr.fourmond.jerome.framework.Pair;
import fr.fourmond.jerome.framework.Vertex;
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
 * {@link EditVertexStage} est un {@link Stage} permettant d'éditer
 * un {@link Vertex} et ses différents attributs
 * @author jfourmond
 */
public class EditVertexStage extends Stage {
	private final static String TITLE = "Graphe Et Ontologies - Edition sommet";
	
	private Vertex vertex;
	
	private GridPane gridPane;
		private Text title;
	private Button addAttribute;
	private Button edit;

	private Map<String, String> attributes;
	private List<Pair<Label, TextField>> attributesView;
	
	private int currentRow;
	private int currentCol;
	
	public EditVertexStage(Vertex vertex) {
		setTitle(TITLE);
		this.vertex = vertex;
		
		attributes = vertex.getAttributes();
		
		buildComposants();
		buildInterface();
		buildEvents();
	}
	
	//	GETTERS
	public Vertex getVertex() { return vertex; }
	
	//	SETTERS
	public void setVertex(Vertex vertex) { this.vertex = vertex; }
	
	private void buildComposants() {
		attributesView = new ArrayList<>();
		
		gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(25, 25, 25, 25));
		
		title = new Text("Edition sommet : " + vertex.getID());
		addAttribute = new Button("Ajouter attribut");
		edit = new Button("Editer");
		
		// Construction des attributs
		for(Entry<String, String> attribute : attributes.entrySet()) {
			Label label = new Label(attribute.getKey());
			TextField textField = new TextField(attribute.getValue());
			Pair<Label, TextField> pair = new Pair<Label, TextField>(label, textField);
			attributesView.add(pair);
		}
	}
	
	private void buildInterface() {
		gridPane.add(title, 0, 0, 2, 1);
		
		currentRow = 1;
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
					attributesView.remove(pair);
					gridPane.getChildren().removeAll(label, textField, button);
					currentRow--;
					save();
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
		hbBtn.getChildren().add(edit);
		gridPane.add(edit, 0, currentRow+2);
		
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
				dialog.setTitle("Graphe Et Ontologies - Edition sommet - Nouvel attribut");
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
					} else {
						 if(attributes.containsKey(s)) {
								alert.setContentText("L'attribut existe déjà.");
								alert.showAndWait();
						} else {
							attributes.put(s, "");
							buildComposants();
							buildInterface();
							buildEvents();
						}
					}
				}
			}
		});
		edit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				save();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setHeaderText("Edition du sommet impossible.");
				alert.initStyle(StageStyle.UTILITY);
				try {
					vertex.setAttributes(attributes);
					close();
				} catch (Exception e) {
					alert.setContentText(e.getMessage());
					alert.showAndWait();
				}
			}
		});
	}
	
	private void save() {
		for(Pair<Label, TextField> pair : attributesView) {
			Label label = pair.getFirst();
			TextField textField = pair.getSecond();
			attributes.put(label.getText(), textField.getText());
		}
	}
}
