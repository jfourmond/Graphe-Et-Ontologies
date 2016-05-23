package fr.fourmond.jerome.view;

import fr.fourmond.jerome.framework.Tree;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AttributePickerView extends Stage {
	private static final String title = "Graphe Et Ontologies - Nouvel attribut";
	
	private Tree tree;
	
	private VBox vBox;
		private GridPane gridPane;
			private Text text;
			private ListView<String> attributesView;
				private ObservableList<String> attributesList;
			private TextField attribute;
				private Button add_attributes;
		private HBox hBox;
			private Button cancel;
			private Button select;
		
			
	private String attributePicked;
			
	public AttributePickerView(Tree tree) {
		setTitle(title);
		
		this.tree = tree;
		
		buildComposants();
		buildInterface();
		buildEvents();
	}
	
	//	GETTERS
	public String getAttributePicked() { return attributePicked; }
	
	private void buildComposants() {
		vBox = new VBox(10);
		vBox.setPadding(new Insets(10, 10, 10, 10));
			gridPane = new GridPane();
				gridPane.setHgap(10);
				gridPane.setVgap(10);
				gridPane.setPadding(new Insets(0, 10, 0, 10));
				text = new Text("Dictionnaire");
					attributesList = FXCollections.observableArrayList(tree.dictionnary());
				attributesView = new ListView<>(attributesList);
				attribute = new TextField();
				add_attributes = new Button("Ajouter");
			hBox = new HBox(10);
			hBox.setAlignment(Pos.BOTTOM_RIGHT);
				cancel = new Button("Annuler");
				select = new Button("Selectionner");
	}
	
	private void buildInterface() {
			gridPane.add(text, 0, 0);
			gridPane.add(attributesView, 0, 1);
			gridPane.add(attribute, 0, 3);
			gridPane.add(add_attributes, 1, 3);
			
			hBox.getChildren().addAll(cancel, select);
		vBox.getChildren().addAll(gridPane, hBox);
		
		Scene scene = new Scene(vBox);
		this.setScene(scene);
	}

	private void buildEvents() {
		attributesView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				attributePicked = newValue;
			}
		});
		
		add_attributes.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String ch = attribute.getText();
				if(ch != null && !ch.isEmpty()) {
					attributesList.add(ch);
					attributesView.getSelectionModel().select(ch);
					close();
				}
			}
		});
		cancel.setOnAction(event -> 
			this.fireEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSE_REQUEST)));
		select.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				attributePicked = attributesView.getSelectionModel().selectedItemProperty().get();
				close();
			}
		});
		setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				attributePicked = null;
			}
		});
	}
	
}
