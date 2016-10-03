package com.tipico.livescore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by clint on 03/10/16.
 */
@Service
public class CachedDataService {

	private static final Logger log = LoggerFactory.getLogger(FeedPollingService.class);

	private Object liveScoreCachedData;

	@Cacheable("liveScores")
	public Object getLiveGamesData(){
		log.debug("Get live games data called");
		return liveScoreCachedData;
	}

	public void updateLiveScoreData(Object liveData){
		log.debug("Live games data updated");
		this.liveScoreCachedData = liveData;
	}

}
