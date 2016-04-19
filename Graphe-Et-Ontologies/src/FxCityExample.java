
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		
		List<Edge<CityVertex, Double>> edges = new ArrayList<>();
		
		CityVertex angers = new CityVertex("Angers", "49000", 147571, 42.7);
		CityVertex nantes = new CityVertex("Nantes", "44000", 294970, 65.19);
		CityVertex cholet = new CityVertex("Cholet", "49300", 56761, 87.47);
		CityVertex chemille = new CityVertex("Chemillé", "49120", 7028, 71.9);
		CityVertex saumur = new CityVertex("Saumur", "49400", 27413, 66.25);
		CityVertex leMans = new CityVertex("Le Mans", "72000", 142626, 52.81);
		CityVertex laRocheSurYon = new CityVertex("La Roche sur Yon", "85000", 52732, 87.52);
		
		try {
			region.addVertex(angers);
			region.addVertex(nantes);
			region.addVertex(cholet);
			region.addVertex(chemille);
			region.addVertex(saumur);
			region.addVertex(leMans);
			region.addVertex(laRocheSurYon);
		} catch (TreeException e) {
			e.printStackTrace();
		}
		
		edges.add(new Edge<>(angers, nantes, 91.4));
		edges.add(new Edge<>(angers, cholet, 69.0));
		edges.add(new Edge<>(angers, chemille, 45.0));
		edges.add(new Edge<>(angers, leMans, 101.0));
		edges.add(new Edge<>(cholet, chemille, 25.0));
		edges.add(new Edge<>(cholet, laRocheSurYon, 74.9));
		edges.add(new Edge<>(nantes, cholet, 69.4));
		edges.add(new Edge<>(nantes, chemille, 75.0));
		edges.add(new Edge<>(nantes, laRocheSurYon, 69.1));
		edges.add(new Edge<>(saumur, angers, 68.2));
		edges.add(new Edge<>(saumur, cholet, 74.2));
		edges.add(new Edge<>(saumur, chemille, 60.0));
		edges.add(new Edge<>(saumur, leMans, 95.3));
		
		try {
			region.addEdges(edges);
		} catch(TreeException e) {
			e.printStackTrace();
		}
		
		System.out.println(region);
		
		launch(args);
	}
}
