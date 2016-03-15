package com.jnrcorp.gtfs.dao.types;

public enum DropOffType {

	REGULAR(0, "Regularly scheduled drop off"),
	NO_DROP_OFF(1, "No drop off available"),
	CALL_AHEAD(2, "Must phone agency to arrange drop off"),
	COODINATE_DRIVER(3, "Must coordinate with driver to arrange drop off");

	private final int id;
	private final String meaning;

	private DropOffType(int id, String meaning) {
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

	public static DropOffType getById(String id) {
		for (DropOffType dropOffType : DropOffType.values()) {
			if (dropOffType.getId() == Integer.valueOf(id)) {
				return dropOffType;
			}
		}
		return null;
	}

}
