package com.jnrcorp.gtfs.load;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;

import com.jnrcorp.gtfs.BaseJunitTest;
import com.jnrcorp.gtfs.dao.hibernate.GTFSRemovalDAO;
import com.jnrcorp.gtfs.dao.model.Agency;
import com.jnrcorp.gtfs.dao.model.CalendarDate;
import com.jnrcorp.gtfs.dao.model.Route;

public class GTFSRemovalDAOTest extends BaseJunitTest {

	@Autowired
	@Qualifier("gtfsRemovalDAO")
	private GTFSRemovalDAO gtfsRemovalDAO;

	private String agencyId = "NJT";

	@Test
	public void testGetAgencyToRemove() {
		Agency agency = gtfsRemovalDAO.getAgencyToRemove(agencyId);
		Assert.notNull(agency);
	}

	@Test
	public void testGetRoutesByAgency() {
		List<Route> routes = gtfsRemovalDAO.getRoutesByAgency(agencyId);
		Assert.notEmpty(routes);
	}

	@Test
	public void testGetCalendarDatesByAgency() {
		List<CalendarDate> calendarDates = gtfsRemovalDAO.getCalendarDatesByAgency(agencyId);
		Assert.notEmpty(calendarDates);
	}

}
