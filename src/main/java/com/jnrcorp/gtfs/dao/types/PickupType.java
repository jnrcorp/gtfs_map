package com.jnrcorp.gtfs.dao.types;

public enum PickupType {

	REGULAR(0, "Regularly scheduled pickup"),
	NO_PICKUP(1, "No pickup available"),
	CALL_AHEAD(2, "Must phone agency to arrange pickup"),
	COODINATE_DRIVER(3, "Must coordinate with driver to arrange pickup");

	private final int id;
	private final String meaning;

	private PickupType(int id, String meaning) {
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

	public static PickupType getById(String id) {
		for (PickupType pickupType : PickupType.values()) {
			if (pickupType.getId() == Integer.valueOf(id)) {
				return pickupType;
			}
		}
		return null;
	}

}
