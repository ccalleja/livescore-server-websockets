package com.tipico.livescore.service.impl;

import com.tipico.livescore.service.CachedDataService;
import com.tipico.livescore.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketServiceImpl implements WebSocketService {

	@Autowired
	private CachedDataService cachedDataService;

	@Autowired
	private SimpMessagingTemplate template;

	@Override
	public void publishUpdates(){
		template.convertAndSend("/topic/live-score",
			cachedDataService.getLiveGamesData());
	}
}
