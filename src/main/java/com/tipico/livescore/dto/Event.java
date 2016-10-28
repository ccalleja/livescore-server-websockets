package com.tipico.livescore.dto;

import com.tipico.livescore.Application;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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

	private Event(LinkedHashMap eventData) {
		ReflectionToStringBuilder.
			setDefaultStyle(ToStringStyle.SIMPLE_STYLE);
		this.id =
			eventData.get("id") == null ? null : String.valueOf(eventData.get("id"));
		this.homeTeamName = ((String) eventData.get("team1"));
		this.awayTeamName = ((String) eventData.get("team2"));
		this.tipicoEventURL = EVENT_BASE_URL + this.id;
		this.elapsedTimeInMinutes = (String) eventData.get("dateAndTime");
		ArrayList scoreList = eventData.get("scores") == null ? null :
			((ArrayList)eventData.get("scores"));
		//get 0 or 1 depending on the latest
		if(scoreList != null){
			LinkedHashMap latestScore = (LinkedHashMap) scoreList.get(scoreList.size()-1);
			this.homeTeamScore = (latestScore.get("count1") == null ? 0 :
				Integer.parseInt((String) latestScore.get("count1")));
			this.awayTeamScore = (latestScore.get("count2") == null ? 0 :
				Integer.parseInt((String) latestScore.get("count2")));
		}
	}

	public static Event buildFromMapData(LinkedHashMap eventData){
		if(eventData == null){
			log.debug("Event data is null, skipping event mapping");
			return null;
		}

		return new Event(eventData);
	}


	public String getElapsedTimeInMinutes() {
		return elapsedTimeInMinutes;
	}

	public String getHomeTeamName() {
		return homeTeamName;
	}

	public int getHomeTeamScore() {
		return homeTeamScore;
	}

	public String getAwayTeamName() {
		return awayTeamName;
	}

	public int getAwayTeamScore() {
		return awayTeamScore;
	}

	public String getTipicoEventURL() {
		return tipicoEventURL;
	}

	public String getId() {
		return id;
	}

	public int getHash() {
		return this.toString().hashCode();
	}

	@Override
	public String toString(){
		return ReflectionToStringBuilder.toString(this);
	}
}
