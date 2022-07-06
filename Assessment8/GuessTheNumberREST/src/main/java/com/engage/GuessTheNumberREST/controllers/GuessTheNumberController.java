package com.engage.GuessTheNumberREST.controllers;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.engage.GuessTheNumberREST.models.Game;
import com.engage.GuessTheNumberREST.models.Guess;
import com.engage.GuessTheNumberREST.service.GuessTheNumberService;

@RestController
@RequestMapping("/")
public class GuessTheNumberController {
	
	private final GuessTheNumberService service;
	
	public GuessTheNumberController(GuessTheNumberService service) {
		this.service = service;
	}
	
	@GetMapping()
	public String welcome() {
		return "\"begin\" - POST to start a new game \n"
				+ "\"guess\" - POST to make a guess by passing \"gameId\" and \"guess\" as JSON \n"
				+ "\"game\" - GET to return a list of games \n"
				+ "\"game/{gameId}\" - GET to return a specific game \n"
				+ "\"rounds/{gameId}\" - GET to return a list of rounds for a specific game";
	}
	
	@PostMapping("begin")
	@ResponseStatus(HttpStatus.CREATED)
	public String createGame() {
		String answer = service.generateAnswer();
		Game game = service.createGame(answer);
		return "New game started, game id: " + game.getGameId();
	}
	
	@PostMapping("guess")
	public String makeAGuess(@RequestBody Guess guess) {
		boolean isFinished = service.isFinished(guess.getGameId());
		if(isFinished) {
			return "Game has been completed, please start a new game make a guess for another one!";
		}
		String answer = service.findAnswer(guess.getGameId());
		String guessStr = service.parseGuess(guess.getGuess());
		if (guessStr.length() > 4) {
			throw new StringIndexOutOfBoundsException();
		}
		Map<String, Integer> result = service.calculateResult(guessStr, answer);
		
		Guess newGuess = createNewGuess(guess.getGuess(), guess.getGameId(), result);
		service.createGuess(newGuess);
		service.updateGame(result, newGuess.getGameId());		
		return "e:" + result.get("exact") + ":p:" + result.get("partial");
	}
	
	@GetMapping("game")
	public List<Game> getAllGames(){
		return service.getAllGames();
	}
	
	@GetMapping("game/{id}")
	public ResponseEntity<Game> findGameById(@PathVariable int id){
		Game result = service.getGameById(id);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("rounds/{id}")
	public ResponseEntity<List<Guess>> findGuessByGameId(@PathVariable int id){
		Game res = service.getGameById(id);
		List<Guess> result = service.getGuessesByGameId(id);
		return ResponseEntity.ok(result);
	}
	
	private Guess createNewGuess(int guess, int gameId, Map<String, Integer> result) {
		Guess newGuess = new Guess();
		newGuess.setGuess(guess);
		newGuess.setExact(result.get("exact"));
		newGuess.setPartial(result.get("partial"));
		newGuess.setTimestamp(new Timestamp(System.currentTimeMillis()));
		newGuess.setGameId(gameId);
		return newGuess;
	}
}
