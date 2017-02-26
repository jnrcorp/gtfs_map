package com.jnrcorp.gtfs.load;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jnrcorp.gtfs.BaseJunitTest;
import com.jnrcorp.gtfs.dao.model.Agency;
import com.jnrcorp.gtfs.dao.model.CalendarDate;
import com.jnrcorp.gtfs.dao.model.Route;
import com.jnrcorp.gtfs.dao.model.Stop;
import com.jnrcorp.gtfs.dao.model.StopTime;
import com.jnrcorp.gtfs.dao.model.TravelTime;
import com.jnrcorp.gtfs.dao.model.Trip;
import com.jnrcorp.gtfs.load.service.ImportDataService;

public class ImportDataExecution extends BaseJunitTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImportDataExecution.class);

	@Autowired
	@Qualifier("importDataService")
	private ImportDataService importDataService;

	private static final String busDataLocation = "C:\\Users\\Joshua\\Downloads\\bus_data\\";
	private static final String railDataLocation = "C:\\Users\\Joshua\\Downloads\\rail_data\\";

	private static final String agencyFileName = "agency.txt";
	private static final String routesFileName = "routes.txt";
	private static final String calendarDatesFileName = "calendar_dates.txt";
	private static final String shapesFileName = "shapes.txt";
	private static final String tripsFileName = "trips.txt";
	private static final String stopsFileName = "stops.txt";
	private static final String stopTimesFileName = "stop_times.txt";

	private static File gtfsIngestionFileRail1 = new File(railDataLocation + agencyFileName);
	private static File gtfsIngestionFileRail2 = new File(railDataLocation + routesFileName);
	private static File gtfsIngestionFileRail3 = new File(railDataLocation + calendarDatesFileName);
	private static File gtfsIngestionFileRail4 = new File(railDataLocation + shapesFileName);
	private static File gtfsIngestionFileRail5 = new File(railDataLocation + tripsFileName);
	private static File gtfsIngestionFileRail6 = new File(railDataLocation + stopsFileName);
	private static File gtfsIngestionFileRail7 = new File(railDataLocation + stopTimesFileName);

	private static File gtfsIngestionFileBus1 = new File(busDataLocation + agencyFileName);
	private static File gtfsIngestionFileBus2 = new File(busDataLocation + routesFileName);
	private static File gtfsIngestionFileBus3 = new File(busDataLocation + calendarDatesFileName);
	private static File gtfsIngestionFileBus4 = new File(busDataLocation + shapesFileName);
	private static File gtfsIngestionFileBus5 = new File(busDataLocation + tripsFileName);
	private static File gtfsIngestionFileBus6 = new File(busDataLocation + stopsFileName);
	private static File gtfsIngestionFileBus7 = new File(busDataLocation + stopTimesFileName);

	@Test
	@Rollback(false)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void clearGTFS() {
		importDataService.removeData(TravelTime.class);
		importDataService.removeData(StopTime.class);
		importDataService.removeData(Stop.class);
		importDataService.removeData(Trip.class);
		importDataService.removeData(CalendarDate.class);
		importDataService.removeData(Route.class);
		importDataService.removeData(Agency.class);
	}

	@Test
	@Rollback(false)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void ingestGTFS() throws ReflectiveOperationException, IOException {
		importDataService.loadData(Agency.class, gtfsIngestionFileRail1, null);
		LOGGER.info("Finished Rail 1");
		importDataService.loadData(Route.class, gtfsIngestionFileRail2, "NJT");
		LOGGER.info("Finished Rail 2");
		importDataService.loadData(CalendarDate.class, gtfsIngestionFileRail3, "NJT");
		LOGGER.info("Finished Rail 3");
//		importDataService.loadData(Shape.class, gtfsIngestionFileRail4, "NJT");
//		LOGGER.info("Finished Rail 4");
		importDataService.loadData(Trip.class, gtfsIngestionFileRail5, "NJT");
		LOGGER.info("Finished Rail 5");
		importDataService.loadData(Stop.class, gtfsIngestionFileRail6, "NJT");
		LOGGER.info("Finished Rail 6");
		importDataService.loadData(StopTime.class, gtfsIngestionFileRail7, "NJT");
		LOGGER.info("Finished Rail 7");
//		importDataService.loadData(Route.class, gtfsIngestionFileBus1, "NJB");
//		LOGGER.info("Finished Bus 1");
		importDataService.loadData(Route.class, gtfsIngestionFileBus2, "NJB");
		LOGGER.info("Finished Bus 2");
		importDataService.loadData(CalendarDate.class, gtfsIngestionFileBus3, "NJB");
		LOGGER.info("Finished Bus 3");
//		importDataService.loadData(Shape.class, gtfsIngestionFileBus4, "NJB");
//		LOGGER.info("Finished Bus 4");
		importDataService.loadData(Trip.class, gtfsIngestionFileBus5, "NJB");
		LOGGER.info("Finished Bus 5");
		importDataService.loadData(Stop.class, gtfsIngestionFileBus6, "NJB");
		LOGGER.info("Finished Bus 6");
		importDataService.loadData(StopTime.class, gtfsIngestionFileBus7, "NJB");
		LOGGER.info("Finished Bus 7");
	}

}
