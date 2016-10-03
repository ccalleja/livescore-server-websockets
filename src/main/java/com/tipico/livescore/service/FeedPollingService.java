package com.tipico.livescore.service;


import com.tipico.livescore.Application;
import com.tipico.livescore.dto.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class FeedPollingService {

	// "/json/services/sports/live" 		[all live];
	// "/json/services/sports/live/1101" 	[all football];
	// "/json/services/sports/live/392201" 	[football norway];

	private final String LIVE_FEED_URL = Application.SERVER_BASE_URL +
		"/json/services/sports/live/1101"; //[football norway]

	private static final Logger log = LoggerFactory.getLogger(FeedPollingService.class);

	@Autowired
	CachedDataService cachedDataService;

	@Autowired
	WebSocketService webSocketService;

	@Scheduled(fixedDelay=5000)
	public void fetchLiveScoreData() {
		RestTemplate restTemplate = new RestTemplate();
		LinkedHashMap response = restTemplate
			.getForObject(LIVE_FEED_URL, LinkedHashMap.class);
		List<Event> parsedResponse = processRequiredData(response);
		if (parsedResponse != null &&
			//todo - use a better comparison to avoid sending data
			!parsedResponse.equals(cachedDataService.getLiveGamesData())) {
			cachedDataService.updateLiveScoreData(parsedResponse);
			webSocketService.publishUpdates();
		}
	}

	private List<Event> processRequiredData(LinkedHashMap unprocessedResponse) {
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
