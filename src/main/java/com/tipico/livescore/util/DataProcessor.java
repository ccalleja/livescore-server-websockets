package com.tipico.livescore.util;

import com.tipico.livescore.dto.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

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
		List<Event> parsedEvents = new ArrayList<>();
		log.debug("Parsing group categories");
		for (LinkedHashMap groupCategory : groupCategories) {
			String groupCatType = (String) groupCategory.get("image");
			String groupCatName = (String) groupCategory.get("displayName");
			if (groupCatType.equalsIgnoreCase("soccer")) {
				log.debug(
					String.format("Processing group category %s", groupCatName));
				ArrayList<LinkedHashMap> matches = (ArrayList) groupCategory.get("matches");
				for (LinkedHashMap match : matches) {
					Event event = Event.buildFromMapData(match);
					if (event != null) {
						parsedEvents.add(event);
					}
				}

			} else {
				log.debug(String.format("Skipping group category %s. " +
					"Only catering for SOCCER", groupCatName));
			}

		}

		return parsedEvents;
	}
}
