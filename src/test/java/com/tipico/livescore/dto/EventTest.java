package com.tipico.livescore.dto;

import com.tipico.livescore.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
public class EventTest {

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
	}

	@Test
	public void testEventToString() {
		Event event = Event.buildFromMapData(testEventData.get("event1"));

		String toString1 = event.toString();
		assertNotNull(toString1);
		assertEquals("123,8,team1,1,team2,0," + Application.SERVER_BASE_URL +
			"/json/services/sports/event/123",
			toString1);

		event = Event.buildFromMapData(testEventData.get("event1"));
		assertEquals(toString1, event.toString());
	}

	@Test
	public void testEventHash() {
		Event event = Event.buildFromMapData(testEventData.get("event1"));

		int hashCode = event.getHash();
		assertEquals(-52531761, hashCode);

		event = Event.buildFromMapData(testEventData.get("event1"));

		assertEquals(hashCode, event.getHash());
	}

}