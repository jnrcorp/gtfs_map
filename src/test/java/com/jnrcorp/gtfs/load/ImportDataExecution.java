package com.jnrcorp.gtfs.load;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jnrcorp.gtfs.dao.model.Agency;
import com.jnrcorp.gtfs.dao.model.CalendarDate;
import com.jnrcorp.gtfs.dao.model.Route;
import com.jnrcorp.gtfs.dao.model.Shape;
import com.jnrcorp.gtfs.dao.model.Stop;
import com.jnrcorp.gtfs.dao.model.StopTime;
import com.jnrcorp.gtfs.dao.model.Trip;
import com.jnrcorp.gtfs.load.service.ImportDataService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:applicationContext-resources.xml",
		"classpath:applicationContext-hibernate.xml",
		"classpath:applicationContext-services.xml",
})
@Transactional
public class ImportDataExecution {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImportDataExecution.class);

	@Autowired
	@Qualifier("importDataService")
	private ImportDataService importDataService;

	private static File gtfsIngestionFile1 = new File("C:\\Users\\Joshua\\Downloads\\bus_data\\agency.csv");
	private static File gtfsIngestionFile2 = new File("C:\\Users\\Joshua\\Downloads\\bus_data\\routes.csv");
	private static File gtfsIngestionFile3 = new File("C:\\Users\\Joshua\\Downloads\\bus_data\\calendar_dates.csv");
	private static File gtfsIngestionFile4 = new File("C:\\Users\\Joshua\\Downloads\\bus_data\\shapes.csv");
	private static File gtfsIngestionFile5 = new File("C:\\Users\\Joshua\\Downloads\\bus_data\\trips.csv");
	private static File gtfsIngestionFile6 = new File("C:\\Users\\Joshua\\Downloads\\bus_data\\stops.csv");
	private static File gtfsIngestionFile7 = new File("C:\\Users\\Joshua\\Downloads\\bus_data\\stop_times.csv");

	@Test
	@Rollback(false)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void ingestGTFS() {
		importDataService.loadData(Agency.class, gtfsIngestionFile1);
		LOGGER.info("Finished file 1");
		importDataService.loadData(Route.class, gtfsIngestionFile2);
		LOGGER.info("Finished file 2");
		importDataService.loadData(CalendarDate.class, gtfsIngestionFile3);
		LOGGER.info("Finished file 3");
		importDataService.loadData(Shape.class, gtfsIngestionFile4);
		LOGGER.info("Finished file 4");
		importDataService.loadData(Trip.class, gtfsIngestionFile5);
		LOGGER.info("Finished file 5");
		importDataService.loadData(Stop.class, gtfsIngestionFile6);
		LOGGER.info("Finished file 6");
		importDataService.loadData(StopTime.class, gtfsIngestionFile7);
		LOGGER.info("Finished file 7");
	}

}
