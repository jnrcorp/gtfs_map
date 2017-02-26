package com.jnrcorp.gtfs.load;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

import com.jnrcorp.gtfs.dao.model.Agency;
import com.jnrcorp.gtfs.dao.model.CalendarDate;
import com.jnrcorp.gtfs.dao.model.DAOBaseObject;
import com.jnrcorp.gtfs.dao.model.Route;
import com.jnrcorp.gtfs.dao.model.Shape;
import com.jnrcorp.gtfs.dao.model.Stop;
import com.jnrcorp.gtfs.dao.model.StopTime;
import com.jnrcorp.gtfs.dao.model.Trip;
import com.jnrcorp.gtfs.dao.types.DirectionId;
import com.jnrcorp.gtfs.dao.types.DropOffType;
import com.jnrcorp.gtfs.dao.types.ExceptionType;
import com.jnrcorp.gtfs.dao.types.PickupType;
import com.jnrcorp.gtfs.dao.types.RouteType;

public class GTFSRowImport {

	public interface FieldSetter<T extends DAOBaseObject> {
		void setValue(T object, String value);
	}

	private interface AgencyFieldSetter extends FieldSetter<Agency> {
		@Override
		void setValue(Agency agency, String value);
	}

	private interface CalendarDateFieldSetter extends FieldSetter<CalendarDate> {
		@Override
		void setValue(CalendarDate calendarDate, String value);
	}

	private interface RouteFieldSetter extends FieldSetter<Route> {
		@Override
		void setValue(Route route, String value);
	}

	private interface ShapeFieldSetter extends FieldSetter<Shape> {
		@Override
		void setValue(Shape shape, String value);
	}

	private interface StopFieldSetter extends FieldSetter<Stop> {
		@Override
		void setValue(Stop stop, String value);
	}

	private interface StopTimeFieldSetter extends FieldSetter<StopTime> {
		@Override
		void setValue(StopTime stopTime, String value);
	}

	private interface TripFieldSetter extends FieldSetter<Trip> {
		@Override
		void setValue(Trip trip, String value);
	}

	private static Map<String, AgencyFieldSetter> agencyFieldsByColumnHeader = new HashMap<>();
	private static Map<String, CalendarDateFieldSetter> calendarDateFieldsByColumnHeader = new HashMap<>();
	private static Map<String, RouteFieldSetter> routeFieldsByColumnHeader = new HashMap<>();
	private static Map<String, ShapeFieldSetter> shapeFieldsByColumnHeader = new HashMap<>();
	private static Map<String, StopFieldSetter> stopFieldsByColumnHeader = new HashMap<>();
	private static Map<String, StopTimeFieldSetter> stopTimeFieldsByColumnHeader = new HashMap<>();
	private static Map<String, TripFieldSetter> tripFieldsByColumnHeader = new HashMap<>();

	public static Map<Class<? extends DAOBaseObject>, Map<String, ? extends FieldSetter<?>>> fieldSettersByClass = new HashMap<>();

