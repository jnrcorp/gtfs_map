package com.jnrcorp.gtfs.dao.types;

public enum RouteType {

	LIGHT_RAIL(0, "Tram, Streetcar, Light rail. Any light rail or street level system within a metropolitan area."),
	SUBWAY(1, "Subway, Metro. Any underground rail system within a metropolitan area."),
	RAIL(2, "Rail. Used for intercity or long-distance travel."),
	BUS(3, "Bus. Used for short- and long-distance bus routes."),
	FERRY(4, "Ferry. Used for short- and long-distance boat service."),
	CABLE_CAR(5, "Cable car. Used for street-level cable cars where the cable runs beneath the car."),
	GONDOLA(6, "Gondola, Suspended cable car. Typically used for aerial cable cars where the car is suspended from the cable."),
	FUNICULAR(7, "Funicular. Any rail system designed for steep inclines.");

	private final int id;
	private final String meaning;

	private RouteType(int id, String meaning) {
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

	public static RouteType getById(String id) {
		for (RouteType routeType : RouteType.values()) {
			if (routeType.getId() == Integer.valueOf(id)) {
				return routeType;
			}
		}
		return null;
	}

}
