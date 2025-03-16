package me.tbandawa.web.skyzmetro.exceptions;

@SuppressWarnings("serial")
public class InvalidFileTypeException extends RuntimeException {
	
	public InvalidFileTypeException(String message) {
		super(message);
	}	
}
