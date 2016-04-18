
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import fr.fourmond.jerome.example.CityVertex;
import fr.fourmond.jerome.framework.Edge;
import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.framework.TreeException;
import fr.fourmond.jerome.view.fx.TreeFxView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FxCityExample extends Application {
	static Tree<CityVertex, Double> region;
	
	static TreeFxView<CityVertex, Double> treeView;
	
	private File file;
	
	private MenuBar menuBar;
		private Menu menu_file;
			private MenuItem item_open;
			private MenuItem item_quit;
		private Menu menu_edit;
			private MenuItem item_ontologie;
	
	private FileChooser fileChooser;
		
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Graphe Et Ontologies");
		
		treeView = new TreeFxView<>(region);
		
		fileChooser = new FileChooser();
		
		menuBar = new MenuBar();
			menu_file = new Menu("Fichier");
				item_open = new MenuItem("Ouvrir");
				item_open.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						File F;
						F = fileChooser.showOpenDialog(primaryStage);
						if(F != null) {
							file = F;
							System.out.println(file);
						}
					}
				});
				item_quit = new MenuItem("Quitter");
				item_quit.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						Platform.exit();
					}
				});
			menu_file.getItems().addAll(item_open, item_quit);
			menu_edit = new Menu("Edition");
				item_ontologie = new MenuItem("Ontologie");
				item_ontologie.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						try {
							if(file != null && file.exists()) {
								Desktop desktop = Desktop.getDesktop();
								desktop.open(file);
							} else System.err.println("File don't exist");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				});
			menu_edit.getItems().add(item_ontologie);
			
		menuBar.getMenus().addAll(menu_file, menu_edit);
		
		treeView.setTop(menuBar);
		
		primaryStage.setScene(new Scene(treeView));
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		region = new Tree<>();
		
		CityVertex villeA = new CityVertex("Angers", "49000", 147571, 42.7);
		CityVertex villeB = new CityVertex("Nantes", "44000", 294970, 65.19);
		CityVertex villeC = new CityVertex("Cholet", "49300", 56761, 87.47);
		CityVertex villeD = new CityVertex("Chemillé", "49120", 7028, 71.9);
		
		try {
			region.addVertex(villeA);
			region.addVertex(villeB);
			region.addVertex(villeC);
		} catch (TreeException e) {
			e.printStackTrace();
		}
		
		Edge<CityVertex, Double> AtoB = new Edge<>(villeA, villeB, 91.4);
		Edge<CityVertex, Double> AtoC = new Edge<>(villeA, villeC, 69.0);
		Edge<CityVertex, Double> BtoC = new Edge<>(villeB, villeC, 69.4);
		Edge<CityVertex, Double> AtoD = new Edge<>(villeA, villeD, 45.0);
		Edge<CityVertex, Double> BtoD = new Edge<>(villeB, villeD, 75.0);
		Edge<CityVertex, Double> CtoD = new Edge<>(villeC, villeD, 25.0);
		
		
		try {
			region.addEdge(AtoB);
			region.addEdge(AtoC);
			region.addEdge(BtoC);
			region.addEdge(AtoD);
			region.addEdge(BtoD);
			region.addEdge(CtoD);
		} catch(TreeException e) {
			e.printStackTrace();
		}
		
		System.out.println(region);
		
		launch(args);
	}
}
