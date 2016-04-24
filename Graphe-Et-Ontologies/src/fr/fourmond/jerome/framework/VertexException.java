package fr.fourmond.jerome.framework;

/**
 * {@link Exception} dans le cas d'opérations non autorisées ou dangereuses
 * sur un {@link Vertex}
 * @author jfourmond
 */
public class VertexException extends Exception {
	private static final long serialVersionUID = 1L;

	public VertexException() { super(); }

	public VertexException(String message) { super(message); }
	
	public VertexException(Throwable cause) { super(cause); }
	
	public VertexException(String message, Throwable cause) { super(message, cause); }
}
