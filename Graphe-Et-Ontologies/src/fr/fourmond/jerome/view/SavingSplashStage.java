package fr.fourmond.jerome.view;

import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.framework.TreeSaver;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SavingSplashStage extends Stage {
	private Tree tree;
	private TreeSaver saver;
	
	private Scene scene;
	
	private VBox vBox;
		private Label label;
		private ProgressBar pb;

	public SavingSplashStage(Tree tree) {
		this.tree = tree;
		
		buildComposants();
		buildInterface();
		buildEvents();
		
		save();
	}

	private void buildComposants() {
		initStyle(StageStyle.TRANSPARENT);
		
		vBox = new VBox(25);
		vBox.setBackground(new Background(new BackgroundFill(Color.CRIMSON, CornerRadii.EMPTY, Insets.EMPTY)));
			label = new Label("Graphe Et Ontologies");
			pb = new ProgressBar();
				// pb.setProgress(0);
				
			pb.prefWidthProperty().bind(vBox.widthProperty().subtract(20));
			
	}

	private void buildInterface() {
		vBox.getChildren().addAll(label, pb);
			vBox.setAlignment(Pos.CENTER);
		
		scene = new Scene(vBox, 300, 200, Color.BLUE);
		
		this.setScene(scene);
		this.show();
	}

	private void buildEvents() { /* TODO NO EVENTS THEORETICALLY */ }
	
	private void save() {
		if(tree.getFile() == null) {
			System.out.println("Need FileChooser");
		} else {
			saver = new TreeSaver(tree);
			saver.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
				@Override
				public void handle(WorkerStateEvent event) { close(); }
			});
			saver.setOnFailed(new EventHandler<WorkerStateEvent>() {
				@Override
				public void handle(WorkerStateEvent event) {
					showError(saver.getException().getMessage());
				}
			});
			pb.progressProperty().bind(saver.progressProperty());
			Thread thread = new Thread(saver);
			thread.start();
		}
	}
	
	private void showError(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erreur");
		alert.setHeaderText("Erreur dans l'enregistrement du fichier");
		alert.initStyle(StageStyle.UTILITY);
		alert.setContentText(message);
		alert.showAndWait();
		close();
	}
}
