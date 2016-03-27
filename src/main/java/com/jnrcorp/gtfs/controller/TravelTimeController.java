package com.jnrcorp.gtfs.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jnrcorp.gtfs.dao.hibernate.TravelTimeDAO;
import com.jnrcorp.gtfs.dao.view.StopOutput;
import com.jnrcorp.gtfs.dao.view.TravelTimeOutput;
import com.jnrcorp.gtfs.form.TravelTimesInput;
import com.jnrcorp.gtfs.util.model.Range;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;

@Controller
public class TravelTimeController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TravelTimeController.class);

	@Autowired
	@Qualifier("travelTimeDAO")
	private TravelTimeDAO travelTimeDAO;

	@RequestMapping(method=RequestMethod.GET, value="logback")
	@ResponseBody
	public String logback(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		// print internal state
	    LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
	    StatusPrinter.print(lc);
		return "loadData";
	}

	@RequestMapping(method=RequestMethod.GET, value="travelTimes")
	@ResponseBody
	public List<StopOutput> travelTimes(HttpServletRequest request, HttpServletResponse response, ModelMap model, RedirectAttributes redirectAttributes, TravelTimesInput travelTimesInput) {
		LOGGER.info("Loading travel times.");
		List<Integer> nyStopIds = Arrays.asList(3511, 43274, 43310, 105);
		Range<Integer> travelTimeRange = new Range<>(travelTimesInput.getMinTravelTime(), travelTimesInput.getMaxTravelTime());
		List<TravelTimeOutput> travelTimes = travelTimeDAO.getTravelTimes(nyStopIds, travelTimeRange);
		List<TravelTimeOutput> transferStopTravelTimes = travelTimeDAO.getTravelTimesOneTransfer(Arrays.asList(105), travelTimeRange);
		travelTimes.addAll(transferStopTravelTimes);
		Map<String, StopOutput> stopOutputsByStopId = new HashMap<>();
		for (TravelTimeOutput travelTimeOutput : travelTimes) {
			String key = travelTimeOutput.getDirectionId() + "" + travelTimeOutput.getStopId();
			StopOutput stopOutput = stopOutputsByStopId.get(key);
			if (stopOutput == null) {
				stopOutput = new StopOutput(travelTimeOutput);
				stopOutputsByStopId.put(key, stopOutput);
			} else {
				stopOutput.addTravelTime(travelTimeOutput);
			}
		}
		List<StopOutput> stopOutputs = new ArrayList<>(stopOutputsByStopId.values());
		return stopOutputs;
	}

}
