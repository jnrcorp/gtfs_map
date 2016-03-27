package com.jnrcorp.gtfs.controller;

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

import com.jnrcorp.gtfs.dao.hibernate.TravelTimeDAO;

@Controller
public class ImportController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImportController.class);

	@Autowired
	@Qualifier("travelTimeDAO")
	private TravelTimeDAO travelTimeDAO;

	@RequestMapping(method=RequestMethod.GET, value="generateTravelTime")
	@ResponseBody
	public String generateTravelTime(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		travelTimeDAO.clearTravelTimes();
		LOGGER.info("Clear Travel Times data.");
		travelTimeDAO.generateTravelTimeToPABT();
		LOGGER.info("Finished generating PABT data.");
		travelTimeDAO.generateTravelTimeToNYPenn();
		LOGGER.info("Finished generating NY Penn data.");
		return "loadData";
	}

}
