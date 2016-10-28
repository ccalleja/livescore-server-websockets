package com.tipico.livescore.service;

import com.tipico.livescore.dto.Event;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataProcessorTest {

	@Autowired
	DataProcessor dataProcessor;

	private HashMap<String, LinkedHashMap> testEventData;

	@Before
	public void dataSetup() {
		testEventData = new HashMap<>();
		LinkedHashMap event1 = new LinkedHashMap();
		event1.put("id", "123");
		event1.put("team1","team1");
		event1.put("team2", "team2");
		ArrayList scoreList1 = new ArrayList();
		LinkedHashMap latestScores1 = new LinkedHashMap();
		latestScores1.put("count1","1");
		latestScores1.put("count2","0");
		scoreList1.add(latestScores1);
		event1.put("scores", scoreList1);
		event1.put("dateAndTime", "8");
		testEventData.put("event1", event1);

		LinkedHashMap event2 = new LinkedHashMap();
		event2.put("id", "234");
		event2.put("team3","team3");
		event2.put("team4", "team4");
		ArrayList scoreList2 = new ArrayList();
		LinkedHashMap latestScores2 = new LinkedHashMap();
		latestScores2.put("count1","1");
		latestScores2.put("count2","1");
		scoreList2.add(latestScores2);
		event2.put("scores", scoreList2);
		event2.put("dateAndTime", "18");
		testEventData.put("event2", event2);

		LinkedHashMap event3 = new LinkedHashMap();
		event3.put("id", "123");
		event3.put("team1","team1");
		event3.put("team2", "team2");
		ArrayList scoreList3 = new ArrayList();
		LinkedHashMap latestScores3 = new LinkedHashMap();
		latestScores3.put("count1","1");
		latestScores3.put("count2","1");
		scoreList3.add(latestScores3);
		event3.put("scores", scoreList3);
		event3.put("dateAndTime", "18");
		testEventData.put("event3", event3);

		LinkedHashMap event4 = new LinkedHashMap();
		event4.put("id", "234");
		event4.put("team3","team3");
		event4.put("team4", "team4");
		ArrayList scoreList4 = new ArrayList();
		LinkedHashMap latestScores4 = new LinkedHashMap();
		latestScores4.put("count1","2");
		latestScores4.put("count2","1");
		scoreList4.add(latestScores4);
		event4.put("scores", scoreList4);
		event4.put("dateAndTime", "28");
		testEventData.put("event4", event4);
	}

	@Test
	public void testDetectChanges() {

		List<Event> events = new ArrayList(){{
			add(Event.buildFromMapData(testEventData.get("event1")));
			add(Event.buildFromMapData(testEventData.get("event2")));
		}};

		List<Event> events2 = new ArrayList(){{
			add(Event.buildFromMapData(testEventData.get("event3")));
			add(Event.buildFromMapData(testEventData.get("event3")));
		}};

		assertTrue(dataProcessor.changesDetected(events, events2));
	}


	@Test
	public void testDetectChanges_NoChanges() {

		List<Event> events = new ArrayList(){{
			add(Event.buildFromMapData(testEventData.get("event1")));
			add(Event.buildFromMapData(testEventData.get("event3")));
		}};

		assertFalse(dataProcessor.changesDetected(events, events));
	}


	@Test
	public void testDetectChanges_NullSafe() {

		List<Event> events = new ArrayList(){{
			add(Event.buildFromMapData(testEventData.get("event1")));
			add(Event.buildFromMapData(testEventData.get("event3")));
		}};

		assertTrue(dataProcessor.changesDetected(null, events));
	}



}