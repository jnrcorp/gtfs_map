package com.jnrcorp.gtfs.dao.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jnrcorp.gtfs.dao.types.DropOffType;
import com.jnrcorp.gtfs.dao.types.PickupType;

@Entity
@Table(name="stop_times")
public class StopTime extends DAOBaseObject implements Serializable {

	private static final long serialVersionUID = -5158950334477280302L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;

	@Column(name="agency_id", length=10)
	private String agencyId;

	@Column(name="trip_id")
	private Integer tripId;

	@Column(name="arrival_time")
	private Date arrivalTime;

	@Column(name="departure_time")
	private Date departureTime;

	@Column(name="stop_id")
	private Integer stopId;

	@Column(name="stop_sequence")
	private Integer stopSequence;

	@Column(name="pickup_type")
	@Type(type="com.jnrcorp.gtfs.dao.hibernate.userTypes.EnumUserType", parameters={
		@Parameter(name="enumClass",value="com.jnrcorp.gtfs.dao.types.PickupType"),
		@Parameter(name="identifierMethod",value="getIdAsString"),
		@Parameter(name="valueOfMethod",value="getById")
	})
	private PickupType pickupType;

	@Column(name="drop_off_type")
	@Type(type="com.jnrcorp.gtfs.dao.hibernate.userTypes.EnumUserType", parameters={
		@Parameter(name="enumClass",value="com.jnrcorp.gtfs.dao.types.DropOffType"),
		@Parameter(name="identifierMethod",value="getIdAsString"),
		@Parameter(name="valueOfMethod",value="getById")
	})
	private DropOffType dropOffType;

	@Column(name="shape_dist_traveled")
	private Double shapeDistanceTraveled;

	public StopTime() {
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

	@Override
	public String getAgencyId() {
		return agencyId;
	}

	@Override
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public Integer getTripId() {
		return tripId;
	}

	public void setTripId(Integer tripId) {
		this.tripId = tripId;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public Integer getStopId() {
		return stopId;
	}

	public void setStopId(Integer stopId) {
		this.stopId = stopId;
	}

	public Integer getStopSequence() {
		return stopSequence;
	}

	public void setStopSequence(Integer stopSequence) {
		this.stopSequence = stopSequence;
	}

	public PickupType getPickupType() {
		return pickupType;
	}

	public void setPickupType(PickupType pickupType) {
		this.pickupType = pickupType;
	}

	public DropOffType getDropOffType() {
		return dropOffType;
	}

	public void setDropOffType(DropOffType dropOffType) {
		this.dropOffType = dropOffType;
	}

	public Double getShapeDistanceTraveled() {
		return shapeDistanceTraveled;
	}

	public void setShapeDistanceTraveled(Double shapeDistanceTraveled) {
		this.shapeDistanceTraveled = shapeDistanceTraveled;
	}

}
