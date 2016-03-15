package com.jnrcorp.gtfs.load;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

import com.jnrcorp.gtfs.dao.hibernate.BaseObjectDAO;
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
		void setValue(T object, String value, BaseObjectDAO baseObjectDAO);
	}

	private interface AgencyFieldSetter extends FieldSetter<Agency> {
		@Override
		void setValue(Agency agency, String value, BaseObjectDAO baseObjectDAO);
	}

	private interface CalendarDateFieldSetter extends FieldSetter<CalendarDate> {
		@Override
		void setValue(CalendarDate calendarDate, String value, BaseObjectDAO baseObjectDAO);
	}

	private interface RouteFieldSetter extends FieldSetter<Route> {
		@Override
		void setValue(Route route, String value, BaseObjectDAO baseObjectDAO);
	}

	private interface ShapeFieldSetter extends FieldSetter<Shape> {
		@Override
		void setValue(Shape shape, String value, BaseObjectDAO baseObjectDAO);
	}

	private interface StopFieldSetter extends FieldSetter<Stop> {
		@Override
		void setValue(Stop stop, String value, BaseObjectDAO baseObjectDAO);
	}

	private interface StopTimeFieldSetter extends FieldSetter<StopTime> {
		@Override
		void setValue(StopTime stopTime, String value, BaseObjectDAO baseObjectDAO);
	}

	private interface TripFieldSetter extends FieldSetter<Trip> {
		@Override
		void setValue(Trip trip, String value, BaseObjectDAO baseObjectDAO);
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
		agencyFieldsByColumnHeader.put(GTFSImportConstants.HEADER_AGENCY_ID, (agency, value, baseObjectDAO) -> agency.setAgencyId(value));
		agencyFieldsByColumnHeader.put(GTFSImportConstants.HEADER_AGENCY_NAME, (agency, value, baseObjectDAO) -> agency.setAgencyName(value));
		agencyFieldsByColumnHeader.put(GTFSImportConstants.HEADER_AGENCY_URL, (agency, value, baseObjectDAO) -> agency.setAgencyUrl(value));
		agencyFieldsByColumnHeader.put(GTFSImportConstants.HEADER_AGENCY_TIMEZONE, (agency, value, baseObjectDAO) -> agency.setAgencyTimezone(value));
		agencyFieldsByColumnHeader.put(GTFSImportConstants.HEADER_AGENCY_LANGUAGE, (agency, value, baseObjectDAO) -> agency.setAgencyLanguage(value));
		agencyFieldsByColumnHeader.put(GTFSImportConstants.HEADER_AGENCY_PHONE, (agency, value, baseObjectDAO) -> agency.setAgencyPhone(value));

		calendarDateFieldsByColumnHeader.put(GTFSImportConstants.HEADER_SERVICE_ID, (calendarDate, value, baseObjectDAO) -> calendarDate.setServiceId(Integer.valueOf(value)));
		calendarDateFieldsByColumnHeader.put(GTFSImportConstants.HEADER_DATE, (calendarDate, value, baseObjectDAO) -> calendarDate.setDate(GTFSRowImport.parseDate(value)));
		calendarDateFieldsByColumnHeader.put(GTFSImportConstants.HEADER_EXCEPTION_TYPE, (calendarDate, value, baseObjectDAO) -> calendarDate.setExceptionType(ExceptionType.getById(value)));

		routeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_ROUTE_ID, (route, value, baseObjectDAO) -> route.setRouteId(Integer.valueOf(value)));
		routeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_AGENCY_ID, (route, value, baseObjectDAO) -> route.setAgencyId(value));
		routeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_ROUTE_SHORT_NAME, (route, value, baseObjectDAO) -> route.setRouteShortName(value));
		routeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_ROUTE_LONG_NAME, (route, value, baseObjectDAO) -> route.setRouteLongName(value));
		routeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_ROUTE_TYPE, (route, value, baseObjectDAO) -> route.setRouteType(RouteType.getById(value)));
		routeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_ROUTE_URL, (route, value, baseObjectDAO) -> route.setRouteUrl(value));
		routeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_ROUTE_COLOR, (route, value, baseObjectDAO) -> route.setRouteColor(value));

		shapeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_SHAPE_ID, (shape, value, baseObjectDAO) -> shape.setShapeId(Integer.valueOf(value)));
		shapeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_SHAPE_POINT_LATITUDE, (shape, value, baseObjectDAO) -> shape.setShapePointLatitute(Double.valueOf(value)));
		shapeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_SHAPE_POINT_LONGITUDE, (shape, value, baseObjectDAO) -> shape.setShapePointLongitude(Double.valueOf(value)));
		shapeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_SHAPE_POINT_SEQUENCE, (shape, value, baseObjectDAO) -> shape.setShapePointSequence(Integer.valueOf(value)));
		shapeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_SHAPE_DISTANCE_TRAVELED, (shape, value, baseObjectDAO) -> shape.setShapeDistanceTraveled(Double.valueOf(value)));

		stopFieldsByColumnHeader.put(GTFSImportConstants.HEADER_STOP_ID, (stop, value, baseObjectDAO) -> stop.setStopId(Integer.valueOf(value)));
		stopFieldsByColumnHeader.put(GTFSImportConstants.HEADER_STOP_CODE, (stop, value, baseObjectDAO) -> stop.setStopCode(value));
		stopFieldsByColumnHeader.put(GTFSImportConstants.HEADER_STOP_NAME, (stop, value, baseObjectDAO) -> stop.setStopName(value));
		stopFieldsByColumnHeader.put(GTFSImportConstants.HEADER_STOP_DESCRIPTION, (stop, value, baseObjectDAO) -> stop.setStopDescription(value));
		stopFieldsByColumnHeader.put(GTFSImportConstants.HEADER_STOP_LATITUDE, (stop, value, baseObjectDAO) -> stop.setStopLatitute(Double.valueOf(value)));
		stopFieldsByColumnHeader.put(GTFSImportConstants.HEADER_STOP_LONGITUDE, (stop, value, baseObjectDAO) -> stop.setStopLongitude(Double.valueOf(value)));
		stopFieldsByColumnHeader.put(GTFSImportConstants.HEADER_ZONE_ID, (stop, value, baseObjectDAO) -> stop.setZoneId(Integer.valueOf(value)));

		stopTimeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_TRIP_ID, (stopTime, value, baseObjectDAO) -> stopTime.setTripId(Integer.valueOf(value)));
		stopTimeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_ARRIVAL_TIME, (stopTime, value, baseObjectDAO) -> stopTime.setArrivalTime(GTFSRowImport.parseTime(value)));
		stopTimeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_DEPARTURE_TIME, (stopTime, value, baseObjectDAO) -> stopTime.setDepartureTime(GTFSRowImport.parseTime(value)));
		stopTimeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_STOP_ID, (stopTime, value, baseObjectDAO) -> stopTime.setStopId(Integer.valueOf(value)));
		stopTimeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_STOP_SEQUENCE, (stopTime, value, baseObjectDAO) -> stopTime.setStopSequence(Integer.valueOf(value)));
		stopTimeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_PICKUP_TYPE, (stopTime, value, baseObjectDAO) -> stopTime.setPickupType(PickupType.getById(value)));
		stopTimeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_DROP_OFF_TYPE, (stopTime, value, baseObjectDAO) -> stopTime.setDropOffType(DropOffType.getById(value)));
		stopTimeFieldsByColumnHeader.put(GTFSImportConstants.HEADER_SHAPE_DISTANCE_TRAVELED, (stopTime, value, baseObjectDAO) -> stopTime.setShapeDistanceTraveled(Double.valueOf(value)));

		tripFieldsByColumnHeader.put(GTFSImportConstants.HEADER_ROUTE_ID, (trip, value, baseObjectDAO) -> trip.setRouteId(Integer.valueOf(value)));
		tripFieldsByColumnHeader.put(GTFSImportConstants.HEADER_SERVICE_ID, (trip, value, baseObjectDAO) -> trip.setServiceId(Integer.valueOf(value)));
		tripFieldsByColumnHeader.put(GTFSImportConstants.HEADER_TRIP_ID, (trip, value, baseObjectDAO) -> trip.setTripId(Integer.valueOf(value)));
		tripFieldsByColumnHeader.put(GTFSImportConstants.HEADER_TRIP_HEADSIGN, (trip, value, baseObjectDAO) -> trip.setTripHeadsign(value));
		tripFieldsByColumnHeader.put(GTFSImportConstants.HEADER_DIRECTION_ID, (trip, value, baseObjectDAO) -> trip.setDirectionId(DirectionId.getById(value)));
		tripFieldsByColumnHeader.put(GTFSImportConstants.HEADER_BLOCK_ID, (trip, value, baseObjectDAO) -> trip.setBlockId(value));
		tripFieldsByColumnHeader.put(GTFSImportConstants.HEADER_SHAPE_ID, (trip, value, baseObjectDAO) -> trip.setShapeId(Integer.valueOf(value)));

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
