package com.jnrcorp.gtfs.load;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;

import com.jnrcorp.gtfs.BaseJunitTest;
import com.jnrcorp.gtfs.dao.hibernate.TravelTimeDAO;

public class TravelTimeGeneration extends BaseJunitTest {

	@Autowired
	@Qualifier("travelTimeDAO")
	private TravelTimeDAO travelTimeDAO;

	@Test
	@Rollback(false)
	public void populateTravelTimesPABT() {
		travelTimeDAO.generateTravelTimeToPABT();
	}

}
