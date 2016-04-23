import fr.fourmond.jerome.framework.Tree;

public class Main {

	public static void main(String[] args) {
		Tree tree = new Tree();
		
		try {
			tree.readFromFile("../Ontologies/Villes.xml");
		} catch(Exception E) {
			E.printStackTrace();
		}
		
		System.out.println(tree);
	}

}
