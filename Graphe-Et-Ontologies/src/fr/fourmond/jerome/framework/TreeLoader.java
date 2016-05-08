package fr.fourmond.jerome.framework;

import java.io.File;
import java.io.FileNotFoundException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javafx.concurrent.Task;

/**
 * {@link TreeLoader} est une {@link Task} permettant de
 * charger un arbre à partir d'un fichier XML bien formé (adéquat au programme)
 * @author jfourmond
 */
public class TreeLoader extends Task<Boolean> implements ErrorHandler{
	private static final String INDEX = "IndexSource";
	private static final String ENTREE = "ENTREE";
		private static final String ID = "id";
	private static final String RELATION = "RELATION";
		private static final String NOM = "nom";
	private static final String LIEN = "LIEN";
	
	private Tree tree;
	
	private Document document;
	private DocumentBuilderFactory domFactory;
	private DocumentBuilder domBuilder;
	private static String vertexKey;
	
	public TreeLoader(Tree tree) {
		this.tree = tree;
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
		
		domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setValidating(true);
		
		domBuilder = domFactory.newDocumentBuilder();
		domBuilder.setErrorHandler(this);
		
		document = domBuilder.parse(file);
		document.getDocumentElement().normalize();
		
		// buildVertices();
		NodeList nList = document.getElementsByTagName(ENTREE);
		int indexSize = nList.getLength() * 2;
		int index = 0;
		updateProgress(index, indexSize);
		for(int i=0 ; i<nList.getLength() ; i++) {
			Node node = nList.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				vertexKey = element.getAttribute(ID); 					// La clé du sommet actuel
				tmpTree.createVertex(vertexKey);								// Création du sommet actuel
				if (node.hasAttributes()) {								// Recherche de ses autres attributs
					NamedNodeMap nodeMap = node.getAttributes();		// Récupération de ses attributs
					for(int j=0; j<nodeMap.getLength(); j++) {
						Node attribute = nodeMap.item(j);
						String name = attribute.getNodeName();
						if(!name.equals(ID)) {
							String value = attribute.getNodeValue();
							tmpTree.addAttribute(vertexKey, name, value);		// Ajout des attributs au sommet actuel
						}
					}
				}
			}
			updateProgress(++index, indexSize);
		}
		
		// buildRelations();
		String vertex1ID;
		NodeList entreeList = document.getElementsByTagName(ENTREE);
		for(int i=0 ; i<entreeList.getLength() ; i++) {							// Pour chaque Entrée
			Node inode = entreeList.item(i);
			if(inode.getNodeType() == Node.ELEMENT_NODE) {
				Element ielement = (Element) inode;
				vertex1ID = ielement.getAttribute(ID); 							// La clé du sommet actuel
				NodeList relationList = ielement.getElementsByTagName(RELATION);
				for(int j=0 ; j<relationList.getLength() ; j++) {				// Pour chaque Relation
					Node jnode = relationList.item(j);
					if(jnode.getNodeType() == Node.ELEMENT_NODE) {
						Element jelement = (Element) jnode;
						// Récupération du nom de la relation
						String relationName = jelement.getAttribute(NOM);
						if(!tmpTree.containsRelation(relationName)) {					// Si la relation n'existe pas,
							tmpTree.createRelation(relationName);						// on la crée
						}
						NodeList lienList = jelement.getElementsByTagName(LIEN);
						for(int k=0 ; k<lienList.getLength() ; k++) {			// Pour chaque lien
							Node knode = lienList.item(k);
							if(knode.getNodeType() == Node.ELEMENT_NODE) {
								String value = knode.getTextContent();
								if(!value.isEmpty()) {
									if(tmpTree.isID(value)) {
										tmpTree.addPair(relationName, vertex1ID, value);
									} else {
										String vertex2ID = tmpTree.getVertexID(value);
										tmpTree.addPair(relationName, vertex1ID, vertex2ID);
									}
								}
							}
						}
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
		System.err.println("Echec du chargement.");
		updateMessage("Echec du chargement.");
	}
	
	@Override
	public void error(SAXParseException exception) throws SAXException {
		System.out.println("Warning : " + exception.getMessage());
	}

	@Override
	public void fatalError(SAXParseException exception) throws SAXException {
		System.err.println("FATAL : " + exception.getMessage());
		throw exception;
	}

	@Override
	public void warning(SAXParseException exception) throws SAXException {
		System.err.println("ERROR : " + exception.getMessage());
		throw exception;
	}
}
