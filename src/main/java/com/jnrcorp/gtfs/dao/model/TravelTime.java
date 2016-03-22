package com.jnrcorp.gtfs.dao.model;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="travel_times")
public class TravelTime extends DAOBaseObject implements Serializable {

	private static final long serialVersionUID = -8578420745865450340L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;

	@Column(name="route_id")
	private Integer routeId;

	@Column(name="direction_id")
	private Integer directionId;

	@Column(name="from_stop_id")
	private Integer fromStopId;

	@Column(name="to_stop_id")
	private Integer toStopId;

	@Column(name="travel_time_minutes")
	private BigInteger travelTimeMinutes;

	@Column(name="total_trips")
	private BigInteger totalTrips;

	@Column(name="stop_sequence")
	private Integer stopSequence;

	public TravelTime() {
		super();
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
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

	public BigInteger getTravelTimeMinutes() {
		return travelTimeMinutes;
	}

	public void setTravelTimeMinutes(BigInteger travelTimeMinutes) {
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
