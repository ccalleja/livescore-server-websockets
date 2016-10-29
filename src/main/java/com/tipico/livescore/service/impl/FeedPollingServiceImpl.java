package com.tipico.livescore.service.impl;

import com.tipico.livescore.Application;
import com.tipico.livescore.service.DataProcessor;
import com.tipico.livescore.service.FeedPollingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;

import java.util.LinkedHashMap;

@Service
public class FeedPollingServiceImpl implements FeedPollingService {

	@Value( "${live.sport.id}" )
	private String liveSportId;

	private static final String LIVE_FEED_URL = Application.SERVER_BASE_URL +
		"/json/services/sports/live/";

	@Autowired
	private DataProcessor dataProcessor;

	@Autowired
	private RestOperations restTemplate;

	@Override
	@Scheduled(fixedDelay=5000)
	public void fetchLiveScoreData() {
		LinkedHashMap response = restTemplate
			.getForObject(LIVE_FEED_URL+liveSportId, LinkedHashMap.class);
		//todo - make this part more intelligent
		//the above can return games, or groups
		dataProcessor.process(response);
	}
}
