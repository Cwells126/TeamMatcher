package com.connectcard.domain;

import java.util.ArrayList;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MATCHUP")
public class Matchup {

	private Integer gameId;

	private String homeTeam;
	

	private String awayTeam;
	
	private Float line;
	
	
	private Calendar gameDate;
	
	private Float homeScore;
	
	private Float awayScore;
	

	
	
	
	

	public Matchup(Integer gameId, String homeTeam,String awayTeam, Float line, Calendar gameDate ) {
		super();
		this.gameId = gameId;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.line = line;
		this.gameDate = gameDate;
		
	}

	public Matchup() {}

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="GAME_ID")
	public Integer getGameId() {
		return gameId;
	}

	public void setGameId(Integer gameId) {
		this.gameId = gameId;
	}

	@Column(name="HOME_TEAM", nullable=false)
	public String getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	@Column(name="AWAY_TEAM", nullable=false)
	public String getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}

	@Column(name="LINE", nullable=false)
	public Float getLine() {
		return line;
	}

	public void setLine(Float line) {
		this.line = line;
	}

	@Column(name="GAME_DATE", nullable=true)
	public Calendar getGameDate() {
		return gameDate;
	}

	public void setGameDate(Calendar gameDate) {
		this.gameDate = gameDate;
	}
	
		public void setHomeScore(Float homeScore) {
		this.homeScore = homeScore;
	}
	
	@Column(name="HOME_SCORE", nullable=true)
	public Float getHomeScore() {
		return homeScore;
	}
	
	public void setAwayScore(Float awayScore) {
		this.awayScore = awayScore;
	}
	
	@Column(name="AWAY_SCORE", nullable=true)
	public Float getAwayScore() {
		return awayScore;
	}

	
	
}
