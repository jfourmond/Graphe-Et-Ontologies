package fr.fourmond.jerome.framework;

public class TreeException extends Exception {

	private static final long serialVersionUID = 1L;

	public TreeException() { super(); }

	public TreeException(String message) { super(message); }
	
	public TreeException(Throwable cause) { super(cause); }
	
	public TreeException(String message, Throwable cause) { super(message, cause); }
	
}
