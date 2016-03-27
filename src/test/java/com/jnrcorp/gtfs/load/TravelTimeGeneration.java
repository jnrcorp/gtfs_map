package com.jnrcorp.gtfs.load;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;

import com.jnrcorp.gtfs.BaseJunitTest;
import com.jnrcorp.gtfs.dao.hibernate.TravelTimeDAO;

public class TravelTimeGeneration extends BaseJunitTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(TravelTimeGeneration.class);

	@Autowired
	@Qualifier("travelTimeDAO")
	private TravelTimeDAO travelTimeDAO;

	@Test
	@Rollback(false)
	public void populateTravelTimesPABT() {
		travelTimeDAO.clearTravelTimes();
		LOGGER.info("Clear Travel Times data.");
		travelTimeDAO.generateTravelTimeToPABT();
		LOGGER.info("Finished generating PABT data.");
		travelTimeDAO.generateTravelTimeToNYPenn();
		LOGGER.info("Finished generating NY Penn data.");
	}

}
