package fr.fourmond.jerome.ontology;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * {@link TreeOntology} représente le graphe d'une ontologie.
 * @author etudiant
 */
public class TreeOntology {
	private static DocumentBuilderFactory domFactory;
	private static DocumentBuilder builder;
	
	// Association < Identifiant du Sommet, < Attribut, Valeur d'attribut > >
	private Map<String, Map<String, String>> vertices;
	// Association < Identifiant relation, < Sommet, Sommet >
	private Map<String, List<Pair<String, String>>> relations;
	
	private Document document;
	
	public TreeOntology() {
		vertices = new HashMap<>();
		relations = new HashMap<>();
		document = null;
	}
	
	//	GETTERS
	public Map<String, Map<String, String>> getVertices() { return vertices; }
	
	public Map<String, List<Pair<String, String>>> getRelations() { return relations; }
	
	//	SETTERS
	public void setVertices(Map<String, Map<String, String>> vertices) { this.vertices = vertices; }
	
	public void setRelations(Map<String, List<Pair<String, String>>> relations) { this.relations = relations; }
	
	//	METHODES
	/**
	 * Crée un sommet (sans attribut)
	 * @param vertex : identifiant unique du sommet
	 * @throws TreeOntologyException si le sommet existe déjà
	 */
	public void createVertex(String vertex) throws TreeOntologyException {
		if(getVertex(vertex) == null)
			vertices.put(vertex, new HashMap<>());
		else throw new TreeOntologyException("Le sommet existe déjà.");
	}
	
	/**
	 * Retourne les attributs du sommet
	 * @param vertex : identifiant unique du sommet
	 * @return les attributs du sommet
	 */
	public Map<String, String> getVertex(String vertex) { return vertices.get(vertex); }
	
	/**
	 * Crée un attribut associé au sommet
	 * @param vertex : identifiant unique du sommet
	 * @param attribute : identifiant unique de l'attribut
	 * @throws TreeOntologyException si l'attribut existe déjà
	 */
	public void createAttribute(String vertex, String attribute) throws TreeOntologyException {
		if(getVertex(vertex).get(attribute) == null) {
			getVertex(vertex).put(attribute, null);
		} else throw new TreeOntologyException("L'attribut existe déjà.");
		
	}
	
	/**
	 * Remplace la valeur de l'attribut associé au sommet
	 * @param vertex : identifiant unique du sommet
	 * @param attribute : identifiant unique de l'attribut
	 * @param value : valeur de l'attribut
	 * @throws TreeOntologyException : si l'attribute n'existe pas 
	 */
	public void setAttribute(String vertex, String attribute, String value) throws TreeOntologyException {
		if(getVertex(vertex).containsKey(attribute))
			getVertex(vertex).replace(attribute, value);
		else throw new TreeOntologyException("L'attribut n'existe pas");
	}
	
	/**
	 * Association d'un attribut et de sa valeur correspondante avec un sommet
	 * @param vertex : identifiant unique du sommet
	 * @param attribute : identifiant unique de l'attribut
	 * @param value : valeur de l'attribut
	 */
	public void addAttribute(String vertex, String attribute, String value) throws TreeOntologyException {
		createAttribute(vertex, attribute);
		setAttribute(vertex, attribute, value);
	}
	
	/**
	 * Crée une relation
	 * @param relation : identifiant unique de la relation
	 * @throws TreeOntologyException si la relation existe déjà
	 */
	public void createRelation(String relation) throws TreeOntologyException {
		if(getRelation(relation) == null)
			relations.put(relation, new ArrayList<>());
		else throw new TreeOntologyException("La relation existe déjà.");
	}
	
	/**
	 * Retourne la liste d'arc de la relation
	 * @param relation : identifiant unique de la relation
	 * @return la liste d'arc de la relation
	 */
	public List<Pair<String, String>> getRelation(String relation) { return relations.get(relation); }
	
	/**
	 * Ajoute un arc à la relation
	 * @param relation : identifiant unique de la relation
	 * @param vertex1 : identifiant unique du premier sommet
	 * @param vertex2 : identifiant unique du second sommet
	 * @throws TreeOntologyException si la relation n'existe pas
	 */
	public void addEdge(String relation, String vertex1, String vertex2) throws TreeOntologyException {
		if(getRelation(relation) != null)
			getRelation(relation).add(new Pair<>(vertex1, vertex2));
		else throw new TreeOntologyException("La relation n'existe pas.");
	}
	
