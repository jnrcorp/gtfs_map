package com.jnrcorp.gtfs.dao.view;

import java.io.Serializable;

public class TravelTimeOutput implements Serializable {

	private static final long serialVersionUID = 2048057319758658665L;

	private Integer travelTimeMinutes;
	private Integer totalTripCount;
	private String stopName;
	private Double stopLatitude;
	private Double stopLongitude;
	private Integer stopSequence;
	private String routeName;
	private Integer directionId;
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

	public Integer getTotalTripCount() {
		return totalTripCount;
	}

	public void setTotalTripCount(Integer totalTripCount) {
		this.totalTripCount = totalTripCount;
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

	public Integer getStopSequence() {
		return stopSequence;
	}

	public void setStopSequence(Integer stopSequence) {
		this.stopSequence = stopSequence;
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public Integer getDirectionId() {
		return directionId;
	}

	public void setDirectionId(Integer directionId) {
		this.directionId = directionId;
	}

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

}
