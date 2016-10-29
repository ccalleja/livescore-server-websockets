package com.tipico.livescore.service.impl;

import com.tipico.livescore.dto.Event;
import com.tipico.livescore.service.CachedDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.util.CollectionUtils.*;

/**
 * Created by clint on 03/10/16.
 */
@Service
public class CachedDataServiceImpl implements CachedDataService {

	private static final Logger log = LoggerFactory.getLogger(FeedPollingServiceImpl.class);

	private List<Event> liveScoreCachedData;

	@Override
	@Cacheable("liveScoreCache")
	public List<Event> getLiveGamesData(){
		log.debug("Get live games data called");
		return liveScoreCachedData;
	}

	@Override
	@CacheEvict(value = "liveScoreCache", allEntries = true, condition = "#liveData!=null")
	public void updateLiveScoreData(List<Event> liveData){
		if(!isEmpty(liveData)) {
			log.debug("Cache updating");
			this.liveScoreCachedData = liveData;
		}

	}

}
