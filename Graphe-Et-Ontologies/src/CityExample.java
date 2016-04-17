import javax.swing.UIManager;

import fr.fourmond.jerome.example.CityVertex;
import fr.fourmond.jerome.framework.Edge;
import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.framework.TreeException;
import fr.fourmond.jerome.view.swing.TreeView;
import fr.fourmond.jerome.view.swing.Window;

public class CityExample {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception E) {
			E.printStackTrace();
		}
		Tree<CityVertex, Double> region = new Tree<>();
		
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
		
		TreeView<CityVertex, Double> view = new TreeView<>(region);
		
		new Window(view);
	}

}
