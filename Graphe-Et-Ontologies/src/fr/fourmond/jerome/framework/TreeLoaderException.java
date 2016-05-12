package fr.fourmond.jerome.framework;

/**
 *{@link Exception} dans le cas d'opérations non autorisées ou dangereuses
 * sur un {@link TreeLoader}
 * @author jfourmond
 */
public class TreeLoaderException extends Exception {
	private static final long serialVersionUID = 1L;

	public TreeLoaderException() { super(); }

	public TreeLoaderException(String message) { super(message); }
	
	public TreeLoaderException(Throwable cause) { super(cause); }
	
	public TreeLoaderException(String message, Throwable cause) { super(message, cause); }
}
