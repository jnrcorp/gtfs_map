package com.jnrcorp.gtfs.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="shapes")
public class Shape extends DAOBaseObject implements Serializable {

	private static final long serialVersionUID = 843673252400564723L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;

	@Column(name="shape_id")
	private Integer shapeId;

	@Column(name="shape_pt_lat")
	private Double shapePointLatitute;

	@Column(name="shape_pt_lon")
	private Double shapePointLongitude;

	@Column(name="shape_pt_sequence")
	private Integer shapePointSequence;

	@Column(name="shape_dist_traveled")
	private Double shapeDistanceTraveled;

	public Shape() {
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

	public Integer getShapeId() {
		return shapeId;
	}

	public void setShapeId(Integer shapeId) {
		this.shapeId = shapeId;
	}

	public Double getShapePointLatitute() {
		return shapePointLatitute;
	}

	public void setShapePointLatitute(Double shapePointLatitute) {
		this.shapePointLatitute = shapePointLatitute;
	}

	public Double getShapePointLongitude() {
		return shapePointLongitude;
	}

	public void setShapePointLongitude(Double shapePointLongitude) {
		this.shapePointLongitude = shapePointLongitude;
	}

	public Integer getShapePointSequence() {
		return shapePointSequence;
	}

	public void setShapePointSequence(Integer shapePointSequence) {
		this.shapePointSequence = shapePointSequence;
	}

	public Double getShapeDistanceTraveled() {
		return shapeDistanceTraveled;
	}

	public void setShapeDistanceTraveled(Double shapeDistanceTraveled) {
		this.shapeDistanceTraveled = shapeDistanceTraveled;
	}

}
