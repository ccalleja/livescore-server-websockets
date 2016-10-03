package com.tipico.livescore.service;


import com.tipico.livescore.Application;
import com.tipico.livescore.dto.Event;
import com.tipico.livescore.util.DataProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
	private CachedDataService cachedDataService;

	@Autowired
	private WebSocketService webSocketService;

	@Autowired
	private DataProcessor dataProcessor;

	@Scheduled(fixedDelay=5000)
	public void fetchLiveScoreData() {
		RestTemplate restTemplate = new RestTemplate();
		LinkedHashMap response = restTemplate
			.getForObject(LIVE_FEED_URL, LinkedHashMap.class);
		List<Event> parsedResponse = dataProcessor.process(response);
		if (parsedResponse != null &&
			//todo - use a better comparison to avoid sending data
			!parsedResponse.equals(cachedDataService.getLiveGamesData())) {
			cachedDataService.updateLiveScoreData(parsedResponse);
			webSocketService.publishUpdates();
		}
	}
}
