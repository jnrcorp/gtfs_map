package com.jnrcorp.gtfs.dao.view;

import java.io.Serializable;

public class TravelTimeOutput implements Serializable {

	private static final long serialVersionUID = 2048057319758658665L;

	private Integer travelTimeMinutes;
	private Integer totalTripCOunt;
	private String stopName;
	private Double stopLatitude;
	private Double stopLongitude;
	private String routeName;
	private String agencyId;

	public TravelTimeOutput() {
		super();
	}

	public Integer getTravelTimeMinutes() {
		return travelTimeMinutes;
	}

	public void setTravelTimeMinutes(Integer travelTimeMinutes) {
		this.travelTimeMinutes = travelTimeMinutes;
	}

	public Integer getTotalTripCOunt() {
		return totalTripCOunt;
	}

	public void setTotalTripCOunt(Integer totalTripCOunt) {
		this.totalTripCOunt = totalTripCOunt;
	}

	public String getStopName() {
		return stopName;
	}

	public void setStopName(String stopName) {
		this.stopName = stopName;
	}

	public Double getStopLatitude() {
		return stopLatitude;
	}

	public void setStopLatitude(Double stopLatitude) {
		this.stopLatitude = stopLatitude;
	}

	public Double getStopLongitude() {
		return stopLongitude;
	}

	public void setStopLongitude(Double stopLongitude) {
		this.stopLongitude = stopLongitude;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

}
