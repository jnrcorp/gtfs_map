package com.jnrcorp.gtfs.controller.view;

import java.util.List;

import com.jnrcorp.gtfs.dao.view.TravelTimeOutput;

public class TravelTimeView {

	private String routeName;
	private Integer directionId;
	private List<TravelTimeOutput> travelTimes;

	public TravelTimeView() {
		super();
	}

	public String getRouteName() {
		return routeName;
	}

	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}

	public Integer getDirectionId() {
		return directionId;
	}

	public void setDirectionId(Integer directionId) {
		this.directionId = directionId;
	}

	public List<TravelTimeOutput> getTravelTimes() {
		return travelTimes;
	}

	public void setTravelTimes(List<TravelTimeOutput> travelTimes) {
		this.travelTimes = travelTimes;
	}

}
