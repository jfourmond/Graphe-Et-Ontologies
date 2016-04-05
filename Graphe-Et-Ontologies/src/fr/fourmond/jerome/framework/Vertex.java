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
	String full_data();
	
	/**
	 * Devrait afficher au sein du sommet
	 * @return description brève du sommet
	 */
	String brief_data();
	
	/**
	 * Devrait afficher la description d'un arc entre les deux {@link Vertex}
	 * @param A : Premier {@link Vertex} 
	 * @param B : Second {@link Vertex}
	 * @return description de l'arc (sous la forme d'un {@link String}
	 */
	String build_edge(Vertex A, Vertex B);
}
