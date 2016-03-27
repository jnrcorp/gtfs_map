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

import com.jnrcorp.gtfs.dao.types.ExceptionType;

@Entity
@Table(name="calendar_dates")
public class CalendarDate extends DAOBaseObject implements Serializable {

	private static final long serialVersionUID = 1120588925047476957L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;

	@Column(name="agency_id", length=10)
	private String agencyId;

	@Column(name="service_id")
	private Integer serviceId;

	@Column(name="date")
	private Date date;

	@Column(name="exception_type")
	@Type(type="com.jnrcorp.gtfs.dao.hibernate.userTypes.EnumUserType", parameters={
		@Parameter(name="enumClass",value="com.jnrcorp.gtfs.dao.types.ExceptionType"),
		@Parameter(name="identifierMethod",value="getIdAsString"),
		@Parameter(name="valueOfMethod",value="getById")
	})
	private ExceptionType exceptionType;

	public CalendarDate() {
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

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public ExceptionType getExceptionType() {
		return exceptionType;
	}

	public void setExceptionType(ExceptionType exceptionType) {
		this.exceptionType = exceptionType;
	}

}
