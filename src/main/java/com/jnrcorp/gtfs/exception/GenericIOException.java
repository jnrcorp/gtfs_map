package com.jnrcorp.gtfs.exception;

public class GenericIOException extends RuntimeException {

	private static final long serialVersionUID = 7769602522078826313L;

	public GenericIOException() {
		super();
	}

	public GenericIOException(String msg) {
		super(msg);
	}

	public GenericIOException(Throwable t) {
		super(t);
	}

	public GenericIOException(String msg, Throwable t) {
		super(msg, t);
	}

}
