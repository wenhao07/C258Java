package com.engage.GuessTheNumberREST.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.engage.GuessTheNumberREST.data.GameDao;
import com.engage.GuessTheNumberREST.data.GuessDao;
import com.engage.GuessTheNumberREST.models.Game;
import com.engage.GuessTheNumberREST.models.Guess;

@Service
public class GuessTheNumberServiceImpl implements GuessTheNumberService{
	private final GameDao gameDao;
	private final GuessDao guessDao;
	
	public GuessTheNumberServiceImpl(GameDao gameDao, GuessDao guessDao) {
		this.gameDao = gameDao;
		this.guessDao = guessDao;
	}
	
	@Override
	public String generateAnswer() {
		String answer = "";
		List<String> digits = Arrays.asList("0","1","2","3","4","5","6","7","8","9");
		Collections.shuffle(digits);
		
		for (int i=0 ; i < 4 ; i++) {
			answer += digits.get(i);
		}
		return answer;
	}
	
	@Override
	public Game createGame(String answer) {
		Game newGame = new Game();
		newGame.setAnswer(answer);
		return gameDao.add(newGame);
	}
	
	@Override
	public Guess createGuess(Guess guess) {
		return guessDao.add(guess);
	}

	@Override
	public Map<String, Integer> calculateResult(String guess, String answer) {
		Map<String, Integer> result = new HashMap<>();
		int exact = 0;
		int partial = 0;
		
		for (int i=0 ; i < 4; i++) {
			if (guess.charAt(i) == answer.charAt(i)){
				exact ++;
			}else if ( (guess.charAt(i) != answer.charAt(i))
					&& (answer.indexOf(guess.charAt(i))>0) ){
				partial ++;
			}
		}
		result.put("exact", exact);
		result.put("partial", partial);
		return result;
	}

	@Override
	public List<Game> getAllGames() {
		List<Game> gameList = gameDao.getAll();
		
		for(Game g: gameList) {
			if(!g.isFinished()) {
				g.setAnswer("****");
			}
		}
		return gameList;
	}

	@Override
	public Game getGameById(int gameId) {
		Game g = gameDao.findById(gameId);
		if(!g.isFinished()) {
			g.setAnswer("****");
		}
		return g;
	}

	@Override
	public List<Guess> getGuessesByGameId(int gameId) {
		return guessDao.findByGameId(gameId);
	}

	@Override
	public void updateGame(Map<String, Integer> result, int gameId) {
		if(result.get("exact") == 4) {
			Game updatedGame = gameDao.findById(gameId);
			updatedGame.setFinished(true);
			gameDao.update(updatedGame);
		}
	}
	
	@Override
	public String parseGuess(int guess) {
		return String.valueOf(guess);
	}
	
	@Override
	public String findAnswer(int gameId) {
		return gameDao.findById(gameId).getAnswer();
	}
	
	@Override
	public boolean isFinished(int gameId) {
		return gameDao.isFinished(gameId);
	}
}

