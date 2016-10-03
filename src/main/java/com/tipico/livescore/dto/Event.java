package com.tipico.livescore.dto;

import com.tipico.livescore.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by eman on 25/09/16.
 */
public class Event {

	private static final Logger log = LoggerFactory.getLogger(Event.class);
	private static final String EVENT_BASE_URL = Application.SERVER_BASE_URL +
		"/json/services/sports/event/";


	private String id;
	private String elapsedTimeInMinutes;

	private String homeTeamName;
	private int homeTeamScore;

	private String awayTeamName;
	private int awayTeamScore;

	private String tipicoEventURL;

	public static Event buildFromMapData(LinkedHashMap eventData){
		if(eventData == null){
			log.debug("Event data is null, skipping event mapping");
			return null;
		}

		Event event = new Event();
		event.setId(eventData.get("id") == null ? null : String.valueOf(eventData.get("id")));
		event.setHomeTeamName((String) eventData.get("team1"));
		event.setAwayTeamName((String) eventData.get("team2"));
		event.setTipicoEventURL(EVENT_BASE_URL + event.getId());
		event.setElapsedTimeInMinutes((String) eventData.get("dateAndTime"));
		ArrayList scoreList = eventData.get("scores") == null ? null :
			((ArrayList)eventData.get("scores"));
		//get 0 or 1 depending on the latest
		if(scoreList != null){
			LinkedHashMap latestScore = (LinkedHashMap) scoreList.get(scoreList.size()-1);
			event.setHomeTeamScore(latestScore.get("count1") == null ? 0 :
				Integer.parseInt((String) latestScore.get("count1")));
			event.setAwayTeamScore(latestScore.get("count2") == null ? 0 :
				Integer.parseInt((String) latestScore.get("count2")));
		}

		return event;
	}


	public String getElapsedTimeInMinutes() {
		return elapsedTimeInMinutes;
	}

	public void setElapsedTimeInMinutes(String elapsedTimeInMinutes) {
		this.elapsedTimeInMinutes = elapsedTimeInMinutes;
	}

	public String getHomeTeamName() {
		return homeTeamName;
	}

	public void setHomeTeamName(String homeTeamName) {
		this.homeTeamName = homeTeamName;
	}

	public int getHomeTeamScore() {
		return homeTeamScore;
	}

	public void setHomeTeamScore(int homeTeamScore) {
		this.homeTeamScore = homeTeamScore;
	}

	public String getAwayTeamName() {
		return awayTeamName;
	}

	public void setAwayTeamName(String awayTeamName) {
		this.awayTeamName = awayTeamName;
	}

	public int getAwayTeamScore() {
		return awayTeamScore;
	}

	public void setAwayTeamScore(int awayTeamScore) {
		this.awayTeamScore = awayTeamScore;
	}

	public String getTipicoEventURL() {
		return tipicoEventURL;
	}

	public void setTipicoEventURL(String tipicoEventURL) {
		this.tipicoEventURL = tipicoEventURL;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
