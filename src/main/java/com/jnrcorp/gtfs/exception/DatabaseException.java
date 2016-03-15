package com.jnrcorp.gtfs.exception;

public class DatabaseException extends RuntimeException {

	private static final long serialVersionUID = -466005713926704320L;

	public DatabaseException() {
		super();
	}

	public DatabaseException(String msg) {
		super(msg);
	}

	public DatabaseException(Throwable e) {
		super(e);
	}

	public DatabaseException(String msg, Throwable e) {
		super(msg, e);
	}

}
