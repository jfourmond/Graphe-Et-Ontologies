package fr.fourmond.jerome.view;

import fr.fourmond.jerome.framework.Tree;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OntologyStage extends Stage {
	private Tree ontology;
	
	private TreeView treeView;
	
	public OntologyStage(Tree ontology) {
		this.setTitle("Graphe Et Ontologies");
		this.ontology = ontology;
		
		treeView = new TreeView(this.ontology);
		
		this.setScene(new Scene(treeView));
		this.show();
	}
}
