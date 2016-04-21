package fr.fourmond.jerome.ontology;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import fr.fourmond.jerome.framework.Vertex;

public class VertexOntology implements Vertex {

	private String ID;
	private Map<String, String> attributes;
	
	//	CONSTRUCTEURS
	public VertexOntology(String ID) {
		this.ID = ID;
		attributes = new HashMap<>();
	}
	
	//	GETTERS
	public String getID() { return ID; }
	
	public Map<String, String> getAttributes() { return attributes; }
	
	//	SETTERS
	public void setID(String ID) { this.ID = ID; }
	
	public void setAttributes(Map<String, String> attributes) { this.attributes = attributes; }
	
	/**
	 * Retourne la valeur de l'attribut s'il existe
	 * @param attributeID : identifiant unique de l'attribut
	 * @return la valeur de l'attribut s'il existe, <code>null</code> sinon
	 */
	public String get(String attributeID) { return attributes.get(attributeID); }

	/**
	 * Edite la valeur de l'attribut
	 * @param attributeID : identifiant unique de l'attribut
	 * @param value : nouvelle valeur de l'attribut
	 * @throws VertexOntologyException : si l'attribut n'existe pas
	 */
	public void set(String attributeID, String value) throws VertexOntologyException {
		if(containsAttribute(attributeID))
			attributes.replace(attributeID, value);
		else throw new VertexOntologyException("L'attribut n'existe pas.");
	}
	
	/**
	 * Retourne <code>true</code> si le sommet possède déjà l'attribut
	 * @param attributeID : identifiant unique de l'attribut
	 * @return <code>true</code> si le sommet possède l'attribut, <code>false</code> sinon
	 */
	public boolean containsAttribute(String attributeID) { return attributes.containsKey(attributeID); }
	
	/**
	 * Ajoute un attribut
	 * @param attributeID : identifiant unique de l'attribut
	 * @throws VertexOntologyException : si l'attribut existe déjà
	 */
	public void add(String attributeID) throws VertexOntologyException {
		if(containsAttribute(attributeID)) throw new VertexOntologyException("L'attribut existe déjà");
		else attributes.put(attributeID, null);
	}
	
	@Override
	public String fullData() {
		return toString();
	}

	@Override
	public String briefData() {
		return this.ID;
	}
	
	@Override
	public String toString() {
		String ch = "ID : " + ID + "\n";
		Set<Entry<String, String>> attributes = this.attributes.entrySet();
		for(Entry<String, String> attribute : attributes) {
			ch += "\t" + attribute.getKey() + " = " + attribute.getValue() + "\n";
		}
		return ch;
	}
}
