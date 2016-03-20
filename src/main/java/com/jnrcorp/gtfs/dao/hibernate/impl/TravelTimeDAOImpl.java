package com.jnrcorp.gtfs.dao.hibernate.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jnrcorp.gtfs.dao.hibernate.TravelTimeDAO;
import com.jnrcorp.gtfs.dao.model.TravelTime;
import com.jnrcorp.gtfs.dao.view.TravelTimeOutput;
import com.jnrcorp.gtfs.util.model.Range;

@Repository("travelTimeDAO")
@Transactional
public class TravelTimeDAOImpl extends BaseDAOHibernate implements TravelTimeDAO {

	@Override
	public void generateTravelTimeToPABT() {
		List<Integer> pabtStopIds = Arrays.asList(3511, 43274, 43310);

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" 	CEIL(AVG(ABS(TIME_TO_SEC(st.arrival_time) - TIME_TO_SEC(st_pabt.arrival_time)) / 60)) as travelTimeMinutes, ");
		sql.append(" 	COUNT(st.stop_id) as totalTrips, ");
		sql.append(" 	t.route_id as routeId, ");
		sql.append(" 	t.direction_id as directionId, ");
		sql.append(" 	st.stop_id as fromStopId, ");
		sql.append(" 	st_pabt.stop_id as toStopId ");
		sql.append(" FROM stop_times st ");
		sql.append(" JOIN stops s ON st.stop_id = s.stop_id ");
		sql.append(" JOIN trips t ON st.trip_id = t.trip_id ");
		sql.append(" JOIN stop_times st_pabt ON st.trip_id = st_pabt.trip_id AND st_pabt.stop_id IN (:pabtStopIds) AND st_pabt.pickup_type = 0 ");
		sql.append(" WHERE st.stop_id NOT IN (:pabtStopIds) AND st.pickup_type = 0 ");
		sql.append(" GROUP BY t.route_id, t.direction_id, st.stop_id, st_pabt.stop_id ");

		SQLQuery query = createSQLQuery(sql.toString());
		query.setParameterList("pabtStopIds", pabtStopIds);
		query.setResultTransformer(new AliasToBeanResultTransformer(TravelTime.class));

		List<TravelTime> travelTimes = list(query);

		saveOrUpdateAll(travelTimes);
	}

	@Override
	public List<TravelTimeOutput> getTravelTimes(Collection<Integer> stopIds, Range<Integer> travelTimeRange) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" 	tt.travel_time_minutes as travelTimeMinutes, ");
		sql.append(" 	tt.total_trips as totalTripCount, ");
		sql.append(" 	tt.direction_id as directionId, ");
		sql.append(" 	s.stop_name as stopName, ");
		sql.append(" 	s.stop_lat as stopLatitude, ");
		sql.append(" 	s.stop_lon as stopLongitude, ");
		sql.append(" 	r.route_short_name as routeName, ");
		sql.append(" 	r.agency_id as agencyId ");
		sql.append(" FROM travel_times tt ");
		sql.append(" JOIN stops s ON tt.from_stop_id = s.stop_id ");
		sql.append(" JOIN routes r ON tt.route_id = r.route_id ");
		sql.append(" WHERE tt.to_stop_id IN (:stopIds) ");
		if (travelTimeRange.bothExist()) {
			sql.append(" AND travel_time_minutes BETWEEN :minTravelTime AND :maxTravelTime ");
		} else if (travelTimeRange.onlyMinExists()) {
			sql.append(" AND travel_time_minutes >= :minTravelTime ");
		} else if (travelTimeRange.onlyMaxExists()) {
			sql.append(" AND travel_time_minutes <= :maxTravelTime ");
		}

		SQLQuery query = createSQLQuery(sql.toString());
		query.setParameterList("stopIds", stopIds);
		if (travelTimeRange.bothExist()) {
			query.setParameter("minTravelTime", travelTimeRange.getMin());
			query.setParameter("maxTravelTime", travelTimeRange.getMax());
		} else if (travelTimeRange.onlyMinExists()) {
			query.setParameter("minTravelTime", travelTimeRange.getMin());
		} else if (travelTimeRange.onlyMaxExists()) {
			query.setParameter("maxTravelTime", travelTimeRange.getMax());
		}
		query.setResultTransformer(new AliasToBeanResultTransformer(TravelTimeOutput.class));
		return list(query);
	}

}
