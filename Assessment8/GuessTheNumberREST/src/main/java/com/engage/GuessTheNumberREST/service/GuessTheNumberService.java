package com.engage.GuessTheNumberREST.service;

import java.util.List;
import java.util.Map;

import com.engage.GuessTheNumberREST.models.Game;
import com.engage.GuessTheNumberREST.models.Guess;

public interface GuessTheNumberService {
	
	public String generateAnswer();
	
	public Game createGame(String answer);

	public Guess createGuess(Guess guess);
	
	public Map<String, Integer> calculateResult(String guess, String answer);
	
	public List<Game> getAllGames();
	
	public Game getGameById(int gameId);
	
	public List<Guess> getGuessesByGameId(int gameId);
	
	public void updateGame(Map<String, Integer> result, int gameId);
	
	public String parseGuess(int guess);
	
	public String findAnswer(int gameId);

	boolean isFinished(int gameId);
	
}
