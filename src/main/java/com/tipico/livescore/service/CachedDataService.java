package com.tipico.livescore.service;

import com.tipico.livescore.dto.Event;

import java.util.List;

/**
 * Created by clint on 27/10/2016.
 */
public interface CachedDataService {

	List<Event> getLiveGamesData();

	void updateLiveScoreData(List<Event> liveData);

}
