package com.tipico.livescore.controller;

import com.tipico.livescore.dto.Event;
import com.tipico.livescore.service.CachedDataService;
import com.tipico.livescore.service.WebSocketService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LiveScoreController {

    @Autowired
    private CachedDataService cachedDataService;

    @Autowired
    private WebSocketService webSocketService;

    @ApiOperation(value = "getLiveFeed", nickname = "getLiveFeed")
    @RequestMapping(value = "/livescore", method = RequestMethod.GET)
    public List<Event> getLiveFeed() {
        return cachedDataService.getLiveGamesData();
    }

    /**
     * This method allows us to register and get data immediately
     * thus not needing a seperate get. However it would be ideal to
     * use {@SimpMessagingTemplate.sendToUser} instead so do not broadcast
     * for everyone
     */
    @MessageMapping("/live-feed")
    public void subscribeToLiveFeed() {
        webSocketService.publishUpdates();
    }
}
