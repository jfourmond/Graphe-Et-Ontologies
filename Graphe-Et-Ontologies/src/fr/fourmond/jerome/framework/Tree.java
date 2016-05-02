package fr.fourmond.jerome.framework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom2.Attribute;
import org.jdom2.JDOMException;
import org.jdom2.input.DOMBuilder;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * {@link Tree} représente un arbre/graphe (non orienté).
 * Il est composé d'une {@link List} de {@link Vertex}, 
 * et d'une {@link List} de {@link Relation}.
 * @author jfourmond
 */
public class Tree implements ErrorHandler {
	private static final String INDEX = "IndexSource";
		private static final String ENTREE = "ENTREE";
			private static final String ID = "id";
		private static final String RELATION = "RELATION";
			private static final String NOM = "nom";
		private static final String LIEN = "LIEN";
	
	private static DocumentBuilderFactory domFactory;
	private static DocumentBuilder domBuilder;
	private static String vertexKey;
	
	private static SAXBuilder saxBuilder;
	
	private Document document;
	private File file;
	
	private List<Vertex> vertices;
	private List<Relation> relations;
	
	//	CONSTRUCTEURS
	public Tree() {
		file = null;
		document = null;
		
		vertices = new ArrayList<>();
		relations = new ArrayList<>();
	}
	
	//	GETTERS
	public File getFile() { return file; }
	
	public Document getDocument() { return document; }
	
	public List<Vertex> getVertices() { return vertices; }
	
	public List<Relation> getRelations() { return relations; }
	
	
	//	SETTERS
	public void setFile(File file) { this.file = file; }
	
	public void setDocument(Document document) { this.document = document; }
	
	public void setVertices(List<Vertex> vertices) { this.vertices = vertices; }
	
	public void setRelations(List<Relation> relations) { this.relations = relations; }
	
	
	//	METHODES
	/**
	 * Crée un sommet (sans attribut)
	 * @param vertexID : identifiant unique du sommet
	 * @throws TreeException si le sommet existe déjà
	 * @throws VertexException si l'identifiant est <code>null</code> ou vide
	 */
	public void createVertex(String vertexID) throws TreeException, VertexException {
		if(getVertex(vertexID) == null) {
			Vertex vertex = new Vertex(vertexID);
			vertices.add(vertex);
		} else throw new TreeException("Le sommet existe déjà.");
	}
	
	/**
	 * Crée un sommet
	 * @param vertex : le sommet à ajouter
	 * @throws TreeException si le sommet existe déjà
	 */
	public void createVertex(Vertex vertex) throws TreeException {
		if(getVertex(vertex.getID()) == null) {
			vertices.add(vertex);
		} else throw new TreeException("Le sommet existe déjà.");
	}
	
	/**
	 * Retourne les attributs du sommet
	 * @param vertexID : identifiant unique du sommet
	 * @return les attributs du sommet
	 */
	public Vertex getVertex(String vertexID) {
		for(Vertex vertex : vertices) {
			if(vertex.getID().equals(vertexID))
				return vertex;
		}
		return null;
	}
	
	/**
	 * Retourne l'identifiant unique du sommet ayant pour valeur d'attribut
	 * @param value : la valeur d'attribut à rechercher
	 * @return l'identifiant unique du sommet, ou null
	 */
	public String getVertexID(String value) {
		for(Vertex vertex : vertices) {
			if(vertex.isValueOfAttribute(value))
				return vertex.getID();
		}
		return null;
	}
	
	/**
	 * Supprime le sommet 
	 * @param vertex : sommet à supprimer
	 */
	public void removeVertex(Vertex vertex) {
		vertices.remove(vertex);
		removeRelatedPair(vertex);
	}
	
	/**
	 * Teste si l'identifiant est l'identifiant d'un {@link Vertex} de l'arbre
	 * @param id : identifiant
	 * @return <code>true</code> si l'identifiant correspond à un {@link Vertex}, <code>false</code> sinon
	 */
	public boolean isID(String id) {
		for(Vertex vertex : vertices) {
			if(vertex.getID().equals(id))
				return true;
		}
		return false;
	}
	
	/**
	 * Teste s'il n'existe pas des sommets dans l'arbre
	 * @return : <code>true</code> si il n'y a pas de sommet, <code>false</code> sinon
	 */
	public boolean isVerticesEmpty() {
		return vertices.isEmpty();
	}
	
