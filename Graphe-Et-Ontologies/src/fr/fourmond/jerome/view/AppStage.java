package fr.fourmond.jerome.view;

import fr.fourmond.jerome.framework.Tree;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AppStage extends Stage {
	private Tree tree;
	
	private TreeView treeView;
	
	public AppStage(Tree tree) {
		this.setTitle("Graphe Et Ontologies");
		this.tree= tree;
		
		treeView = new TreeView(this.tree);
		
		this.setScene(new Scene(treeView));
		
		setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				if(!treeView.saved) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("Graphe Et Ontologies - Attention");
					alert.setHeaderText("Des modifications ont été effectuées sur l'ontologie");
					alert.setContentText("Voulez-vous les conserver ?");
	
					ButtonType buttonCancel = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);
					ButtonType buttonSave = new ButtonType("Enregistrer");
					ButtonType buttonClose = new ButtonType("Fermer");
	
					alert.getButtonTypes().setAll(buttonClose, buttonCancel, buttonSave);
	
					alert.showAndWait().ifPresent(response -> {
						if (response == buttonSave) {
							System.out.println("Faudrait sauvegarder...");
						} else if(response == buttonClose){
							
						} else {
							event.consume();
						}
					});
				}
			}
		});
	}
}
