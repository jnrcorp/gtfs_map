package com.jnrcorp.gtfs.dao.hibernate.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jnrcorp.gtfs.dao.hibernate.GTFSRemovalDAO;
import com.jnrcorp.gtfs.dao.model.Agency;
import com.jnrcorp.gtfs.dao.model.CalendarDate;
import com.jnrcorp.gtfs.dao.model.Route;
import com.jnrcorp.gtfs.dao.model.Shape;
import com.jnrcorp.gtfs.dao.model.Stop;
import com.jnrcorp.gtfs.dao.model.StopTime;
import com.jnrcorp.gtfs.dao.model.Trip;

@Repository("gtfsRemovalDAO")
@Transactional
public class GTFSRemovalDAOImpl extends BaseDAOHibernate implements GTFSRemovalDAO {

	@Override
	public Agency getAgencyToRemove(String agencyId) {
		Criteria criteria = createCriteria(Agency.class);
		criteria.add(Restrictions.eq("agencyId", agencyId));
		return uniqueResult(criteria);
	}

	@Override
	public List<Route> getRoutesByAgency(String agencyId) {
		return null;
	}

	@Override
	public List<CalendarDate> getCalendarDatesByAgency(String agencyId) {
		return null;
	}

	@Override
	public List<Shape> getShapesByAgency(String agencyId) {
		return null;
	}

	@Override
	public List<Trip> getTripsByAgency(String agencyId) {
		return null;
	}

	@Override
	public List<Stop> getStopsByAgency(String agencyId) {
		Criteria criteria = createCriteria(Stop.class);
		return null;
	}

	@Override
	public List<StopTime> getStopTimesByAgency(String agencyId) {
		return null;
	}

}
