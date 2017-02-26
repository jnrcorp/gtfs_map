package com.jnrcorp.gtfs.dao.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="travel_times")
public class TravelTime extends DAOBaseObject implements Serializable {

	private static final long serialVersionUID = -8578420745865450340L;

	@Id
	@Column(name="agency_id", length=10)
	private String agencyId;

	@Id
	@Column(name="route_id")
	private Integer routeId;

	@Id
	@Column(name="direction_id")
	private Integer directionId;

	@Id
	@Column(name="from_stop_id")
	private Integer fromStopId;

	@Id
	@Column(name="to_stop_id")
	private Integer toStopId;

	@Column(name="travel_time_minutes")
	private BigDecimal travelTimeMinutes;

	@Column(name="total_trips")
	private BigInteger totalTrips;

	@Id
	@Column(name="stop_sequence")
	private Integer stopSequence;

	public TravelTime() {
		super();
	}

	@Override
	public String getAgencyId() {
		return agencyId;
	}

	@Override
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public Integer getRouteId() {
		return routeId;
	}

	public void setRouteId(Integer routeId) {
		this.routeId = routeId;
	}

	public Integer getDirectionId() {
		return directionId;
	}

	public void setDirectionId(Integer directionId) {
		this.directionId = directionId;
	}

	public Integer getFromStopId() {
		return fromStopId;
	}

	public void setFromStopId(Integer fromStopId) {
		this.fromStopId = fromStopId;
	}

	public Integer getToStopId() {
		return toStopId;
	}

	public void setToStopId(Integer toStopId) {
		this.toStopId = toStopId;
	}

	public BigDecimal getTravelTimeMinutes() {
		return travelTimeMinutes;
	}

	public void setTravelTimeMinutes(BigDecimal travelTimeMinutes) {
		this.travelTimeMinutes = travelTimeMinutes;
	}

	public BigInteger getTotalTrips() {
		return totalTrips;
	}

	public void setTotalTrips(BigInteger totalTrips) {
		this.totalTrips = totalTrips;
	}

	public Integer getStopSequence() {
		return stopSequence;
	}

	public void setStopSequence(Integer stopSequence) {
		this.stopSequence = stopSequence;
	}

}
