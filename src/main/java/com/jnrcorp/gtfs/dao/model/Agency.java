package com.jnrcorp.gtfs.dao.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@Entity
@Table(name="agency")
public class Agency extends DAOBaseObject implements Serializable {

	private static final long serialVersionUID = -4258816758410948772L;

	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;

	@Column(name="agency_id", length=10)
	private String agencyId;

	@Column(name="agency_name", length=60)
	private String agencyName;

	@Column(name="agency_url", length=300)
	private String agencyUrl;

	@Column(name="agency_timezone", length=30)
	private String agencyTimezone;

	@Column(name="agency_lang", length=4)
	private String agencyLanguage;

	@Column(name="agency_phone", length=30)
	private String agencyPhone;

	public Agency() {
		super();
	}

	@Override
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
		sb.append("agencyId", agencyId);
		sb.append("agencyName", agencyName);
		sb.append("agencyUrl", agencyUrl);
		sb.append("agencyTimezone", agencyTimezone);
		sb.append("agencyLanguage", agencyLanguage);
		sb.append("agencyPhone", agencyPhone);
		return super.toString();
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

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getAgencyUrl() {
		return agencyUrl;
	}

	public void setAgencyUrl(String agencyUrl) {
		this.agencyUrl = agencyUrl;
	}

	public String getAgencyTimezone() {
		return agencyTimezone;
	}

	public void setAgencyTimezone(String agencyTimezone) {
		this.agencyTimezone = agencyTimezone;
	}

	public String getAgencyLanguage() {
		return agencyLanguage;
	}

	public void setAgencyLanguage(String agencyLanguage) {
		this.agencyLanguage = agencyLanguage;
	}

	public String getAgencyPhone() {
		return agencyPhone;
	}

	public void setAgencyPhone(String agencyPhone) {
		this.agencyPhone = agencyPhone;
	}

}
