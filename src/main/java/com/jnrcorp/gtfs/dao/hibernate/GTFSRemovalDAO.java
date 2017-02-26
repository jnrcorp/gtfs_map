package com.jnrcorp.gtfs.dao.hibernate;

import java.util.List;

import com.jnrcorp.gtfs.dao.model.Agency;
import com.jnrcorp.gtfs.dao.model.CalendarDate;
import com.jnrcorp.gtfs.dao.model.Route;
import com.jnrcorp.gtfs.dao.model.Stop;
import com.jnrcorp.gtfs.dao.model.StopTime;
import com.jnrcorp.gtfs.dao.model.Trip;

public interface GTFSRemovalDAO extends BaseObjectDAO {

	Agency getAgencyToRemove(String agencyId);

	List<Route> getRoutesByAgency(String agencyId);

	List<CalendarDate> getCalendarDatesByAgency(String agencyId);

	List<Trip> getTripsByAgency(String agencyId);

	List<Stop> getStopsByAgency(String agencyId);

	List<StopTime> getStopTimesByAgency(String agencyId);

}
