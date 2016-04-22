package fr.fourmond.jerome.framework;

public class VertexException extends Exception {
	private static final long serialVersionUID = 1L;

	public VertexException() { super(); }

	public VertexException(String message) { super(message); }
	
	public VertexException(Throwable cause) { super(cause); }
	
	public VertexException(String message, Throwable cause) { super(message, cause); }
}
