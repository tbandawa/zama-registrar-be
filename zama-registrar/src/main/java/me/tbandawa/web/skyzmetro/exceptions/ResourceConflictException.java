package me.tbandawa.web.skyzmetro.exceptions;

@SuppressWarnings("serial")
public class ResourceConflictException extends RuntimeException {

	public ResourceConflictException(String message) {
		super(message);
	}
}
