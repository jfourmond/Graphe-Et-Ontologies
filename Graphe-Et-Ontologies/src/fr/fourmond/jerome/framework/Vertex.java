package fr.fourmond.jerome.framework;

/**
 * {@link Vertex} est une interface devant être implémenter
 * pour pouvoir l'utiliser dans un {@link Tree}
 * @author jfourmond
 */
public interface Vertex {
	/**
	 * Devrait afficher lors du "clic" sur le sommet
	 * @return description complète du sommet
	 */
	public String fullData();
	
	/**
	 * Devrait afficher au sein du sommet
	 * @return description brève du sommet
	 */
	public String briefData();
}
