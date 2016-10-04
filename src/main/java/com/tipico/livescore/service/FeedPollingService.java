package com.tipico.livescore.service;


import com.tipico.livescore.Application;
import com.tipico.livescore.dto.Event;
import com.tipico.livescore.processor.DataProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class FeedPollingService {

	@Value( "${live.sport.id}" )
	private String liveSportId;

	private static final String LIVE_FEED_URL = Application.SERVER_BASE_URL +
		"/json/services/sports/live/";

	@Autowired
	private CachedDataService cachedDataService;

	@Autowired
	private WebSocketService webSocketService;

	@Autowired
	private DataProcessor dataProcessor;

	@Autowired
	private RestOperations restTemplate;

	@Scheduled(fixedDelay=5000)
	public void fetchLiveScoreData() {
		LinkedHashMap response = restTemplate
			.getForObject(LIVE_FEED_URL+liveSportId, LinkedHashMap.class);
		//todo - make this part more intelligent
		//the above can return games, or groups
		List<Event> parsedResponse = dataProcessor.process(response);
		if (parsedResponse != null &&
			//todo - compare better
			// all game details toString concatenated
			!parsedResponse.equals(cachedDataService.getLiveGamesData())) {
			cachedDataService.updateLiveScoreData(parsedResponse);
			webSocketService.publishUpdates();
		}
	}
}
