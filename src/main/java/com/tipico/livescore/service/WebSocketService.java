package com.tipico.livescore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

	@Autowired
	private CachedDataService cachedDataService;

	@Autowired
	private SimpMessagingTemplate template;

	public void publishUpdates(){
		template.convertAndSend("/topic/live-score",
			cachedDataService.getLiveGamesData());
	}
}
