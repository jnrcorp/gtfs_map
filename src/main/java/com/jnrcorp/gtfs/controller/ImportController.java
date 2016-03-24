package com.jnrcorp.gtfs.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jnrcorp.gtfs.dao.model.Agency;
import com.jnrcorp.gtfs.dao.model.CalendarDate;
import com.jnrcorp.gtfs.dao.model.Route;
import com.jnrcorp.gtfs.dao.model.Shape;
import com.jnrcorp.gtfs.dao.model.Stop;
import com.jnrcorp.gtfs.dao.model.StopTime;
import com.jnrcorp.gtfs.dao.model.TravelTime;
import com.jnrcorp.gtfs.dao.model.Trip;
import com.jnrcorp.gtfs.load.ImportDataExecution;
import com.jnrcorp.gtfs.load.service.ImportDataService;

@Controller
public class ImportController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImportDataExecution.class);

	@Autowired
	@Qualifier("importDataService")
	private ImportDataService importDataService;

	private static File gtfsIngestionFile1 = new File("bus_data/agency.txt");
	private static File gtfsIngestionFile2 = new File("bus_data/routes.txt");
	private static File gtfsIngestionFile3 = new File("bus_data/calendar_dates.txt");
	private static File gtfsIngestionFile4 = new File("bus_data/shapes.txt");
	private static File gtfsIngestionFile5 = new File("bus_data/trips.txt");
	private static File gtfsIngestionFile6 = new File("bus_data/stops.txt");
	private static File gtfsIngestionFile7 = new File("bus_data/stop_times.txt");

	@RequestMapping(method=RequestMethod.GET, value="loadData")
	@ResponseBody
	public String loadData(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
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
		return "loadData";
	}

	@RequestMapping(method=RequestMethod.GET, value="clearData")
	@ResponseBody
	public String clearData(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		importDataService.removeData(TravelTime.class);
		importDataService.removeData(StopTime.class);
		importDataService.removeData(Stop.class);
		importDataService.removeData(Trip.class);
		importDataService.removeData(Shape.class);
		importDataService.removeData(CalendarDate.class);
		importDataService.removeData(Route.class);
		importDataService.removeData(Agency.class);
		return "clearData";
	}

}
