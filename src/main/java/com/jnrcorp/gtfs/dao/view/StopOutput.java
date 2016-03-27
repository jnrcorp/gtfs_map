package com.jnrcorp.gtfs.dao.view;

import java.util.ArrayList;
import java.util.List;

public class StopOutput {

	private Integer stopId;
	private String stopName;
	private Double stopLatitude;
	private Double stopLongitude;
	private String agencyId;
	private Integer travelTimeMinutes = 0;
	private Integer transferTravelTime = 0;
	private Integer totalTripCount = 0;
	private String destinationStopName;
	private Integer directionId;
	private final List<TravelTimeOutput> travelTimes = new ArrayList<>();

	public StopOutput(TravelTimeOutput travelTime) {
		super();
		this.stopId = travelTime.getStopId();
		this.stopName = travelTime.getStopName();
		this.stopLatitude = travelTime.getStopLatitude();
		this.stopLongitude = travelTime.getStopLongitude();
		this.agencyId = travelTime.getAgencyId();
		this.directionId = travelTime.getDirectionId();
		this.destinationStopName = travelTime.getDestinationStopName();
	}

	public void addTravelTime(TravelTimeOutput travelTime) {
		travelTimes.add(travelTime);
		travelTimeMinutes = (travelTimeMinutes.intValue() + travelTime.getTravelTimeMinutes().intValue()) / travelTimes.size();
		transferTravelTime = (transferTravelTime.intValue() + travelTime.getTransferTravelTime().intValue()) / travelTimes.size();
		totalTripCount += travelTime.getTotalTripCount().intValue();
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

	public String getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public Integer getTravelTimeMinutes() {
		return travelTimeMinutes;
	}

	public void setTravelTimeMinutes(Integer travelTimeMinutes) {
		this.travelTimeMinutes = travelTimeMinutes;
	}

	public Integer getTransferTravelTime() {
		return transferTravelTime;
	}

	public void setTransferTravelTime(Integer transferTravelTime) {
		this.transferTravelTime = transferTravelTime;
	}

	public Integer getTotalTripCount() {
		return totalTripCount;
	}

	public void setTotalTripCount(Integer totalTripCount) {
		this.totalTripCount = totalTripCount;
	}

	public String getDestinationStopName() {
		return destinationStopName;
	}

	public void setDestinationStopName(String destinationStopName) {
		this.destinationStopName = destinationStopName;
	}

	public Integer getDirectionId() {
		return directionId;
	}

	public void setDirectionId(Integer directionId) {
		this.directionId = directionId;
	}

	public List<TravelTimeOutput> getTravelTimes() {
		return travelTimes;
	}

}
