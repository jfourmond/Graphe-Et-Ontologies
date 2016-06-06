package fr.fourmond.jerome.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * {@link EditVertexStage} est un {@link Stage} permettant d'éditer
 * un {@link Vertex} et ses différents attributs
 * @author jfourmond
 */
public class EditVertexStage extends Stage {
	private final static String TITLE = "Graphe Et Ontologies - Edition sommet";
	
	private Vertex oldVertex;
	private Vertex newVertex;
	
	private Tree tree;
	
	private VBox vBox;
		private GridPane gridPane;
			private Text title;
			private Label name;
				private TextField nameField;
		private Button addAttribute;
	private HBox hBox;
		private Button cancel;
		private Button edit;

	private String textName;
	private Map<String, String> attributes;
	private List<Pair<Label, TextField>> attributesView;
	
	private int currentRow;
	private int currentCol;
	
	public EditVertexStage(Vertex vertex, Tree tree) throws VertexException {
		setTitle(TITLE);
		
		this.oldVertex = vertex;
		this.newVertex = new Vertex(this.oldVertex);
		
		this.tree = tree;
		
		textName = this.newVertex.getName();
		attributes = this.newVertex.getAttributes();
		
		buildComposants();
		buildInterface();
		buildEvents();
	}
	
	//	GETTERS
	public Vertex getOldVertex() { return oldVertex; }
	
	public Vertex getNewVertex() { return newVertex; }
	
	//	SETTERS
	public void setOldVertex(Vertex oldVertex) { this.oldVertex = oldVertex; }
	
	public void setNewVertex(Vertex newVertex) { this.newVertex = newVertex; }
	
	//	METHODES
	private void buildComposants() {
		getIcons().add(new Image("file:gando.png"));
		
		attributesView = new ArrayList<>();
		
		vBox = new VBox();
		vBox.setPadding(new Insets(10, 10, 10, 10));
			gridPane = new GridPane();
			gridPane.setAlignment(Pos.CENTER);
			gridPane.setHgap(10);
			gridPane.setVgap(10);
			gridPane.setPadding(new Insets(25, 25, 25, 25));
		
		title = new Text("Edition sommet : " + oldVertex.getID());
			name = new Label("Nom");
			nameField = new TextField(textName);
		addAttribute = new Button("Ajouter attribut");
		
		hBox = new HBox(10);
		hBox.setAlignment(Pos.BOTTOM_RIGHT);
			cancel = new Button("Annuler");
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
			gridPane.add(name, 0, 1);
			gridPane.add(nameField, 1, 1);
			
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
			hBox.getChildren().addAll(cancel, edit);
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
					} else if(attributes.containsKey(result)) {
						alert.setContentText("L'attribut existe déjà.");
						alert.showAndWait();
					} else {
						attributes.put(result, "");
						buildComposants();
						buildInterface();
						buildEvents();
					}
				}
			}
		});
		edit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				edit();
			}
		});
		nameField.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println(nameField.getText());
				newVertex.setName(nameField.getText());
			}
		});
		nameField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode().equals(KeyCode.ENTER)) { edit(); }
			}
		});
		cancel.setOnAction(event -> 
			this.fireEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSE_REQUEST)));
		setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				newVertex = null;
			}
		});
	}
	
	private void save() {
		textName = nameField.getText();
		for(Pair<Label, TextField> pair : attributesView) {
			Label label = pair.getFirst();
			TextField textField = pair.getSecond();
			attributes.put(label.getText(), textField.getText());
		}
	}
	
	private void edit() {
		save();
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erreur");
		alert.setHeaderText("Edition du sommet impossible.");
		alert.initStyle(StageStyle.UTILITY);
		try {
			newVertex.setName(textName);
			newVertex.setAttributes(attributes);
			close();
		} catch (Exception e) {
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}
}
