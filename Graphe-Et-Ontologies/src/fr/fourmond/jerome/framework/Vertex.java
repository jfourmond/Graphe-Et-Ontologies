package fr.fourmond.jerome.framework;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class Vertex {
	
	private String ID;
	private Map<String, String> attributes;
	
	//	CONSTRUCTEURS
	public Vertex(String ID) {
		this.ID = ID;
		attributes = new HashMap<>();
	}
	
	//	GETTERS
	public String getID() { return ID; }
	
	public Map<String, String> getAttributes() { return attributes; }
	
	//	SETTERS
	public void setID(String iD) { ID = iD; }
	
	public void setAttributes(Map<String, String> attributes) { this.attributes = attributes; }
	
	//	METHODES
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
	 * @throws VertexException : si l'attribut n'existe pas
	 */
	public void set(String attributeID, String value) throws VertexException {
		if(containsAttribute(attributeID))
			attributes.replace(attributeID, value);
		else throw new VertexException("L'attribut n'existe pas.");
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
	 * @throws VertexException : si l'attribut existe déjà
	 */
	public void add(String attributeID) throws VertexException {
		if(containsAttribute(attributeID)) throw new VertexException("L'attribut existe déjà");
		else attributes.put(attributeID, null);
	}
	
	/**
	 * Teste si le paramètre "value" est la valeur d'un attribut 
	 * @param value : valeur à rechercher
	 * @return <code>true</code> si la valeur existe, <code>false</code> sinon
	 */
	public boolean isValueOfAttribute(String value) {
		for(Entry<String, String> attribute : attributes.entrySet()) {
			if(attribute.getValue().equals(value))
				return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		String ch = "ID : " + ID + "\n";
		Set<Entry<String, String>> attributes = this.attributes.entrySet();
		for(Entry<String, String> attribute : attributes) {
			String readAttribute = attribute.getKey();
			readAttribute = readAttribute.substring(0, 1).toUpperCase() + readAttribute.substring(1);
			ch += "\t" + readAttribute + " = " + attribute.getValue() + "\n";
		}
		return ch;
	}
}
