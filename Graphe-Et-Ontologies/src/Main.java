import javax.swing.UIManager;

import fr.fourmond.jerome.framework.Edge;
import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.framework.TreeException;
import fr.fourmond.jerome.view.Window;
import fr.fourmond.jerome.view.TreeView;

public class Main {

	public static void main(String[] args) {
		System.out.println("Test...");
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception E) {
			E.printStackTrace();
		}
		
		Tree<DefaultVertex, Integer> t = new Tree<>();
		
		DefaultVertex A = new DefaultVertex(20);
		DefaultVertex B = new DefaultVertex(15);
		DefaultVertex D = new DefaultVertex(32);
		DefaultVertex E = new DefaultVertex(5);
		DefaultVertex F = new DefaultVertex(18);
		DefaultVertex G = new DefaultVertex(78);
		DefaultVertex K = new DefaultVertex(42);
		
		// OtherVertex C = new OtherVertex("hello");
		
		try {
			t.addVertex(A);
			// t.addVertex(A);	ERREUR
			t.addVertex(B);
			// t.add(C);	ERREUR
			t.addVertex(D);
			t.addVertex(E);
			t.addVertex(F);
			t.addVertex(G);
			t.addVertex(K);
			
			t.addEdge(new Edge<DefaultVertex, Integer>(A, B, 10));
			// t.addEdges(new Edge<DefaultVertex, Integer>(A, B, 10));	ERREUR
			// t.addEdges(new Edge<DefaultVertex, Integer>(A, C, 10));	ERREUR
			t.addEdge(new Edge<DefaultVertex, Integer>(A, D, 22));
			t.addEdge(new Edge<DefaultVertex, Integer>(A, E, 44));
			t.addEdge(new Edge<DefaultVertex, Integer>(A, F, 5));
			t.addEdge(new Edge<DefaultVertex, Integer>(G, K, 2));
		} catch(TreeException TE) {
			TE.printStackTrace();
		}
		
		System.out.println(t);
		TreeView<DefaultVertex, Integer> treeView = new TreeView<>(t);
		
		new Window(treeView);
	}

}
