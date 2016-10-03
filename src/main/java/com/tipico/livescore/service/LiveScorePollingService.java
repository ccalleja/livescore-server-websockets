package com.tipico.livescore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LiveScorePollingService {

	private static final Logger log = LoggerFactory.getLogger(LiveScorePollingService.class);

	@Scheduled(fixedDelay=5000)
	public void fetchLiveScoreData(){
		RestTemplate restTemplate = new RestTemplate();
		Object response = restTemplate.getForObject("https://m.tipico.com/json/services/sports/live",
			Object.class);
		log.info(response.toString());
	}
}
