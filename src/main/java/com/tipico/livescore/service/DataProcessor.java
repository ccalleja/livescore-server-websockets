package com.tipico.livescore.service;

import com.tipico.livescore.dto.Event;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by clint on 27/10/2016.
 */
public interface DataProcessor {
	void process(LinkedHashMap unprocessedResponse);
	boolean changesDetected(List<Event> currentEvents, List<Event> fetchedEvents);
}
