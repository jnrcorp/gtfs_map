-- Are there areny stops that depart at a different time then they arrive.
SELECT * FROM gtfs.stop_times WHERE arrival_time <> departure_time;

-- All stops at the PABT
SELECT * FROM stops WHERE stop_name LIKE 'PORT AUTH%';

-- Trips that stop at the PABT
SELECT DISTINCT(trip_id) FROM stop_times WHERE stop_id IN (3511,43274,43310) AND pickup_type = 0;

-- All stop times on trips that start at the PABT that are NOT the PABT
-- All stops on trips that start at the PABT that are NOT the PABT
SELECT
	(TIME_TO_SEC(st.arrival_time) - TIME_TO_SEC(st_pabt.arrival_time)) / 60 as travel_time_minutes,
	st.stop_id,
    s.stop_name,
    s.stop_lat,
    s.stop_lon,
    st.arrival_time as stop_time,
    st_pabt.stop_id as pabt_stop_id,
    st_pabt.arrival_time as pabt_arrival_time
FROM stop_times st
JOIN trips t ON st.trip_id AND t.trip_id AND t.direction_id = 0
JOIN stop_times st_pabt ON st.trip_id = st_pabt.trip_id AND st_pabt.stop_id IN (3511,43274,43310) AND st_pabt.pickup_type = 0
JOIN stops s ON st.stop_id = s.stop_id
WHERE
	st.stop_id NOT IN (3511,43274,43310) AND st.pickup_type = 0
	AND (TIME_TO_SEC(st.arrival_time) - TIME_TO_SEC(st_pabt.arrival_time)) / 60 BETWEEN -60 AND 60;

-- Average travel time from all stops that go to/from PABT
SELECT
	AVG(ABS(TIME_TO_SEC(st.arrival_time) - TIME_TO_SEC(st_pabt.arrival_time)) / 60) as travel_time_minutes,
	st.stop_id
FROM stop_times st
JOIN stops s ON st.stop_id = s.stop_id
JOIN stop_times st_pabt ON st.trip_id = st_pabt.trip_id AND st_pabt.stop_id IN (3511,43274,43310) AND st_pabt.pickup_type = 0
WHERE
	st.stop_id NOT IN (3511,43274,43310) AND st.pickup_type = 0
GROUP BY
	st.stop_id;

