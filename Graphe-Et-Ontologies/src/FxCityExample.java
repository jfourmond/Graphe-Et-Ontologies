
import java.io.File;

import fr.fourmond.jerome.example.CityVertex;
import fr.fourmond.jerome.framework.Edge;
import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.framework.TreeException;
import fr.fourmond.jerome.view.fx.TreeFxView;
import javafx.application.Application;
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
			private MenuItem open;
			private MenuItem quit;
		private Menu edit;
	
	private FileChooser fileChooser;
		
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Graphe Et Ontologies");
		
		treeView = new TreeFxView<>(region);
		
		fileChooser = new FileChooser();
		
		menuBar = new MenuBar();
			menu_file = new Menu("Fichier");
				open = new MenuItem("Ouvrir");
				open.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						file = fileChooser.showOpenDialog(primaryStage);
					}
				});
				quit = new MenuItem("Quitter");
			menu_file.getItems().addAll(open, quit);
			edit = new Menu("Edition");
			
		menuBar.getMenus().addAll(menu_file, edit);
		
		treeView.setTop(menuBar);
				
		primaryStage.setScene(new Scene(treeView));
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		region = new Tree<>();
		
		CityVertex villeA = new CityVertex("Angers", "49000", 147571, 42.7);
		CityVertex villeB = new CityVertex("Nantes", "44000", 294970, 65.19);
		CityVertex villeC = new CityVertex("Cholet", "49300", 56761, 87.47);
		
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
		
		try {
			region.addEdge(AtoB);
			region.addEdge(AtoC);
			region.addEdge(BtoC);
		} catch(TreeException e) {
			e.printStackTrace();
		}
		
		System.out.println(region);
		
		launch(args);
	}
}
