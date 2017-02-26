package com.jnrcorp.gtfs.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Entity
@Table(name="shapes")
public class Shape extends DAOBaseObject implements Serializable {

	private static final long serialVersionUID = 843673252400564723L;

	@Id
	@Column(name="agency_id", length=10)
	private String agencyId;

	@Id
	@Column(name="shape_id")
	private Integer shapeId;

	@Column(name="shape_pt_lat")
	private Double shapePointLatitute;

	@Column(name="shape_pt_lon")
	private Double shapePointLongitude;

	@Id
	@Column(name="shape_pt_sequence")
	private Integer shapePointSequence;

	@Column(name="shape_dist_traveled")
	private Double shapeDistanceTraveled;

	public Shape() {
		super();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Shape)) {
			return false;
		}
		Shape other = (Shape) obj;
		EqualsBuilder eb = new EqualsBuilder();
		eb.append(agencyId, other.agencyId);
		eb.append(shapeId, other.shapeId);
		eb.append(shapePointSequence, other.shapePointSequence);
		return eb.isEquals();
	}

	@Override
	public int hashCode() {
		HashCodeBuilder hcb = new HashCodeBuilder();
		hcb.append(agencyId);
		hcb.append(shapeId);
		hcb.append(shapePointSequence);
		return hcb.toHashCode();
	}

	@Override
	public String getAgencyId() {
		return agencyId;
	}

	@Override
	public void setAgencyId(String agencyId) {
		this.agencyId = agencyId;
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