	public void readFromFile(String filename) throws TreeOntologyException {
		vertices.clear();
		relations.clear();
		
		buildDocument(filename);
		
		Element element = document.getDocumentElement();
		element.normalize();

		if (document.hasChildNodes()) {
			buildTree(document.getChildNodes());
		}
	}
	
	private void buildDocument(String filename) {
		domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setValidating(true);
		builder = null;
		try {
			builder = domFactory.newDocumentBuilder();
			builder.setErrorHandler(new ErrorHandler() {
				@Override
				public void warning(SAXParseException exception) throws SAXException { System.out.println("Warning : " + exception.getMessage()); }
				@Override
				public void fatalError(SAXParseException exception) throws SAXException {
					System.err.println("FATAL : " + exception.getMessage());
					throw exception;
				}
				@Override
				public void error(SAXParseException exception) throws SAXException {
					System.err.println("ERROR : " + exception.getMessage());
					throw exception;
				}
			});
			document = builder.parse("../Ontologies/Index327.xml");
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void printNode(NodeList nodeList) {
		 for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);
			// make sure it's element node.
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				// get node name and value
				System.out.println("\nNode Name =" + tempNode.getNodeName() + " [OPEN]");
				System.out.println("Node Value =" + tempNode.getTextContent());
				if (tempNode.hasAttributes()) {
					// get attributes names and values
					NamedNodeMap nodeMap = tempNode.getAttributes();
					for (int i = 0; i < nodeMap.getLength(); i++) {
						Node node = nodeMap.item(i);
						System.out.println("attr name : " + node.getNodeName());
						System.out.println("attr value : " + node.getNodeValue());
					}
				}
				if (tempNode.hasChildNodes()) {
					// loop again if has child nodes
					printNode(tempNode.getChildNodes());
				}
				System.out.println("Node Name =" + tempNode.getNodeName() + " [CLOSE]");
			}
		 }
	}
	
	private void buildTree(NodeList nodeList) throws TreeOntologyException {
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				String nodeName = tempNode.getNodeName();
				if(nodeName.equals("ENTREE")) {
					Element element = (Element) tempNode;
					String vertexKey = element.getAttribute("id"); // La clé du sommet
					createVertex(vertexKey);
					if (tempNode.hasAttributes()) {
						NamedNodeMap nodeMap = tempNode.getAttributes();
						for (int i = 0; i < nodeMap.getLength(); i++) {
							Node node = nodeMap.item(i);
							String name = node.getNodeName();
							if(!name.equals("id")) {
								String value = node.getNodeValue();
								addAttribute(vertexKey, name, value);
							}
						}
					}
				}
				if(nodeName.equals("RELATION")) {
					Element element = (Element) tempNode;
					String relationName = element.getAttribute("nom");
					if(!relations.containsKey(relationName)) {
						createRelation(relationName);
					}
					if (tempNode.hasChildNodes()) {
						NodeList childNodes = tempNode.getChildNodes();
						for(int j=0 ; j<childNodes.getLength() ; j++) {
							Node linkNode = childNodes.item(j);
							if (linkNode.getNodeType() == Node.ELEMENT_NODE) {
								String linkNodeName = linkNode.getNodeName();
								String linkNodeValue = linkNode.getTextContent();
								addEdge(relationName, vertexKey, linkNodeValue);
								// TODO Corriger
							}
						}
					}
				}
				if (tempNode.hasChildNodes()) {
					buildTree(tempNode.getChildNodes());
				}
			}
		 }
	}
	
	@Override
	public String toString() {
		String ch = "Sommets : \n";
		// Affichage des sommets
		Set<String> vertexSet = vertices.keySet();
		Map<String, String> vertexAttributes;
		Set<Entry<String, String>> attributes;
		for(String vertex : vertexSet) {
			vertexAttributes = vertices.get(vertex);
			attributes = vertexAttributes.entrySet();
			ch += "\t" + vertex + " : \n";
			for(Entry<String, String> attribute : attributes) {
				ch += "\t\t" + attribute.getKey() + " = " + attribute.getValue() + "\n";
			}
		}
		ch += "Relations : \n";
		// Affichage des relations
		Set<String> relationSet = relations.keySet();
		List<Pair<String, String>> relationVertices;
		for(String relation : relationSet) {
			relationVertices = relations.get(relation);
			ch += "\t" + relation + " : \n";
			for(Pair<String, String> vertices : relationVertices) {
				ch += "\t\t" + vertices.getFirst() + " -> " + vertices.getSecond() + "\n";
			}
		}
		return ch;
	}
}
