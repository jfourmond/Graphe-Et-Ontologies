package fr.fourmond.jerome.framework;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * {@link Vertex} représente un sommet.
 * Il est composé d'un ID, un {@link String}, devant être unique,
 * d'un nom,
 * et d'une {@link Map} associant attributs et valeurs
 * @author jfourmond
 */
public class Vertex {
	private String ID;
	private String name;
	private Map<String, String> attributes;
	
	//	CONSTRUCTEURS
	public Vertex(String ID, String name) throws VertexException {
		if(ID == null || ID.isEmpty())
			throw new VertexException("Aucun ID spécifié.");
		else if(name == null || name.isEmpty()) {
			throw new VertexException("Aucun nom spécifié.");
		} else { 
			this.ID = ID;
			this.name = name;
			attributes = new HashMap<>();
		}
	}
	
	public Vertex(Vertex vertex) throws VertexException {
		ID = vertex.getID();
		name = vertex.getName();
		attributes = new HashMap<>(vertex.getAttributes());
	}
	
	//	GETTERS
	public String getID() { return ID; }
	
	public String getName() { return name; }
	
	public Map<String, String> getAttributes() { return attributes; }
	
	//	SETTERS
	public void setID(String iD) throws VertexException {
		if(iD == null || iD.isEmpty())
			throw new VertexException("Aucun ID spécifié");
		else 
			ID = iD;
	}
	
	public void setName(String name) { this.name = name; }
	
	public void setAttributes(Map<String, String> attributes) { this.attributes = attributes; }
	
	//	METHODES
	/**
	 * Retourne la valeur de l'attribut s'il existe
	 * @param attributeID : identifiant unique de l'attribut
	 * @return la valeur de l'attribut s'il existe, <code>null</code> sinon
	 */
	public String get(String attributeID) {
		if(attributeID.equals("ID"))
			return ID;
		else if(attributeID.equals("Nom"))
			return name;
		else return attributes.get(attributeID);
	}

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
	
	/**
	 * Retourne un ensemble des noms d'attributs utilisés.
	 * @return un ensemble des noms d'attributs utilisés
	 */
	public Set<String> getKey() { return attributes.keySet(); }
	
	public String info() {
		String ch = "Sommet " + ID + "\n";
		ch += "\tNom : " + name + "\n";
		Set<Entry<String, String>> attributes = this.attributes.entrySet();
		for(Entry<String, String> attribute : attributes) {
			String readAttribute = attribute.getKey();
			readAttribute = readAttribute.substring(0, 1).toUpperCase() + readAttribute.substring(1);
			ch += "\t" + readAttribute + " = " + attribute.getValue() + "\n";
		}
		return ch;
	}
	
	@Override
	public String toString() { return ID + " - " + name; }
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass())
			return false;
		else {
			Vertex vertex = (Vertex) obj;
			return this.ID.equals(vertex.ID);
		}
	}
}
