import javax.swing.UIManager;

import fr.fourmond.jerome.framework.Edge;
import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.framework.TreeException;
import fr.fourmond.jerome.view.MainView;
import fr.fourmond.jerome.view.TreeView;

public class Main {

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception E) {
			E.printStackTrace();
		}
		
		Tree<DefaultVertex, Integer> t = new Tree<>();
		
		DefaultVertex A = new DefaultVertex(20);
		DefaultVertex B = new DefaultVertex(15);
		
		OtherVertex C = new OtherVertex("hello");
		
		Edge<DefaultVertex, Integer> Edge1 = new Edge<>(A, B, 10);
		Edge<DefaultVertex, Integer> Edge2 = new Edge<>(A, B, 10);
		
		try {
			t.addVertex(A);
			// t.addVertex(A);	ERREUR
			t.addVertex(B);
			// t.add(C);	ERREUR
			
			t.addEdges(new Edge<DefaultVertex, Integer>(A, B, 10));
			// t.addEdges(new Edge<DefaultVertex, Integer>(A, B, 10));	ERREUR
			// t.addEdges(new Edge<DefaultVertex, Integer>(A, C, 10));	ERREUR
		} catch(TreeException TE) {
			TE.printStackTrace();
		}
		
		System.out.println(t);
		TreeView<DefaultVertex, Integer> treeView = new TreeView<>(t);
		
		new MainView(treeView);
	}

}
