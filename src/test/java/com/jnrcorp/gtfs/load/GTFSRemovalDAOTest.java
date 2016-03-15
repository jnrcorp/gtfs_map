package com.jnrcorp.gtfs.load;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.jnrcorp.gtfs.dao.hibernate.GTFSRemovalDAO;
import com.jnrcorp.gtfs.dao.model.Agency;
import com.jnrcorp.gtfs.dao.model.CalendarDate;
import com.jnrcorp.gtfs.dao.model.Route;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:applicationContext-resources.xml",
		"classpath:applicationContext-hibernate.xml",
		"classpath:applicationContext-services.xml",
})
@Transactional
public class GTFSRemovalDAOTest {

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
