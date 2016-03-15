package com.jnrcorp.gtfs.dao.hibernate.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
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
		Criteria criteria = createCriteria(Route.class);
		criteria.add(Restrictions.eq("agencyId", agencyId));
		return list(criteria);
	}

	@Override
	public List<CalendarDate> getCalendarDatesByAgency(String agencyId) {
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT cd FROM CalendarDate cd, Trip t, Route r ");
		hql.append(" WHERE t.agencyId = :agencyId ");
		hql.append(" AND t.routeId = r.routeId ");
		hql.append(" AND cd.serviceId = t.serviceId ");
		Query query = createQuery(hql.toString());
		query.setParameter("agencyId", agencyId);
		return list(query);
	}

	@Override
	public List<Shape> getShapesByAgency(String agencyId) {
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT s FROM Shape s, Trip t, Route r ");
		hql.append(" WHERE t.agencyId = :agencyId ");
		hql.append(" AND t.routeId = r.routeId ");
		hql.append(" AND t.shapeId = s.shapeId ");
		Query query = createQuery(hql.toString());
		query.setParameter("agencyId", agencyId);
		return list(query);
	}

	@Override
	public List<Trip> getTripsByAgency(String agencyId) {
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT t FROM Trip t, Route r ");
		hql.append(" WHERE t.agencyId = :agencyId ");
		hql.append(" AND t.routeId = r.routeId ");
		Query query = createQuery(hql.toString());
		query.setParameter("agencyId", agencyId);
		return list(query);
	}

	@Override
	public List<Stop> getStopsByAgency(String agencyId) {
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT s FROM Trip t, Route r, StopTime st, Stop s ");
		hql.append(" WHERE t.agencyId = :agencyId ");
		hql.append(" AND t.routeId = r.routeId ");
		hql.append(" AND st.tripId = r.tripId ");
		hql.append(" AND s.stopId = s.stopId ");
		Query query = createQuery(hql.toString());
		query.setParameter("agencyId", agencyId);
		return list(query);
	}

	@Override
	public List<StopTime> getStopTimesByAgency(String agencyId) {
		StringBuilder hql = new StringBuilder();
		hql.append(" SELECT st FROM Trip t, Route r, StopTime st ");
		hql.append(" WHERE t.agencyId = :agencyId ");
		hql.append(" AND t.routeId = r.routeId ");
		hql.append(" AND st.tripId = r.tripId ");
		Query query = createQuery(hql.toString());
		query.setParameter("agencyId", agencyId);
		return list(query);
	}

}
