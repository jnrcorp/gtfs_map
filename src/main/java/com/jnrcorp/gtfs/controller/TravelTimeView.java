package com.jnrcorp.gtfs.controller;

import java.util.List;

import com.jnrcorp.gtfs.dao.view.TravelTimeOutput;

public class TravelTimeView {

	List<TravelTimeOutput> travelTimes;

	public TravelTimeView() {
		super();
	}

	public TravelTimeView(List<TravelTimeOutput> travelTimes) {
		super();
		this.travelTimes = travelTimes;
	}

	public List<TravelTimeOutput> getTravelTimes() {
		return travelTimes;
	}

	public void setTravelTimes(List<TravelTimeOutput> travelTimes) {
		this.travelTimes = travelTimes;
	}

}
