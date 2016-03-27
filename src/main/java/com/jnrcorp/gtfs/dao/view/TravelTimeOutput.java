package com.jnrcorp.gtfs.dao.view;

import java.io.Serializable;

public class TravelTimeOutput implements Serializable {

	private static final long serialVersionUID = 2048057319758658665L;

	private Number travelTimeMinutes;
	private Number transferTravelTime = 0;
	private Number totalTripCount;
	private Integer stopId;
	private String stopName;
	private Double stopLatitude;
	private Double stopLongitude;
	private String routeName;
	private Integer directionId;
	private String agencyId;

	public TravelTimeOutput() {
		super();
	}

	public Number getTravelTimeMinutes() {
		return travelTimeMinutes;
	}

	public void setTravelTimeMinutes(Number travelTimeMinutes) {
		this.travelTimeMinutes = travelTimeMinutes;
	}

	public Number getTransferTravelTime() {
		return transferTravelTime;
	}

	public void setTransferTravelTime(Number transferTravelTime) {
		this.transferTravelTime = transferTravelTime;
	}

	public Number getTotalTripCount() {
		return totalTripCount;
	}

	public void setTotalTripCount(Number totalTripCount) {
		this.totalTripCount = totalTripCount;
	}

	public Integer getStopId() {
		return stopId;
	}

	public void setStopId(Integer stopId) {
		this.stopId = stopId;
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
