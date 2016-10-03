package com.tipico.livescore.controller;

import com.tipico.livescore.service.CachedDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LiveScoreController {

    @Autowired
    private CachedDataService cachedDataService;

    @RequestMapping("/livescore")
    public Object greeting() {
        return cachedDataService.getLiveGamesData();
    }
}
