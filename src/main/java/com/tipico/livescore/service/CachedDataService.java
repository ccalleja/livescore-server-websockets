package com.tipico.livescore.service;

import com.tipico.livescore.dto.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by clint on 03/10/16.
 */
@Service
public class CachedDataService {

	private static final Logger log = LoggerFactory.getLogger(FeedPollingService.class);

	private List<Event> liveScoreCachedData;

	@Cacheable("liveScoreCache")
	public Object getLiveGamesData(){
		log.debug("Get live games data called");
		return liveScoreCachedData;
	}

	@CacheEvict(value = "liveScoreCache", allEntries = true)
	public void updateLiveScoreData(List<Event> liveData){
		log.debug("Cache updating");
		this.liveScoreCachedData = liveData;
	}

}
