package fr.fourmond.jerome.view;

import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import fr.fourmond.jerome.framework.Pair;
import fr.fourmond.jerome.framework.Relation;
import fr.fourmond.jerome.framework.Tree;
import fr.fourmond.jerome.framework.TreeException;
import fr.fourmond.jerome.framework.Vertex;
import javafx.concurrent.Task;

/**
 * {@link TreeSaver} est une {@link Task} permettant
 * de sauvegarder un {@link Tree}
 * @author jfourmond
 */
public class TreeSaver extends Task<Boolean> {
	private static final String INDEX = "IndexSource";
	private static final String ENTREE = "ENTREE";
		private static final String ID = "id";
	private static final String RELATION = "RELATION";
		private static final String NOM = "nom";
	private static final String LIEN = "LIEN";
	
	private Tree tree;
	
	public TreeSaver(Tree tree) {
		this.tree = tree;
	}
	
	@Override
	protected Boolean call() throws Exception {
		if(tree.getFile() == null) {
			updateMessage("Sauvegarde échouée");
			throw new TreeException("Aucun fichier associé à l'arbre.");
		}
		
		Document documentJDOM;
		// Racine du document
		Element racine = new Element(INDEX);
		
		int i=0;
		updateMessage("Sauvegarde...");
		for(Vertex vertex : tree.getVertices()) {
			// Ajout d'une entrée
			Element entree = new Element(ENTREE);
			racine.addContent(entree);
			// Ajout de l'id
			Attribute id = new Attribute(ID, vertex.getID());
			entree.setAttribute(id);
			// Ajout de tous les attributs
			Map<String, String> attributes = vertex.getAttributes();
			for(Entry<String, String> entry : attributes.entrySet()) {
				Attribute attribute = new Attribute(entry.getKey(), entry.getValue());
				entree.setAttribute(attribute);
			}
			// Gestion des relations
			for(Relation r : tree.getRelations()) {
				Element relation = new Element(RELATION);
				entree.addContent(relation);
				// Nom de la relation
				Attribute relationName = new Attribute(NOM, r.getName());
				relation.setAttribute(relationName);
				// Liens de la relation
				List<Pair<Vertex, Vertex>> listPairs = r.getPairs(vertex);
				for(Pair<Vertex, Vertex> pair : listPairs) {
					Element lien = new Element(LIEN);
					relation.addContent(lien);
					lien.setText(pair.getSecond().getID());
				}
			}
			updateProgress(++i, tree.nbVertices());
		}
		
		// Enregistrement
		documentJDOM = new Document(racine);
		
		XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
		sortie.output(documentJDOM, new FileOutputStream(tree.getFile()));
		
		updateMessage("Sauvegarde effectuée");
		
		return true;
	}
}
