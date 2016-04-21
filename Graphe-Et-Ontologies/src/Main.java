import fr.fourmond.jerome.ontology.TreeOntology;
import fr.fourmond.jerome.ontology.TreeOntologyException;

public class Main {

	public static void main(String[] args) {
		TreeOntology tree = new TreeOntology();
		
		try {
			tree.readFromFile("../Ontologies/Villes.xml");
		} catch(TreeOntologyException E) {
			E.printStackTrace();
		}
		
		System.out.println(tree);
	}

}
