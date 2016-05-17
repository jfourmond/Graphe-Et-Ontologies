package fr.fourmond.jerome.framework;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import javafx.concurrent.Task;

/**
 * {@link TreeSaver} est une {@link Task} permettant
 * de sauvegarder un {@link Tree} dans un fichier XML
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
	
	public TreeSaver(Tree tree, File file) {
		this.tree = tree;
		this.tree.setFile(file);
	}
	
	@Override
	protected Boolean call() throws Exception {
		// TODO Genérer une DTD ou une XSD correspondante au fichier
		File file = tree.getFile();
		
		if(file == null) {
			System.err.println("Echec du chargement");
			updateMessage("Sauvegarde échouée");
			throw new TreeException("Aucun fichier associé à l'arbre.");
		}
		
		if(!file.exists())
			file.createNewFile();
		
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
		sortie.output(documentJDOM, new FileOutputStream(file));
		
		// Génération d'une XSD
		// Process proc = Runtime.getRuntime().exec("java -jar lib//trang.jar " + file.getAbsolutePath() + " " + file.getName() + ".xsd");
		
		updateMessage("Sauvegarde effectuée");
		
		return true;
	}
	
	@Override
	protected void succeeded() {
		super.succeeded();
		updateMessage("Sauvegarde terminée.");
	}
	
	
	@Override
	protected void cancelled() {
		super.cancelled();
		System.err.println("Sauvegardeannulé.");
		updateMessage("Sauvegarde annulé.");
	}
	
	@Override
	protected void failed() {
		super.failed();
		getException().printStackTrace();
		updateMessage("Echec de la sauvegarde.");
	}
}
