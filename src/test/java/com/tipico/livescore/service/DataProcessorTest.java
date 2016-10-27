package com.tipico.livescore.service;

import com.tipico.livescore.dto.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataProcessorTest {

	@Autowired
	DataProcessor dataProcessor;

	@Test
	public void testDetectChanges() {

		List<Event> events = new ArrayList(){{
			add(new Event() {{
				setId("123");
				setHomeTeamName("team1");
				setHomeTeamScore(1);
				setAwayTeamName("team2");
				setAwayTeamScore(0);
				setElapsedTimeInMinutes("8");
			}});
			add(new Event() {{
				setId("234");
				setHomeTeamName("team3");
				setHomeTeamScore(1);
				setAwayTeamName("team4");
				setAwayTeamScore(1);
				setElapsedTimeInMinutes("18");
			}});
		}};

		List<Event> events2 = new ArrayList(){{
			add(new Event() {{
				setId("123");
				setHomeTeamName("team1");
				setHomeTeamScore(1);
				setAwayTeamName("team2");
				setAwayTeamScore(1);
				setElapsedTimeInMinutes("18");
			}});
			add(new Event() {{
				setId("234");
				setHomeTeamName("team3");
				setHomeTeamScore(2);
				setAwayTeamName("team4");
				setAwayTeamScore(1);
				setElapsedTimeInMinutes("28");
			}});
		}};

		assertTrue(dataProcessor.changesDetected(events, events2));
	}


	@Test
	public void testDetectChanges_NoChanges() {

		List<Event> events = new ArrayList(){{
			add(new Event() {{
					setId("123");
					setHomeTeamName("team1");
					setHomeTeamScore(1);
					setAwayTeamName("team2");
					setAwayTeamScore(0);
					setElapsedTimeInMinutes("8");
				}});
			add(new Event() {{
					setId("234");
					setHomeTeamName("team3");
					setHomeTeamScore(1);
					setAwayTeamName("team4");
					setAwayTeamScore(1);
					setElapsedTimeInMinutes("18");
				}});
		}};

		assertFalse(dataProcessor.changesDetected(events, events));
	}


	@Test
	public void testDetectChanges_NullSafe() {

		List<Event> events = new ArrayList(){{
			add(new Event() {{
					setId("123");
					setHomeTeamName("team1");
					setHomeTeamScore(1);
					setAwayTeamName("team2");
					setAwayTeamScore(0);
					setElapsedTimeInMinutes("8");
				}});
			add(new Event() {{
					setId("234");
					setHomeTeamName("team3");
					setHomeTeamScore(1);
					setAwayTeamName("team4");
					setAwayTeamScore(1);
					setElapsedTimeInMinutes("18");
				}});
		}};

		assertTrue(dataProcessor.changesDetected(null, events));
	}



}