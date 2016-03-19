package com.jnrcorp.gtfs.dao.hibernate;

import java.util.Collection;
import java.util.List;

import com.jnrcorp.gtfs.dao.view.TravelTimeOutput;
import com.jnrcorp.gtfs.util.model.Range;

public interface TravelTimeDAO extends BaseObjectDAO {

	void generateTravelTimeToPABT();

	List<TravelTimeOutput> getTravelTimes(Collection<Integer> stopIds, Range<Integer> travelTimeRange);

}
