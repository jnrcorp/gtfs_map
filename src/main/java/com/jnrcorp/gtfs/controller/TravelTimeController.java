package com.jnrcorp.gtfs.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jnrcorp.gtfs.dao.hibernate.TravelTimeDAO;
import com.jnrcorp.gtfs.dao.view.TravelTimeOutput;
import com.jnrcorp.gtfs.form.TravelTimesInput;
import com.jnrcorp.gtfs.util.model.Range;

@Controller
public class TravelTimeController {

	@Autowired
	@Qualifier("travelTimeDAO")
	private TravelTimeDAO travelTimeDAO;

	@RequestMapping(method=RequestMethod.GET, value="travelTimes")
	public TravelTimeView travelTimes(HttpServletRequest request, HttpServletResponse response, ModelMap model, RedirectAttributes redirectAttributes, TravelTimesInput travelTimesInput) {
		List<Integer> stopIds = Arrays.asList(3511, 43274, 43310);
		Range<Integer> travelTimeRange = new Range<>(travelTimesInput.getMinTravelTime(), travelTimesInput.getMaxTravelTime());
		List<TravelTimeOutput> travelTimes = travelTimeDAO.getTravelTimes(stopIds, travelTimeRange);
		TravelTimeView travelTimeView = new TravelTimeView(travelTimes);
		return travelTimeView;
	}

}
