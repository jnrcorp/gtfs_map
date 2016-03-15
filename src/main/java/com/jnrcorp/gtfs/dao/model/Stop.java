package com.jnrcorp.gtfs.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="stops")
public class Stop extends DAOBaseObject implements Serializable {

	private static final long serialVersionUID = 2387475817451862090L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;

	@Column(name="stop_id")
	private Integer stopId;

	@Column(name="stop_code", length=20)
	private String stopCode;

	@Column(name="stop_name", length=60)
	private String stopName;

	@Column(name="stop_desc", length=60)
	private String stopDescription;

	@Column(name="stop_lat")
	private Double stopLatitute;

	@Column(name="stop_lon")
	private Double stopLongitude;

	@Column(name="zone_id")
	private Integer zoneId;

	public Stop() {
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

	public Integer getStopId() {
		return stopId;
	}

	public void setStopId(Integer stopId) {
		this.stopId = stopId;
	}

	public String getStopCode() {
		return stopCode;
	}

	public void setStopCode(String stopCode) {
		this.stopCode = stopCode;
	}

	public String getStopName() {
		return stopName;
	}

	public void setStopName(String stopName) {
		this.stopName = stopName;
	}

	public String getStopDescription() {
		return stopDescription;
	}

	public void setStopDescription(String stopDescription) {
		this.stopDescription = stopDescription;
	}

	public Double getStopLatitute() {
		return stopLatitute;
	}

	public void setStopLatitute(Double stopLatitute) {
		this.stopLatitute = stopLatitute;
	}

	public Double getStopLongitude() {
		return stopLongitude;
	}

	public void setStopLongitude(Double stopLongitude) {
		this.stopLongitude = stopLongitude;
	}

	public Integer getZoneId() {
		return zoneId;
	}

	public void setZoneId(Integer zoneId) {
		this.zoneId = zoneId;
	}

}