	static {
		agencyFieldsByColumnHeader.put(GTFSImportConstants.HEADER_AGENCY_ID, (agency, value) -> agency.setAgencyId(value));
		agencyFieldsByColumnHeader.put(GTFSImportConstants.HEADER_AGENCY_NAME, (agency, value) -> agency.setAgencyName(value));
		agencyFieldsByColumnHeader.put(GTFSImportConstants.HEADER_AGENCY_URL, (agency, value) -> agency.setAgencyUrl(value));
		agencyFieldsByColumnHeader.put(GTFSImportConstants.HEADER_AGENCY_TIMEZONE, (agency, value) -> agency.setAgencyTimezone(value));
		agencyFieldsByColumnHeader.put(GTFSImportConstants.HEADER_AGENCY_LANGUAGE, (agency, value) -> agency.setAgencyLanguage(value));
		agencyFieldsByColumnHeader.put(GTFSImportConstants.HEADER_AGENCY_PHONE, (agency, value) -> agency.setAgencyPhone(value));

		calendarDateFieldsByColumnHeader.put(GTFSImportConstants.HEADER_SERVICE_ID, (calendarDate, value) -> calendarDate.setServiceId(Integer.valueOf(value)));
		calendarDateFieldsByColumnHeader.put(GTFSImportConstants.HEADER_DATE, (calendarDate, value) -> calendarDate.setDate(GTFSRowImport.parseDate(value)));
		calendarDateFieldsByColumnHeader.put(GTFSImportConstants.HEADER_EXCEPTION_TYPE, (calendarDate, value) -> calendarDate.setExceptionType(ExceptionType.getById(value)));

		routeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_ROUTE_ID, (route, value) -> route.setRouteId(Integer.valueOf(value)));
		routeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_AGENCY_ID, (route, value) -> route.setAgencyId(value));
		routeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_ROUTE_SHORT_NAME, (route, value) -> route.setRouteShortName(value));
		routeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_ROUTE_LONG_NAME, (route, value) -> route.setRouteLongName(value));
		routeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_ROUTE_TYPE, (route, value) -> route.setRouteType(RouteType.getById(value)));
		routeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_ROUTE_URL, (route, value) -> route.setRouteUrl(value));
		routeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_ROUTE_COLOR, (route, value) -> route.setRouteColor(value));

		shapeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_SHAPE_ID, (shape, value) -> shape.setShapeId(Integer.valueOf(value)));
		shapeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_SHAPE_POINT_LATITUDE, (shape, value) -> shape.setShapePointLatitute(Double.valueOf(value)));
		shapeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_SHAPE_POINT_LONGITUDE, (shape, value) -> shape.setShapePointLongitude(Double.valueOf(value)));
		shapeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_SHAPE_POINT_SEQUENCE, (shape, value) -> shape.setShapePointSequence(Integer.valueOf(value)));
		shapeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_SHAPE_DISTANCE_TRAVELED, (shape, value) -> shape.setShapeDistanceTraveled(Double.valueOf(value)));

		stopFieldsByColumnHeader.put(GTFSImportConstants.HEADER_STOP_ID, (stop, value) -> stop.setStopId(Integer.valueOf(value)));
		stopFieldsByColumnHeader.put(GTFSImportConstants.HEADER_STOP_CODE, (stop, value) -> stop.setStopCode(value));
		stopFieldsByColumnHeader.put(GTFSImportConstants.HEADER_STOP_NAME, (stop, value) -> stop.setStopName(value));
		stopFieldsByColumnHeader.put(GTFSImportConstants.HEADER_STOP_DESCRIPTION, (stop, value) -> stop.setStopDescription(value));
		stopFieldsByColumnHeader.put(GTFSImportConstants.HEADER_STOP_LATITUDE, (stop, value) -> stop.setStopLatitute(Double.valueOf(value)));
		stopFieldsByColumnHeader.put(GTFSImportConstants.HEADER_STOP_LONGITUDE, (stop, value) -> stop.setStopLongitude(Double.valueOf(value)));
		stopFieldsByColumnHeader.put(GTFSImportConstants.HEADER_ZONE_ID, (stop, value) -> stop.setZoneId(Integer.valueOf(value)));

		stopTimeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_TRIP_ID, (stopTime, value) -> stopTime.setTripId(Integer.valueOf(value)));
		stopTimeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_ARRIVAL_TIME, (stopTime, value) -> stopTime.setArrivalTime(GTFSRowImport.parseTime(value)));
		stopTimeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_DEPARTURE_TIME, (stopTime, value) -> stopTime.setDepartureTime(GTFSRowImport.parseTime(value)));
		stopTimeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_STOP_ID, (stopTime, value) -> stopTime.setStopId(Integer.valueOf(value)));
		stopTimeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_STOP_SEQUENCE, (stopTime, value) -> stopTime.setStopSequence(Integer.valueOf(value)));
		stopTimeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_PICKUP_TYPE, (stopTime, value) -> stopTime.setPickupType(PickupType.getById(value)));
		stopTimeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_DROP_OFF_TYPE, (stopTime, value) -> stopTime.setDropOffType(DropOffType.getById(value)));
		stopTimeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_SHAPE_DISTANCE_TRAVELED, (stopTime, value) -> stopTime.setShapeDistanceTraveled(Double.valueOf(value)));

		tripFieldsByColumnHeader.put(GTFSImportConstants.HEADER_ROUTE_ID, (trip, value) -> trip.setRouteId(Integer.valueOf(value)));
		tripFieldsByColumnHeader.put(GTFSImportConstants.HEADER_SERVICE_ID, (trip, value) -> trip.setServiceId(Integer.valueOf(value)));
		tripFieldsByColumnHeader.put(GTFSImportConstants.HEADER_TRIP_ID, (trip, value) -> trip.setTripId(Integer.valueOf(value)));
		tripFieldsByColumnHeader.put(GTFSImportConstants.HEADER_TRIP_HEADSIGN, (trip, value) -> trip.setTripHeadsign(value));
		tripFieldsByColumnHeader.put(GTFSImportConstants.HEADER_DIRECTION_ID, (trip, value) -> trip.setDirectionId(DirectionId.getById(value)));
		tripFieldsByColumnHeader.put(GTFSImportConstants.HEADER_BLOCK_ID, (trip, value) -> trip.setBlockId(value));
		tripFieldsByColumnHeader.put(GTFSImportConstants.HEADER_SHAPE_ID, (trip, value) -> trip.setShapeId(Integer.valueOf(value)));

		fieldSettersByClass.put(Agency.class, agencyFieldsByColumnHeader);
		fieldSettersByClass.put(CalendarDate.class, calendarDateFieldsByColumnHeader);
		fieldSettersByClass.put(Route.class, routeFieldsByColumnHeader);
		fieldSettersByClass.put(Shape.class, shapeFieldsByColumnHeader);
		fieldSettersByClass.put(Stop.class, stopFieldsByColumnHeader);
		fieldSettersByClass.put(StopTime.class, stopTimeFieldsByColumnHeader);
		fieldSettersByClass.put(Trip.class, tripFieldsByColumnHeader);
	}

	private static Date parseDate(String dateStr) {
		String[] datePatterns = { "yyyyMMdd" };
		try {
			return DateUtils.parseDate(dateStr, datePatterns);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static Date parseTime(String dateStr) {
		String[] datePatterns = { "HH:mm:ss" };
		try {
			return DateUtils.parseDate(dateStr, datePatterns);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
