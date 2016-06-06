package fr.fourmond.jerome.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import fr.fourmond.jerome.config.Settings;
import fr.fourmond.jerome.framework.Pair;
import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.framework.Vertex;
import fr.fourmond.jerome.framework.VertexException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * {@link AddVertexStage} est un {@link Stage} permettant de créer
 * un {@link Vertex}, et ses différents attributs, qui sera ajouter à l'arbre
 * @author jfourmond
 */
public class AddVertexStage extends Stage {
	
	private final static String TITLE = "Graphe Et Ontologies - Nouveau sommet";
	
	private Tree tree;
	
	private Vertex vertex;
	
	private VBox vBox;
		private GridPane gridPane;
			private Text title;
			private Label ID;
				private TextField IDField;
			private Label Name;
				private TextField nameField;
			private Button addAttribute;
		private HBox hBox;
			private Button cancel;
			private Button add;
		
	private Set<String> attributes;
	private List<Pair<Label, TextField>> attributesView;
	
	private String text_id;
	private String text_name;
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
	
	// 	GETTERS
	public Vertex getVertex() { return vertex; }
	
	//	SETTERS
	public void setVertex(Vertex vertex) { this.vertex = vertex; }
	
	private void buildComposants() {
		getIcons().add(new Image("file:res/gando.png"));
		
		attributesView.clear();
		
		if(Settings.isAutoId())
			text_id = String.valueOf(tree.currentID());
		vBox = new VBox();
		vBox.setPadding(new Insets(10, 10, 10, 10));
			gridPane = new GridPane();
			gridPane.setAlignment(Pos.CENTER);
			gridPane.setHgap(10);
			gridPane.setVgap(10);
			gridPane.setPadding(new Insets(25, 25, 25, 25));
				title = new Text("Nouveau sommet");
				ID = new Label("Identifiant");
				IDField = new TextField(text_id);
					if(Settings.isAutoId())
						IDField.setDisable(true);
				Name = new Label("Nom");
				nameField = new TextField(text_name);
				addAttribute = new Button("Ajouter attribut");
			hBox = new HBox(10);
			hBox.setAlignment(Pos.BOTTOM_RIGHT);
				cancel = new Button("Annuler");
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
		gridPane.add(Name, 0, 2);
		gridPane.add(nameField, 1, 2);
		
		currentRow = 3;
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

			hBox.getChildren().addAll(cancel, add);
		vBox.getChildren().addAll(gridPane, hBox);
		
		Scene scene = new Scene(vBox);
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
				AttributePickerView apd = new AttributePickerView(tree);
				apd.showAndWait();
				String result = apd.getAttributePicked();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.initStyle(StageStyle.UTILITY);
				alert.setHeaderText(null);
				if(result != null) {
					result = result.trim().toLowerCase();
					if(result.isEmpty()) {
						alert.setContentText("Aucun attribut saisi.");
						alert.showAndWait();
					} else if(!attributes.add(result)) {
						alert.setContentText("L'attribut existe déjà.");
						alert.showAndWait();
					} else {
						save();
						buildComposants();
						buildInterface();
						buildEvents();
					}
				}
			}
		});
		cancel.setOnAction(event -> 
			this.fireEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSE_REQUEST)));
		add.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				add();
			}
		});
		nameField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode().equals(KeyCode.ENTER)) { add(); }
			}
		});
		setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				vertex = null;
			}
		});
	}
	
	private void save() {
		text_id = IDField.getText();
		text_name = nameField.getText();
		for(Pair<Label, TextField> pair : attributesView) {
			Label label = pair.getFirst();
			TextField textField = pair.getSecond();
			text_attributes.put(label.getText().trim(), textField.getText().trim());
		}
	}
	
	private String getValue(String attribute) {
		String ch = "";
		if(text_attributes.containsKey(attribute))
			ch = text_attributes.get(attribute);
		return ch;
	}
	
	private Vertex buildVertex() throws VertexException {
		Vertex vertex = new Vertex(text_id, text_name);
		for(Entry<String, String> entry : text_attributes.entrySet()) {
			String key = entry.getKey().trim();
			String value = entry.getValue().trim();
			vertex.add(key);
			vertex.set(key, value);
		}
		return vertex;
	}
	
	private void add() {
		save();
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erreur");
		alert.setHeaderText("Ajout du sommet impossible.");
		alert.initStyle(StageStyle.UTILITY);
		try {
			vertex = buildVertex();
			close();
		} catch (Exception e) {
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}
}
