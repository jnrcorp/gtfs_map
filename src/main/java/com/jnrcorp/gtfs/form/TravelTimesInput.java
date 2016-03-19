package com.jnrcorp.gtfs.form;

public class TravelTimesInput {

	private Integer minTravelTime;
	private Integer maxTravelTime;

	public TravelTimesInput() {
		super();
	}

	public Integer getMinTravelTime() {
		return minTravelTime;
	}

	public void setMinTravelTime(Integer minTravelTime) {
		this.minTravelTime = minTravelTime;
	}

	public Integer getMaxTravelTime() {
		return maxTravelTime;
	}

	public void setMaxTravelTime(Integer maxTravelTime) {
		this.maxTravelTime = maxTravelTime;
	}

}
