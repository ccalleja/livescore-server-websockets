package com.tipico.livescore.dto;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
public class EventTest {

	@Test
	public void testEventToString() {
		Event event = new Event() {{
			setId("123");
			setHomeTeamName("team1");
			setHomeTeamScore(1);
			setAwayTeamName("team2");
			setAwayTeamScore(0);
			setElapsedTimeInMinutes("8");
			setTipicoEventURL("http://www.site.com/live/123");
		}};

		String toString1 = event.toString();
		assertNotNull(toString1);
		assertEquals("0,123,8,team1,1,team2,0,http://www.site.com/live/123",
			toString1);

		event = new Event() {{
			setId("123");
			setHomeTeamName("team1");
			setHomeTeamScore(1);
			setAwayTeamName("team2");
			setAwayTeamScore(0);
			setElapsedTimeInMinutes("8");
			setTipicoEventURL("http://www.site.com/live/123");
		}};

		assertEquals(toString1, event.toString());
	}

	@Test
	public void testEventHash() {
		Event event = new Event() {{
			setId("123");
			setHomeTeamName("team1");
			setHomeTeamScore(1);
			setAwayTeamName("team2");
			setAwayTeamScore(0);
			setElapsedTimeInMinutes("8");
			setTipicoEventURL("http://www.site.com/live/123");
		}};

		int hashCode = event.getHash();
		assertEquals(-52531761, hashCode);

		event = new Event() {{
			setId("123");
			setHomeTeamName("team1");
			setHomeTeamScore(1);
			setAwayTeamName("team2");
			setAwayTeamScore(0);
			setElapsedTimeInMinutes("8");
			setTipicoEventURL("http://www.site.com/live/123");
		}};

		assertEquals(hashCode, event.getHash());
	}

}