package com.jnrcorp.gtfs.dao.types;

public enum ExceptionType {

	ADDED_FOR_DATE(1, "Service has been added for the specified date"),
	REMOVED_FOR_DATE(2, "Service has been removed for the specified date");

	private final int id;
	private final String meaning;

	private ExceptionType(int id, String meaning) {
		this.id = id;
		this.meaning = meaning;
	}

	public int getId() {
		return id;
	}

	public String getMeaning() {
		return meaning;
	}

	public String getIdAsString() {
		return String.valueOf(this.id);
	}

	public static ExceptionType getById(String id) {
		for (ExceptionType exceptionType : ExceptionType.values()) {
			if (exceptionType.getId() == Integer.valueOf(id)) {
				return exceptionType;
			}
		}
		return null;
	}

}
