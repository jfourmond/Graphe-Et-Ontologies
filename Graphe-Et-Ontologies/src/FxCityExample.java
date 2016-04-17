
import fr.fourmond.jerome.example.CityVertex;
import fr.fourmond.jerome.framework.Edge;
import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.framework.TreeException;
import fr.fourmond.jerome.view.fx.TreeFxView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FxCityExample extends Application {
	static Tree<CityVertex, Double> region;
	
	static TreeFxView<CityVertex, Double> treeView;
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Graphe Et Ontologies");
		
		treeView = new TreeFxView<>(region);
		
		
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
