
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
		
		CityVertex angers = new CityVertex("Angers", "49000", 147571, 42.7);
		CityVertex nantes = new CityVertex("Nantes", "44000", 294970, 65.19);
		CityVertex cholet = new CityVertex("Cholet", "49300", 56761, 87.47);
		CityVertex chemille = new CityVertex("Chemillé", "49120", 7028, 71.9);
		CityVertex saumur = new CityVertex("Saumur", "49400", 27413, 66.25);
		
		try {
			region.addVertex(angers);
			region.addVertex(nantes);
			region.addVertex(cholet);
			region.addVertex(chemille);
			region.addVertex(saumur);
		} catch (TreeException e) {
			e.printStackTrace();
		}
		
		Edge<CityVertex, Double> AtoN = new Edge<>(angers, nantes, 91.4);
		Edge<CityVertex, Double> AtoC = new Edge<>(angers, cholet, 69.0);
		Edge<CityVertex, Double> AtoCh = new Edge<>(angers, chemille, 45.0);
		Edge<CityVertex, Double> CtoCh = new Edge<>(cholet, chemille, 25.0);
		Edge<CityVertex, Double> NtoC = new Edge<>(nantes, cholet, 69.4);
		Edge<CityVertex, Double> NtoCh = new Edge<>(nantes, chemille, 75.0);
		Edge<CityVertex, Double> StoA= new Edge<>(saumur, angers, 68.2);
		Edge<CityVertex, Double> StoC = new Edge<>(saumur, cholet, 74.2);
		Edge<CityVertex, Double> StoCh = new Edge<>(saumur, chemille, 60.0);
		
		try {
			region.addEdge(AtoN);
			region.addEdge(AtoC);
			region.addEdge(NtoC);
			region.addEdge(AtoCh);
			region.addEdge(NtoCh);
			region.addEdge(CtoCh);
			region.addEdge(StoA);
			region.addEdge(StoC);
			region.addEdge(StoCh);
		} catch(TreeException e) {
			e.printStackTrace();
		}
		
		System.out.println(region);
		
		launch(args);
	}
}
