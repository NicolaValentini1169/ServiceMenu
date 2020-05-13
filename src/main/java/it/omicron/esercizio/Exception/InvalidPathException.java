package it.omicron.esercizio.Exception;

public class InvalidPathException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	public InvalidPathException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}