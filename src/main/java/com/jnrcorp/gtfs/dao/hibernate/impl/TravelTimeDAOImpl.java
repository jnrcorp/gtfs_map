package com.jnrcorp.gtfs.dao.hibernate.impl;

import java.util.Arrays;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jnrcorp.gtfs.dao.hibernate.TravelTimeDAO;
import com.jnrcorp.gtfs.dao.model.TravelTime;

@Repository("travelTimeDAO")
@Transactional
public class TravelTimeDAOImpl extends BaseDAOHibernate implements TravelTimeDAO {

	@Override
	public void generateTravelTimeToPABT() {
		List<Integer> pabtStopIds = Arrays.asList(3511, 43274, 43310);

		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" 	CEIL(AVG(ABS(TIME_TO_SEC(st.arrival_time) - TIME_TO_SEC(st_pabt.arrival_time)) / 60)) as travelTimeMinutes, ");
		sql.append(" 	st.stop_id as fromStopId, ");
		sql.append(" 	st_pabt.stop_id as toStopId ");
		sql.append(" FROM stop_times st ");
		sql.append(" JOIN stops s ON st.stop_id = s.stop_id ");
		sql.append(" JOIN stop_times st_pabt ON st.trip_id = st_pabt.trip_id AND st_pabt.stop_id IN (:pabtStopIds) AND st_pabt.pickup_type = 0 ");
		sql.append(" WHERE st.stop_id NOT IN (:pabtStopIds) AND st.pickup_type = 0 ");
		sql.append(" GROUP BY st.stop_id ");

		SQLQuery query = createSQLQuery(sql.toString());
		query.setParameterList("pabtStopIds", pabtStopIds);
		query.setResultTransformer(new AliasToBeanResultTransformer(TravelTime.class));

		List<TravelTime> travelTimes = list(query);

		saveOrUpdateAll(travelTimes);
	}

}
