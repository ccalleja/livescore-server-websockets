package com.tipico.livescore.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by clint on 03/10/16.
 */
@Service
public class CachedDataService {

	Object liveScoreData;

	@Cacheable("liveScores")
	public Object getLiveGamesData(){
		return liveScoreData;
	}

	public void setLiveScoreData(Object liveData){
		this.liveScoreData = liveData;
	}

}
