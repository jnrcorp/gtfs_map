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
	public void clearTravelTimes() {
		String sql = "DELETE FROM travel_times";
		SQLQuery query = createSQLQuery(sql);
		query.executeUpdate();
	}

	@Override
	public void generateTravelTimeToPABT() {

		List<Integer> pabtStopIds = Arrays.asList(3511, 43274, 43310);

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" 	CEIL(AVG(ABS(TIMESTAMPDIFF(MINUTE, st.arrival_time, st_pabt.arrival_time)))) as travelTimeMinutes, ");
		sql.append(" 	COUNT(st.stop_id) as totalTrips, ");
		sql.append(" 	t.route_id as routeId, ");
		sql.append(" 	t.direction_id as directionId, ");
		sql.append(" 	st.stop_id as fromStopId, ");
		sql.append(" 	st_pabt.stop_id as toStopId, ");
		sql.append(" 	st.stop_sequence as stopSequence, ");
		sql.append(" 	st.agency_id as agencyId ");
		sql.append(" FROM stop_times st ");
		sql.append(" JOIN stops s ON st.stop_id = s.stop_id AND st.agency_id = s.agency_id ");
		sql.append(" JOIN trips t ON st.trip_id = t.trip_id AND st.agency_id = t.agency_id ");
		sql.append(" JOIN stop_times st_pabt ON st.trip_id = st_pabt.trip_id AND st.agency_id = st_pabt.agency_id AND st_pabt.stop_id IN (:pabtStopIds) AND st_pabt.pickup_type = 0 ");
		sql.append(" WHERE st.stop_id NOT IN (:pabtStopIds) AND st.pickup_type = 0 ");
		sql.append(" GROUP BY t.route_id, t.direction_id, st.stop_id, st_pabt.stop_id ");

		SQLQuery query = createSQLQuery(sql.toString());
		query.setParameterList("pabtStopIds", pabtStopIds);
		query.setResultTransformer(new AliasToBeanResultTransformer(TravelTime.class));

		List<TravelTime> travelTimes = list(query);

		saveOrUpdateAll(travelTimes);
	}

	@Override
	public void generateTravelTimeToNYPenn() {

		// 105 - NY Penn
		// 107 - Newark Penn
		// 38174, 38187 - Secaucus Junction
		List<Integer> njTransitStopIds = Arrays.asList(105);

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" 	CEIL(AVG(ABS(TIMESTAMPDIFF(MINUTE, st.arrival_time, st_pabt.arrival_time)))) as travelTimeMinutes, ");
		sql.append(" 	COUNT(st.stop_id) as totalTrips, ");
		sql.append(" 	t.route_id as routeId, ");
		sql.append(" 	t.direction_id as directionId, ");
		sql.append(" 	st.stop_id as fromStopId, ");
		sql.append(" 	st_pabt.stop_id as toStopId, ");
		sql.append(" 	st.stop_sequence as stopSequence, ");
		sql.append(" 	st.agency_id as agencyId ");
		sql.append(" FROM stop_times st ");
		sql.append(" JOIN stops s ON st.stop_id = s.stop_id AND st.agency_id = s.agency_id ");
		sql.append(" JOIN trips t ON st.trip_id = t.trip_id AND st.agency_id = t.agency_id ");
		sql.append(" JOIN stop_times st_pabt ON st.trip_id = st_pabt.trip_id AND st.agency_id = st_pabt.agency_id AND st_pabt.stop_id IN (:njTransitStopIds) AND st_pabt.pickup_type = 0 ");
		sql.append(" WHERE st.stop_id NOT IN (:njTransitStopIds) AND st.pickup_type = 0 ");
		sql.append(" GROUP BY t.route_id, t.direction_id, st.stop_id, st_pabt.stop_id ");

		SQLQuery query = createSQLQuery(sql.toString());
		query.setParameterList("njTransitStopIds", njTransitStopIds);
		query.setResultTransformer(new AliasToBeanResultTransformer(TravelTime.class));

		List<TravelTime> travelTimes = list(query);

		saveOrUpdateAll(travelTimes);

		generateTimesToTransferStops();
	}

	private void generateTimesToTransferStops() {

		// 107 - Newark Penn
		// 38174, 38187 - Secaucus Junction
		List<Integer> njTransitStopIds = Arrays.asList(107, 38174, 38187);

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" 	CEIL(AVG(ABS(TIMESTAMPDIFF(MINUTE, st.arrival_time, st_pabt.arrival_time)))) as travelTimeMinutes, ");
		sql.append(" 	COUNT(st.stop_id) as totalTrips, ");
		sql.append(" 	t.route_id as routeId, ");
		sql.append(" 	t.direction_id as directionId, ");
		sql.append(" 	st.stop_id as fromStopId, ");
		sql.append(" 	st_pabt.stop_id as toStopId, ");
		sql.append(" 	st.stop_sequence as stopSequence, ");
		sql.append(" 	st.agency_id as agencyId ");
		sql.append(" FROM stop_times st ");
		sql.append(" JOIN stops s ON st.stop_id = s.stop_id AND st.agency_id = s.agency_id ");
		sql.append(" JOIN trips t ON st.trip_id = t.trip_id AND st.agency_id = t.agency_id ");
		sql.append(" JOIN stop_times st_pabt ON st.trip_id = st_pabt.trip_id AND st.agency_id = st_pabt.agency_id AND st_pabt.stop_id IN (:njTransitStopIds) AND st_pabt.pickup_type = 0 ");
		sql.append(" WHERE st.stop_id NOT IN (:njTransitStopIds) AND st.pickup_type = 0 ");
		sql.append(" GROUP BY t.route_id, t.direction_id, st.stop_id, st_pabt.stop_id ");

		SQLQuery query = createSQLQuery(sql.toString());
		query.setParameterList("njTransitStopIds", njTransitStopIds);
		query.setResultTransformer(new AliasToBeanResultTransformer(TravelTime.class));

		List<TravelTime> travelTimes = list(query);

		saveOrUpdateAll(travelTimes);
	}

	@Override
	public List<TravelTimeOutput> getTravelTimes(Collection<Integer> destinationStopIds, Range<Integer> travelTimeRange) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" 	tt.travel_time_minutes as travelTimeMinutes, ");
		sql.append(" 	tt.total_trips as totalTripCount, ");
		sql.append(" 	tt.direction_id as directionId, ");
		sql.append(" 	s.stop_id as stopId, ");
		sql.append(" 	s.stop_name as stopName, ");
		sql.append(" 	s.stop_lat as stopLatitude, ");
		sql.append(" 	s.stop_lon as stopLongitude, ");
		sql.append(" 	CASE WHEN r.agency_id = 'NJT' THEN r.route_long_name ELSE r.route_short_name END AS routeName, ");
		sql.append(" 	r.agency_id as agencyId ");
		sql.append(" FROM travel_times tt ");
		sql.append(" JOIN stops s ON tt.from_stop_id = s.stop_id AND tt.agency_id = s.agency_id ");
		sql.append(" JOIN routes r ON tt.route_id = r.route_id AND tt.agency_id = r.agency_id ");
		sql.append(" WHERE tt.to_stop_id IN (:stopIds) ");
		if (travelTimeRange.bothExist()) {
			sql.append(" AND travel_time_minutes BETWEEN :minTravelTime AND :maxTravelTime ");
		} else if (travelTimeRange.onlyMinExists()) {
			sql.append(" AND travel_time_minutes >= :minTravelTime ");
		} else if (travelTimeRange.onlyMaxExists()) {
			sql.append(" AND travel_time_minutes <= :maxTravelTime ");
		}

		SQLQuery query = createSQLQuery(sql.toString());
		query.setParameterList("stopIds", destinationStopIds);
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

	@Override
	public List<TravelTimeOutput> getTravelTimesOneTransfer(Collection<Integer> destinationStopIds, Range<Integer> travelTimeRange) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" 	CEIL(AVG(transfer.travel_time_minutes)) as travelTimeMinutes, ");
		sql.append("	CEIL(AVG(destination.travel_time_minutes)) as transferTravelTime, ");
		sql.append(" 	SUM(transfer.total_trips) as totalTripCount, ");
		sql.append(" 	transfer.direction_id as directionId, ");
		sql.append(" 	s.stop_id as stopId, ");
		sql.append(" 	s.stop_name as stopName, ");
		sql.append(" 	s.stop_lat as stopLatitude, ");
		sql.append(" 	s.stop_lon as stopLongitude, ");
		sql.append(" 	CASE WHEN r.agency_id = 'NJT' THEN r.route_long_name ELSE r.route_short_name END AS routeName, ");
		sql.append(" 	r.agency_id as agencyId ");
		sql.append(" FROM travel_times transfer ");
		sql.append(" JOIN stops transfer_to ON transfer_to.stop_id = transfer.to_stop_id AND transfer_to.agency_id = transfer.agency_id ");
		sql.append(" JOIN stops destination_from ON transfer_to.stop_lat = destination_from.stop_lat AND transfer_to.stop_lon = destination_from.stop_lon AND transfer_to.agency_id = destination_from.agency_id AND destination_from.stop_id NOT IN (:destinationStopIds) ");
		sql.append(" JOIN travel_times destination ON destination.from_stop_id = destination_from.stop_id AND destination.agency_id = destination_from.agency_id ");
		sql.append(" JOIN stops s ON transfer.from_stop_id = s.stop_id AND transfer.agency_id = s.agency_id AND s.stop_id NOT IN (:destinationStopIds) ");
		sql.append(" JOIN routes r ON transfer.route_id = r.route_id AND transfer.agency_id = r.agency_id ");
		sql.append(" WHERE destination.to_stop_id IN (:destinationStopIds) ");
		if (travelTimeRange.bothExist()) {
			sql.append(" AND transfer.travel_time_minutes + destination.travel_time_minutes BETWEEN :minTravelTime AND :maxTravelTime ");
		} else if (travelTimeRange.onlyMinExists()) {
			sql.append(" AND transfer.travel_time_minutes + destination.travel_time_minutes >= :minTravelTime ");
		} else if (travelTimeRange.onlyMaxExists()) {
			sql.append(" AND transfer.travel_time_minutes + destination.travel_time_minutes <= :maxTravelTime ");
		}
		sql.append(" GROUP BY s.stop_id, transfer.direction_id ");

		SQLQuery query = createSQLQuery(sql.toString());
		query.setParameterList("destinationStopIds", destinationStopIds);
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
