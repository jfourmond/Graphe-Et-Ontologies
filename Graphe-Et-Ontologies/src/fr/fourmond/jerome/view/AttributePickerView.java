package fr.fourmond.jerome.view;

import fr.fourmond.jerome.framework.Tree;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AttributePickerView extends Stage {
	private static final String title = "Graphe Et Ontologies - Nouveau sommet - Nouvel attribut";
	
	private Tree tree;
	
	private GridPane grid;
		private Text text;
		private ListView<String> attributesView;
			private ObservableList<String> attributesList;
		private TextField attribute;
			private Button add_attributes;
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
		grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(0, 10, 0, 10));
			text = new Text("Dictionnaire");
				attributesList = FXCollections.observableArrayList(tree.dictionnary());
			attributesView = new ListView<>(attributesList);
			attribute = new TextField();
			add_attributes = new Button("Ajouter");
			
			cancel = new Button("Annuler");
			select = new Button("Selectionner");
	}
	
	private void buildInterface() {
		grid.add(text, 0, 0);
		grid.add(attributesView, 0, 1);
		grid.add(attribute, 0, 3);
		grid.add(add_attributes, 1, 3);
		grid.add(cancel, 1, 4);
		grid.add(select, 2, 4);
		
		Scene scene = new Scene(grid);
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
