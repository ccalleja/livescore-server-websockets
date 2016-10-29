package com.tipico.livescore.service.impl;

import com.tipico.livescore.dto.Event;
import com.tipico.livescore.service.CachedDataService;
import com.tipico.livescore.service.DataProcessor;
import com.tipico.livescore.service.WebSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataProcessorImpl implements DataProcessor {

	private static final Logger log = LoggerFactory
		.getLogger(DataProcessorImpl.class);

	@Autowired
	private CachedDataService cachedDataService;

	@Autowired
	private WebSocketService webSocketService;

	@Override
	public void process(LinkedHashMap unprocessedResponse) {
		log.debug(unprocessedResponse.toString());

		Integer totelEvents = (Integer) unprocessedResponse.get("totalEventsCount");
		if (totelEvents != null && totelEvents > 0) {
			List<Event> events = parseAndMapData(((ArrayList) unprocessedResponse.get("groupCategories")));
			if (changesDetected(cachedDataService.getLiveGamesData(), events)) {
				cachedDataService.updateLiveScoreData(events);
				webSocketService.publishUpdates();
			}
		}
	}

	protected List<Event> parseAndMapData(ArrayList<LinkedHashMap> groupCategories) {

		log.info("Starting to parse matches");
		
		List<Event> parsedEvents = groupCategories.stream()
			.filter(groupCat -> groupCat.get("image").equals("soccer"))
			.map(gcMatches -> ((ArrayList<LinkedHashMap>) gcMatches.get("matches")))
			.flatMap(matches -> matches.stream())
			.map(event -> Event.buildFromMapData(event))
			.collect(Collectors.toList());

		return parsedEvents;
	}

	@Override
	public boolean changesDetected(List<Event> currentEvents, List<Event> fetchedEvents){
		if(currentEvents == null && fetchedEvents != null){
			return true;
		} else if(fetchedEvents == null && currentEvents != null){
			return false;
		} else {

			String current = currentEvents.stream().map(Object::toString)
				.collect(Collectors.joining(","));

			String fetched = fetchedEvents.stream().map(Object::toString)
				.collect(Collectors.joining(","));

			return !current.equals(fetched);
		}
	}
}
