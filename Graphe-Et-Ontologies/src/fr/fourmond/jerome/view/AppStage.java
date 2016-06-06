package fr.fourmond.jerome.view;

import java.io.File;

import fr.fourmond.jerome.config.Settings;
import fr.fourmond.jerome.framework.Tree;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AppStage extends Stage {
	private Tree tree;
	
	private TreeView treeView;
	
	private FileChooser fileChooser;
	
	public AppStage(Tree tree) {
		this.setTitle("Graphe Et Ontologies");
		this.tree= tree;
		
		treeView = new TreeView(this.tree);
		
		this.setScene(new Scene(treeView, Settings.getWidth(), Settings.getHeight()));
		
		getIcons().add(new Image("file:res/gando.png"));
		
		getScene().widthProperty().addListener(new ChangeListener<Number>() {
			@Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
				try {
					Settings.setWidth(newSceneWidth.doubleValue());
					Settings.saveSettings();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		getScene().heightProperty().addListener(new ChangeListener<Number>() {
			@Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
				try {
					Settings.setHeight(newSceneHeight.doubleValue());
					Settings.saveSettings();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
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
					ButtonType buttonSaveUnder = new ButtonType("Enregistrer sous");
					ButtonType buttonClose = new ButtonType("Fermer");
	
					alert.getButtonTypes().setAll(buttonClose, buttonCancel, buttonSave, buttonSaveUnder);
	
					alert.showAndWait().ifPresent(response -> {
						if (response == buttonSave) {
							// L'application se ferme après avoir sauvegarder
							new SavingSplashStage(tree);
						} else if(response == buttonSaveUnder) {
							File file = fileChooser.showSaveDialog(null);
							if(file != null) {
								tree.setFile(file);
								new SavingSplashStage(tree);
							}
						} else if(response == buttonClose){
							// L'application se ferme
						} else {
							// L'application ne se ferme pas
							event.consume();
						}
					});
				}
			}
		});
	}
}
