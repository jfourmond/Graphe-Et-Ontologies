package fr.fourmond.jerome.ontology;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	
	private static String vertexKey;
	
	private List<VertexOntology> vertices;
	
	private Map<String, List<Pair<String, String>>> relations;
	
	private Document document;
	
	public TreeOntology() {
		vertices = new ArrayList<>();
		relations = new HashMap<>();
		document = null;
	}
	
	//	GETTERS
	public List<VertexOntology> getVertices() { return vertices; }
	
	public Map<String, List<Pair<String, String>>> getRelations() { return relations; }
	
	//	SETTERS
	public void setVertices(List<VertexOntology> vertices) { this.vertices = vertices; }
	
	public void setRelations(Map<String, List<Pair<String, String>>> relations) { this.relations = relations; }
	
	//	METHODES
	/**
	 * Crée un sommet (sans attribut)
	 * @param vertexID : identifiant unique du sommet
	 * @throws TreeOntologyException si le sommet existe déjà
	 */
	public void createVertex(String vertexID) throws TreeOntologyException {
		if(getVertex(vertexID) == null) {
			VertexOntology vertex = new VertexOntology(vertexID);
			vertices.add(vertex);
		} else throw new TreeOntologyException("Le sommet existe déjà.");
	}
	
	/**
	 * Retourne les attributs du sommet
	 * @param vertexID : identifiant unique du sommet
	 * @return les attributs du sommet
	 */
	public VertexOntology getVertex(String vertexID) {
		for(VertexOntology vertex : vertices) {
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
		for(VertexOntology vertex : vertices) {
			if(vertex.isValueOfAttribute(value))
				return vertex.getID();
		}
		return null;
	}
	
	public boolean isID(String id) {
		for(VertexOntology vertex : vertices) {
			if(vertex.getID().equals(id))
				return true;
		}
		return false;
	}
	
	/**
	 * Crée un attribut au sommet
	 * @param vertexID : identifiant unique du sommet
	 * @param attributeID : identifiant unique de l'attribut
	 * @throws TreeOntologyException si le sommet n'existe pas ou si l'attribut existe déjà
	 */
	public void createAttribute(String vertexID, String attributeID) throws TreeOntologyException {
		VertexOntology vertex = getVertex(vertexID);
		if(vertex != null)
			vertex.add(attributeID);
		else throw new TreeOntologyException("Le sommet n'existe pas.");
	}
	
	/**
	 * Remplace la valeur de l'attribut associé au sommet
	 * @param vertexID : identifiant unique du sommet
	 * @param attributeID : identifiant unique de l'attribut
	 * @param value : valeur de l'attribut
	 * @throws TreeOntologyException : si le sommet, ou l'attribut, n'existe pas 
	 */
	public void setAttribute(String vertexID, String attributeID, String value) throws TreeOntologyException {
		VertexOntology vertex = getVertex(vertexID);
		if(vertex != null) {
			if(vertex.containsAttribute(attributeID))
				vertex.set(attributeID, value);
			else throw new TreeOntologyException("L'attribut n'existe pas");
		} else throw new TreeOntologyException("Le sommet n'existe pas.");
	}
	
	/**
	 * Association d'un attribut et de sa valeur correspondante avec un sommet
	 * @param vertexID : identifiant unique du sommet
	 * @param attributeID : identifiant unique de l'attribut
	 * @param value : valeur de l'attribut
	 */
	public void addAttribute(String vertexID, String attributeID, String value) throws TreeOntologyException {
		createAttribute(vertexID, attributeID);
		setAttribute(vertexID, attributeID, value);
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
		
		correctRelations();
	}
	
	/**
	 * Charge le fichier spécifié
	 * Si le fichier n'est pas valide avec la dtd une exception interrompt le chargement
	 * @param filename : chemin vers le document
	 */
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
			document = builder.parse(filename);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void buildTree(NodeList nodeList) throws TreeOntologyException {
		for (int count = 0; count < nodeList.getLength(); count++) {
			Node tempNode = nodeList.item(count);
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				String nodeName = tempNode.getNodeName();
				if(nodeName.equals("ENTREE")) {
					Element element = (Element) tempNode;
					vertexKey = element.getAttribute("id"); // La clé du sommet
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
								String linkNodeValue = linkNode.getTextContent();
								if(!linkNodeValue.isEmpty()) {
									addEdge(relationName, vertexKey, linkNodeValue);
								}
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
	
	/**
	 * Prévu pour corriger les relations de l'arbre après lecture
	 * d'un document
	 */
	private void correctRelations() {
		Set<String> relationSet = relations.keySet();
		List<Pair<String, String>> relationVertices;
		for(String relation : relationSet) {
			relationVertices = relations.get(relation);
			for(Pair<String, String> vertices : relationVertices) {
				String second = vertices.getSecond();
				if(!isID(second))
					vertices.setSecond(getVertexID(second));
			}
		}
	}
	
	@Override
	public String toString() {
		String ch = "Sommets (" + vertices.size() + ") : \n";
		for(VertexOntology vertex : vertices) {
			ch += "\t" + vertex.fullData();
		}
		
		ch += "Relations (" + relations.size() + ") : \n";
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
