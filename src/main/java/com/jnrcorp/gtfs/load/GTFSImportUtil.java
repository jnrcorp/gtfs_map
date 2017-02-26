package com.jnrcorp.gtfs.load;

import java.util.ArrayList;
import java.util.List;

public class GTFSImportUtil {

	private final static String[] IMPORT_HEADER = new String[] {
		GTFSImportConstants.HEADER_AGENCY_ID,
		GTFSImportConstants.HEADER_AGENCY_LANGUAGE,
		GTFSImportConstants.HEADER_AGENCY_NAME,
		GTFSImportConstants.HEADER_AGENCY_PHONE,
		GTFSImportConstants.HEADER_AGENCY_TIMEZONE,
		GTFSImportConstants.HEADER_AGENCY_URL,
		GTFSImportConstants.HEADER_ARRIVAL_TIME,
		GTFSImportConstants.HEADER_BLOCK_ID,
		GTFSImportConstants.HEADER_DATE,
		GTFSImportConstants.HEADER_DEPARTURE_TIME,
		GTFSImportConstants.HEADER_DIRECTION_ID,
		GTFSImportConstants.HEADER_DROP_OFF_TYPE,
		GTFSImportConstants.HEADER_EXCEPTION_TYPE,
		GTFSImportConstants.HEADER_PICKUP_TYPE,
		GTFSImportConstants.HEADER_ROUTE_COLOR,
		GTFSImportConstants.HEADER_ROUTE_ID,
		GTFSImportConstants.HEADER_ROUTE_LONG_NAME,
		GTFSImportConstants.HEADER_ROUTE_SHORT_NAME,
		GTFSImportConstants.HEADER_ROUTE_TYPE,
		GTFSImportConstants.HEADER_ROUTE_URL,
		GTFSImportConstants.HEADER_SERVICE_ID,
		GTFSImportConstants.HEADER_STOP_CODE,
		GTFSImportConstants.HEADER_STOP_DESCRIPTION,
		GTFSImportConstants.HEADER_STOP_ID,
		GTFSImportConstants.HEADER_STOP_LATITUDE,
		GTFSImportConstants.HEADER_STOP_LONGITUDE,
		GTFSImportConstants.HEADER_STOP_NAME,
		GTFSImportConstants.HEADER_STOP_SEQUENCE,
		GTFSImportConstants.HEADER_TRIP_HEADSIGN,
		GTFSImportConstants.HEADER_TRIP_ID,
		GTFSImportConstants.HEADER_ZONE_ID
	};

	public static String[] getHeader() {
		List<String> headList = new ArrayList<>();
		for (String header : IMPORT_HEADER) {
			headList.add(header);
		}
		return headList.toArray(new String[0]);
	}

}
