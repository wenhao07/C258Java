package com.engage.GuessTheNumberREST.models;

import java.sql.Timestamp;

public class Guess {
	private int guessId;
	private int guess;
	private int exact;
	private int partial;
	private int gameId;
	private Timestamp timestamp;
	
	public int getGuessId() {
		return guessId;
	}
	public void setGuessId(int guessId) {
		this.guessId = guessId;
	}
	public int getGuess() {
		return guess;
	}
	public void setGuess(int guess) {
		this.guess = guess;
	}
	public int getExact() {
		return exact;
	}
	public void setExact(int exact) {
		this.exact = exact;
	}
	public int getPartial() {
		return partial;
	}
	public void setPartial(int partial) {
		this.partial = partial;
	}
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

}
