package com.jnrcorp.gtfs.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jnrcorp.gtfs.controller.view.TravelTimeView;
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
	@ResponseBody
	public List<TravelTimeView> travelTimes(HttpServletRequest request, HttpServletResponse response, ModelMap model, RedirectAttributes redirectAttributes, TravelTimesInput travelTimesInput) {
		List<Integer> stopIds = Arrays.asList(3511, 43274, 43310);
		Range<Integer> travelTimeRange = new Range<>(travelTimesInput.getMinTravelTime(), travelTimesInput.getMaxTravelTime());
		List<TravelTimeOutput> travelTimes = travelTimeDAO.getTravelTimes(stopIds, travelTimeRange);
		Map<String, List<TravelTimeOutput>> timeViewsByRoute = new HashMap<>();
		for (TravelTimeOutput travelTimeOutput : travelTimes) {
			String key = travelTimeOutput.getRouteName() + "," + travelTimeOutput.getDirectionId();
			List<TravelTimeOutput> travelTimeOutputs = timeViewsByRoute.get(key);
			if (travelTimeOutputs == null) {
				travelTimeOutputs = new ArrayList<>();
				timeViewsByRoute.put(key, travelTimeOutputs);
			}
			travelTimeOutputs.add(travelTimeOutput);
		}
		List<TravelTimeView> travelTimeViews = new ArrayList<>();
		for (Map.Entry<String, List<TravelTimeOutput>> entry : timeViewsByRoute.entrySet()) {
			TravelTimeView travelTimeView = new TravelTimeView();
			String[] key = entry.getKey().split(",");
			List<TravelTimeOutput> travelTimeOutputs = entry.getValue();
			Collections.sort(travelTimeOutputs, new Comparator<TravelTimeOutput>() {

				@Override
				public int compare(TravelTimeOutput o1, TravelTimeOutput o2) {
					return o1.getStopSequence().compareTo(o2.getStopSequence());
				}

			});
			travelTimeView.setDirectionId(Integer.valueOf(key[1]));
			travelTimeView.setRouteName(key[0]);
			travelTimeView.setTravelTimes(entry.getValue());
			travelTimeViews.add(travelTimeView);
		}
		return travelTimeViews;
	}

}
