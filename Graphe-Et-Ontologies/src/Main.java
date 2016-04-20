import java.util.Set;

import fr.fourmond.jerome.ontology.TreeOntology;
import fr.fourmond.jerome.ontology.TreeOntologyException;

public class Main {

	public static void main(String[] args) {
		TreeOntology tree = new TreeOntology();
		try {
			tree.createVertex("Ville");
			tree.createVertex("Angers");
				tree.addAttribute("Angers", "postalCode", "49000");
				tree.addAttribute("Angers", "population", "147571");
			tree.createVertex("Nantes");
				tree.addAttribute("Nantes", "postalCode", "44000");
				tree.addAttribute("Nantes", "population", "294970");
				
			tree.createRelation("est");
				tree.addEdge("est", "Angers", "Ville");
				tree.addEdge("est", "Nantes", "Ville");
		} catch(TreeOntologyException E) {
			E.printStackTrace();
		}
			
		System.out.println(tree);
	}

}
