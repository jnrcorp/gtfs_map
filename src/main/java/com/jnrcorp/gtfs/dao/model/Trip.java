package com.jnrcorp.gtfs.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jnrcorp.gtfs.dao.types.DirectionId;

@Entity
@Table(name="trips")
public class Trip extends DAOBaseObject implements Serializable {

	private static final long serialVersionUID = -3341920712045406626L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;

	@Column(name="route_id")
	private Integer routeId;

	@Column(name="service_id")
	private Integer serviceId;

	@Column(name="trip_id")
	private Integer tripId;

	@Column(name="trip_headsign", length=120)
	private String tripHeadsign;

	@Column(name="direction_id")
	@Type(type="com.jnrcorp.gtfs.dao.hibernate.userTypes.EnumUserType", parameters={
		@Parameter(name="enumClass",value="com.jnrcorp.gtfs.dao.types.DirectionId"),
		@Parameter(name="identifierMethod",value="getIdAsString"),
		@Parameter(name="valueOfMethod",value="getById")
	})
	private DirectionId directionId;

	@Column(name="block_id", length=20)
	private String blockId;

	@Column(name="shape_id")
	private Integer shapeId;

	public Trip() {
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

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public Integer getTripId() {
		return tripId;
	}

	public void setTripId(Integer tripId) {
		this.tripId = tripId;
	}

	public String getTripHeadsign() {
		return tripHeadsign;
	}

	public void setTripHeadsign(String tripHeadsign) {
		this.tripHeadsign = tripHeadsign;
	}

	public DirectionId getDirectionId() {
		return directionId;
	}

	public void setDirectionId(DirectionId directionId) {
		this.directionId = directionId;
	}

	public String getBlockId() {
		return blockId;
	}

	public void setBlockId(String blockId) {
		this.blockId = blockId;
	}

	public Integer getShapeId() {
		return shapeId;
	}

	public void setShapeId(Integer shapeId) {
		this.shapeId = shapeId;
	}

}
