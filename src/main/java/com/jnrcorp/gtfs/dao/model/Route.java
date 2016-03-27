package com.jnrcorp.gtfs.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.jnrcorp.gtfs.dao.types.RouteType;

@Entity
@Table(name="routes")
public class Route extends DAOBaseObject implements Serializable {

	private static final long serialVersionUID = 6606080633970192144L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;

	@Column(name="route_id")
	private Integer routeId;

	@Column(name="agency_id", length=10)
	private String agencyId;

	@Column(name="route_short_name", length=10)
	private String routeShortName;

	@Column(name="route_long_name", length=60)
	private String routeLongName;

	@Column(name="route_type")
	@Type(type="com.jnrcorp.gtfs.dao.hibernate.userTypes.EnumUserType", parameters={
		@Parameter(name="enumClass",value="com.jnrcorp.gtfs.dao.types.RouteType"),
		@Parameter(name="identifierMethod",value="getIdAsString"),
		@Parameter(name="valueOfMethod",value="getById")
	})
	private RouteType routeType;

	@Column(name="route_url", length=300)
	private String routeUrl;

	@Column(name="route_color", length=10)
	private String routeColor;

	public Route() {
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

	@Override
	public String getAgencyId() {
		return agencyId;
	}

	@Override
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
	}

	public String getRouteShortName() {
		return routeShortName;
	}

	public void setRouteShortName(String routeShortName) {
		this.routeShortName = routeShortName;
	}

	public String getRouteLongName() {
		return routeLongName;
	}

	public void setRouteLongName(String routeLongName) {
		this.routeLongName = routeLongName;
	}

	public RouteType getRouteType() {
		return routeType;
	}

	public void setRouteType(RouteType routeType) {
		this.routeType = routeType;
	}

	public String getRouteUrl() {
		return routeUrl;
	}

	public void setRouteUrl(String routeUrl) {
		this.routeUrl = routeUrl;
	}

	public String getRouteColor() {
		return routeColor;
	}

	public void setRouteColor(String routeColor) {
		this.routeColor = routeColor;
	}

}
