package com.tipico.livescore.processor;

import com.tipico.livescore.dto.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataProcessor {

	private static final Logger log = LoggerFactory
		.getLogger(DataProcessor.class);

	public List<Event> process(LinkedHashMap unprocessedResponse) {
		log.debug(unprocessedResponse.toString());

		Integer totelEvents = (Integer) unprocessedResponse.get("totalEventsCount");
		if (totelEvents == null || totelEvents == 0) {
			log.debug("No events returned from source, skipping execution");
			return null;
		}

		return parseAndMapData(((ArrayList) unprocessedResponse.get("groupCategories")));

	}

	private List<Event> parseAndMapData(ArrayList<LinkedHashMap> groupCategories) {

		log.info("Starting to parse matches");
		
		List<Event> parsedEvents = groupCategories.stream()
			.filter(groupCat -> groupCat.get("image").equals("soccer"))
			.map(gcMatches -> ((ArrayList<LinkedHashMap>) gcMatches.get("matches")))
			.flatMap(matches -> matches.stream())
			.map(event -> Event.buildFromMapData(event))
			.collect(Collectors.toList());

		return parsedEvents;
	}
}
