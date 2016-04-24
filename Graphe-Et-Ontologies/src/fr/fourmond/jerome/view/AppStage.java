package fr.fourmond.jerome.view;

import fr.fourmond.jerome.framework.Tree;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppStage extends Stage {
	private Tree tree;
	
	private TreeView treeView;
	
	public AppStage(Tree tree) {
		this.setTitle("Graphe Et Ontologies");
		this.tree= tree;
		
		treeView = new TreeView(this.tree);
		
		this.setScene(new Scene(treeView));
		this.show();
	}
}
