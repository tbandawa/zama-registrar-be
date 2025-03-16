package me.tbandawa.web.skyzmetro.exceptions;

@SuppressWarnings("serial")
public class ServiceException extends RuntimeException {
	
	public ServiceException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
