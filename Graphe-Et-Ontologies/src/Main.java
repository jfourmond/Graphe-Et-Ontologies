import fr.fourmond.jerome.ontology.TreeOntology;
import fr.fourmond.jerome.ontology.TreeOntologyException;

public class Main {

	public static void main(String[] args) {
		TreeOntology tree = new TreeOntology();
			
		System.out.println(tree);
		
		try {
			tree.readFromFile("../Ontologies/Index327.dtd");
		} catch(TreeOntologyException E) {
			E.printStackTrace();
		}
		
		System.out.println(tree);
		
	}

}
