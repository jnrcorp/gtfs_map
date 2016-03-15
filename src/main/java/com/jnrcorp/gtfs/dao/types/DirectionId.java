package com.jnrcorp.gtfs.dao.types;

public enum DirectionId {

	INBOUND(0, "travel in one direction (e.g. outbound travel)"),
	OUTBOUND(1, "travel in the opposite direction (e.g. inbound travel)");

	private final int id;
	private final String meaning;

	private DirectionId(int id, String meaning) {
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

	public static DirectionId getById(String id) {
		for (DirectionId directionId : DirectionId.values()) {
			if (directionId.getId() == Integer.valueOf(id)) {
				return directionId;
			}
		}
		return null;
	}

}