	/**
	 * Crée un attribut au sommet
	 * @param vertexID : identifiant unique du sommet
	 * @param attributeID : identifiant unique de l'attribut
	 * @throws TreeException si le sommet n'existe pas
	 * @throws VertexException si l'attribut existe déjà pour ce sommet
	 */
	public void createAttribute(String vertexID, String attributeID) throws TreeException, VertexException {
		Vertex vertex = getVertex(vertexID);
		if(vertex != null)
			vertex.add(attributeID);
		else throw new TreeException("Le sommet n'existe pas.");
	}
	
	/**
	 * Remplace la valeur de l'attribut associé au sommet
	 * @param vertexID : identifiant unique du sommet
	 * @param attributeID : identifiant unique de l'attribut
	 * @param value : valeur de l'attribut
	 * @throws TreeException si le sommet
	 * @throws VertexException si l'attribut n'existe pas
	 */
	public void setAttribute(String vertexID, String attributeID, String value) throws TreeException, VertexException {
		Vertex vertex = getVertex(vertexID);
		if(vertex != null)
			vertex.set(attributeID, value);
		else throw new TreeException("Le sommet n'existe pas.");
	}
	
	/**
	 * 
	 * @param value : valeur de l'attribut
	 * @throws VertexException 
	 */
	/**
	 * Association d'un attribut et de sa valeur correspondante avec un sommet
	 * @param vertexID : identifiant unique du sommet
	 * @param attributeID : identifiant unique de l'attribut
	 * @throws TreeException si le sommet n'existe pas
	 * @throws VertexException si l'attribut n'existe pas
	 */
	public void addAttribute(String vertexID, String attributeID, String value) throws TreeException, VertexException {
		createAttribute(vertexID, attributeID);
		setAttribute(vertexID, attributeID, value);
	}
	
	/**
	 * Crée une relation
	 * @param relation : nom de la relation
	 * @throws TreeException si la relation existe déjà
	 * @throws RelationException si le nom de la relation est <code>null</code> ou vide
	 */
	public void createRelation(String relation) throws TreeException, RelationException {
		if(getRelation(relation) == null)
			relations.add(new Relation(relation));
		else throw new TreeException("La relation existe déjà.");
	}
	
	/**
	 * Crée une relation
	 * @param relation : la relation à ajouter
	 * @throws TreeException si la relation existe déjà
	 */
	public void createRelation(Relation relation) throws TreeException {
		if(getRelation(relation.getName()) == null) {
			relations.add(relation);
		} else throw new TreeException("La relation existe déjà.");
	}
	
	/**
	 * Retourne la relation portant le nom
	 * @param name : nom de la relation
	 * @return la relation portant le nom
	 */
	public Relation getRelation(String name) {
		for(Relation relation : relations) {
			if(relation.getName().equals(name))
				return relation;
		}
		return null;
	}
	
	/**
	 * Teste si la relation portant le nom existe
	 * @param name : nom de la relation à rechercher
	 * @return <code>true</code> si la relation existe, <code>false</code> sinon
	 */
	public boolean containsRelation(String name) {
		return (getRelation(name)) != null ? true : false;
	}
	
	/**
	 * Ajoute une paire à la relation
	 * @param name : nom de la relation
	 * @param vertex1ID : identifiant unique du premier sommet
	 * @param vertex2ID : identifiant unique du second sommet
	 * @throws TreeException si la relation n'existe pas
	 * @throws RelationException si la paire, dans cet ordre précis, existe déjà
	 */
	public void addPair(String name, String vertex1ID, String vertex2ID) throws TreeException, RelationException {
		Relation relation = getRelation(name);
		if(relation != null) {
			Vertex vertex1 = getVertex(vertex1ID);
			Vertex vertex2 = getVertex(vertex2ID);
			relation.add(new Pair<>(vertex1, vertex2));
		} else throw new TreeException("La relation n'existe pas.");
	}
	
	/**
	 * Ajoute une paire à la relation
	 * @param name : nom de la relation
	 * @param vertex1 : premier sommet
	 * @param vertex2 : second sommet
	 * @throws RelationException si la paire, dans cet ordre précis, existe déjà
	 * @throws TreeException si la relation n'existe pas
	 */
	public void addPair(String name, Vertex vertex1, Vertex vertex2) throws RelationException, TreeException {
		Relation relation = getRelation(name);
		if(relation != null)
			relation.add(new Pair<>(vertex1, vertex2));
		else throw new TreeException("La relation n'existe pas.");
	}
	
	/**
	 * Supprime les paires des relations lié au sommet
	 * @param vertex : sommet à rechercher
	 */
	public void removeRelatedPair(Vertex vertex) {
		for(Relation relation : relations) {
			relation.removeRelatedPair(vertex);
		}
	}
	
