package fr.fourmond.jerome.framework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import javafx.concurrent.Task;

/**
 * {@link TreeLoader} est une {@link Task} permettant de
 * charger un arbre à partir d'un fichier XML bien formé (adéquat au programme)
 * @author jfourmond
 */
public class TreeLoader extends Task<Boolean> {
	private static final String INDEX = "IndexSource";
	private static final String ENTREE = "ENTREE";
		private static final String ID = "id";
	private static final String RELATION = "RELATION";
		private static final String NOM = "nom";
	private static final String LIEN = "LIEN";
	
	private Tree tree;
	
	private SAXBuilder saxBuilder;
	
	private static String vertexKey;
	
	public TreeLoader(Tree tree) {
		this.tree = tree;
	}
	
	public TreeLoader(Tree tree, String filename) {
		File file = new File(filename);
		this.tree = tree;
		this.tree.setFile(file);
	}
	
	public TreeLoader(Tree tree, File file) {
		this.tree = tree;
		this.tree.setFile(file);
	}
	
	public Tree getTree() { return tree; }
	
	public void setTree(Tree tree) { this.tree = tree; }
	
	@Override
	protected Boolean call() throws Exception {
		Tree tmpTree = new Tree();
		
		File file = tree.getFile();
		if(file == null) {
			System.err.println("Echec du chargement");
			updateMessage("Echec du chargement");
			throw new TreeException("Aucun fichier associé à  l'arbre");
		}
		if(!file.exists()) {
			throw new FileNotFoundException("Le fichier n'existe pas.");
		}
		
		updateMessage("Chargement en cours...");
		
		// TODO Validation (avec SAXBuilder ?)
		saxBuilder = new SAXBuilder();
		Document document = saxBuilder.build(tree.getFile());
		
		Element racine = document.getRootElement();
		
		List<Element> entries =  racine.getChildren(ENTREE);
		int indexSize = entries.size() * 2;
		int index = 0;
		
		updateProgress(index, indexSize);
		
		// Création des sommets
		for(Element courant : entries) {
			vertexKey = courant.getAttributeValue(ID);							// Clé du sommet actuel
			tmpTree.createVertex(vertexKey);									// Création du sommet actuel
			for(Attribute attribute : courant.getAttributes()) {		// Récupération de ses attributs
				if(!attribute.getName().equals(ID)) {
					String name = attribute.getName();							// Identifiant de l'attribut actuel
					String value = attribute.getValue();						// Valeur de l'attribut actuel
					tmpTree.addAttribute(vertexKey, name, value);				// Ajout de l'attribut actuel au sommet actuel
				}
			}
			updateProgress(++index, indexSize);
		}
		
		String vertex1;
		// Création des relations
		for(Element courant : entries) {
			vertex1 = courant.getAttributeValue(ID);							// Clé du sommet actuel
			List<Element> relations = courant.getChildren(RELATION);	// Récupération de ses relations
			for(Element relation : relations) {						
				String name = relation.getAttributeValue(NOM);					// Récupération du nom de la relation
				if(!tmpTree.containsRelation(name)) {							// Si la relation n'existe pas,
					tmpTree.createRelation(name);								// 		on la crée
				}
				List<Element> liens = relation.getChildren(LIEN);		// Récupération des liens de la relation
				for(Element lien : liens) {
					String vertex2 = lien.getValue();
					if(tmpTree.isID(vertex2)) {
						tmpTree.addPair(name, vertex1, vertex2);
					} else {
						throw new TreeLoaderException("Le terme précisé n'est pas un identifiant de sommet.");
					}
				}
			}
			updateProgress(++index, indexSize);
		}
		
		tree = tmpTree;
		return true;
	}
	
	@Override
	protected void succeeded() {
		super.succeeded();
		updateMessage("Chargement terminé.");
	}
	
	
	@Override
	protected void cancelled() {
		super.cancelled();
		System.err.println("Chargement annulé.");
		updateMessage("Chargement annulé.");
	}
	
	@Override
	protected void failed() {
		super.failed();
		getException().printStackTrace();
		updateMessage("Echec du chargement.");
	}
}
