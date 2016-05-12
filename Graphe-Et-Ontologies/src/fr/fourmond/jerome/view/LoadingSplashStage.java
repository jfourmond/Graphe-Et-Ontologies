package fr.fourmond.jerome.view;

import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.framework.TreeLoader;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoadingSplashStage extends Stage {
	private Tree tree;
	private String filename;
	private TreeLoader loader;
	
	private VBox vBox;
		private Label label;
		private ProgressBar pb;
	
	public LoadingSplashStage(Tree tree, String filename) {
		this.tree = tree;
		this.filename = filename;
		
		buildComposants();
		buildInterface();
		buildEvents();
		
		load();
	}

	public LoadingSplashStage(Tree tree) {
		this.tree = tree;
		this.filename = null;
		
		buildComposants();
		buildInterface();
		buildEvents();
		
		load();
	}

	private void buildComposants() {
		initStyle(StageStyle.TRANSPARENT);
		
		vBox = new VBox(25);
		vBox.setBackground(new Background(new BackgroundFill(Color.DARKCYAN, CornerRadii.EMPTY, Insets.EMPTY)));
			label = new Label("Graphe Et Ontologies");
			pb = new ProgressBar();
				// pb.setProgress(0);
				
			pb.prefWidthProperty().bind(vBox.widthProperty().subtract(20));
	}

	private void buildInterface() {
		vBox.getChildren().addAll(label, pb);
			vBox.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(vBox, 300, 200, Color.BLUE);
		
		this.setScene(scene);
		this.show();
	}

	private void buildEvents() {
		// TODO Auto-generated method stub
		
	}
	
	private void load() {
		if(filename == null) {
			new AppStage(tree);
			close();
		} else {
			loader = new TreeLoader(tree, filename);
			loader.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
				@Override
				public void handle(WorkerStateEvent event) {
					tree = loader.getTree();
				}
			});
			loader.setOnFailed(new EventHandler<WorkerStateEvent>() {
				@Override
				public void handle(WorkerStateEvent event) {
					showError(loader.getException().getMessage());
				}
			});
			pb.progressProperty().bind(loader.progressProperty());
			Thread thread = new Thread(loader);
			thread.start();
			try {
				thread.join();
				System.out.println("YOLO");
				new AppStage(tree);
			} catch (Exception e) {
				e.printStackTrace();
				showError(e.getMessage());
			}
		}
	}
	
	private void showError(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erreur");
		alert.setHeaderText("Erreur dans l'ouverture du fichier");
		alert.initStyle(StageStyle.UTILITY);
		alert.setContentText(message);
		alert.showAndWait();
		
		close();
	}
}