	/**
	 * Lecture d'un fichier XML pour construire un arbre
	 * @param filename : chemin vers le fichier XML
	 * @throws TreeException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws RelationException
	 * @throws VertexException
	 */
	public void readFromFile(String filename) throws TreeException, ParserConfigurationException, SAXException, IOException, RelationException, VertexException {
		vertices.clear();
		relations.clear();
		
		file = new File(filename);
		domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setValidating(true);
		
		domBuilder = domFactory.newDocumentBuilder();
		domBuilder.setErrorHandler(this);
		
		document = domBuilder.parse(file);
		document.getDocumentElement().normalize();

		buildVertices();
		buildRelations();
	}
	
	/**
	 * Lecture d'un fichier XML pour construire un arbre
	 * @param F : le fichier XML
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws TreeException
	 * @throws VertexException
	 * @throws RelationException
	 */
	public void readFromFile(File F) throws ParserConfigurationException, SAXException, IOException, TreeException, VertexException, RelationException {
		vertices.clear();
		relations.clear();
		
		file = F;
		domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setValidating(true);
		
		domBuilder = domFactory.newDocumentBuilder();
		domBuilder.setErrorHandler(this);
		
		document = domBuilder.parse(file);
		document.getDocumentElement().normalize();

		buildVertices();
		buildRelations();
	}
	
	public void writeInFile() throws FileNotFoundException, IOException, JDOMException {
		saxBuilder = new SAXBuilder();
		DOMBuilder builder = new DOMBuilder();
		org.jdom2.Document documentJDOM;
		// Racine du document
		org.jdom2.Element racine = new org.jdom2.Element(INDEX);
		
		for(Vertex vertex : vertices) {
			// Ajout d'une entrée
			org.jdom2.Element entree = new org.jdom2.Element(ENTREE);
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
			for(Relation r : relations) {
				org.jdom2.Element relation = new org.jdom2.Element(RELATION);
				entree.addContent(relation);
				// Nom de la relation
				Attribute relationName = new Attribute(NOM, r.getName());
				relation.setAttribute(relationName);
				// Liens de la relation
				List<Pair<Vertex, Vertex>> listPairs = r.getPairs(vertex);
				for(Pair<Vertex, Vertex> pair : listPairs) {
					org.jdom2.Element lien = new org.jdom2.Element(LIEN);
					relation.addContent(lien);
					lien.setText(pair.getSecond().getID());
				}
			}
		}
		
		// Enregistrement
		if(document != null) {
			documentJDOM = builder.build(document);
		} else {
			documentJDOM = new org.jdom2.Document(racine);
		}
		
		XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
		sortie.output(documentJDOM, new FileOutputStream(file));
	}
	
	private void buildRelations() throws TreeException, RelationException, VertexException {
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
						if(!containsRelation(relationName)) {					// Si la relation n'existe pas,
							createRelation(relationName);						// on la crée
						}
						NodeList lienList = jelement.getElementsByTagName(LIEN);
						for(int k=0 ; k<lienList.getLength() ; k++) {			// Pour chaque lien
							Node knode = lienList.item(k);
							if(knode.getNodeType() == Node.ELEMENT_NODE) {
								String value = knode.getTextContent();
								if(!value.isEmpty()) {
									if(isID(value)) {
										addPair(relationName, vertex1ID, value);
									} else {
										String vertex2ID = getVertexID(value);
										addPair(relationName, vertex1ID, vertex2ID);
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	private void buildVertices() throws TreeException, VertexException {
		NodeList nList = document.getElementsByTagName(ENTREE);
		for(int i=0 ; i<nList.getLength() ; i++) {
			Node node = nList.item(i);
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				vertexKey = element.getAttribute(ID); 					// La clé du sommet actuel
				createVertex(vertexKey);								// Création du sommet actuel
				if (node.hasAttributes()) {								// Recherche de ses autres attributs
					NamedNodeMap nodeMap = node.getAttributes();		// Récupération de ses attributs
					for(int j=0; j<nodeMap.getLength(); j++) {
						Node attribute = nodeMap.item(j);
						String name = attribute.getNodeName();
						if(!name.equals(ID)) {
							String value = attribute.getNodeValue();
							addAttribute(vertexKey, name, value);		// Ajout des attributs au sommet actuel
						}
					}
				}
			}
		}
	}
	
	/**
	 * Vide l'arbre courant
	 */
	public void clear() {
		vertices.clear();
		relations.clear();
	}
	
	@Override
	public String toString() {
		String ch = "Sommets (" + vertices.size() + ") : \n";
		for(Vertex vertex : vertices) {
			ch += "\t" + vertex + "\n";
		}
		
		ch += "Relations (" + relations.size() + ") : \n";
		for(Relation relation : relations) {
			ch += "\t" + relation + "\n";
		}
		return ch;
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
