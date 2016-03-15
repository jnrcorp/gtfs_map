package com.jnrcorp.gtfs.exception;

import java.util.List;

public class InvalidInputException extends RuntimeException {

	private static final long serialVersionUID = -7481913601336922527L;

	private List<String> errorMessages;

	public InvalidInputException(String message) {
		super(message);
	}

	public InvalidInputException(Throwable cause) {
		super(cause);
	}

	public InvalidInputException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidInputException(String message, List<String> errorMessages) {
		super(message);
		setErrorMessages(errorMessages);
	}

	public InvalidInputException(String message, Throwable cause, List<String> errorMessages) {
		super(message, cause);
		setErrorMessages(errorMessages);
	}

	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

}
