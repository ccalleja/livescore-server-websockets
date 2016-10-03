package com.tipico.livescore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class FeedPollingService {

	private final String LIVE_FEED_URL = "https://m.tipico.com/json/services/sports/live";

	private static final Logger log = LoggerFactory.getLogger(FeedPollingService.class);

	@Autowired
	CachedDataService cachedDataService;

	@Scheduled(fixedDelay=15000)
	public void fetchLiveScoreData(){
		RestTemplate restTemplate = new RestTemplate();
		Object response = restTemplate.getForObject(LIVE_FEED_URL, Object.class);
		log.debug(response.toString());
		cachedDataService.setLiveScoreData(response);
	}
}
