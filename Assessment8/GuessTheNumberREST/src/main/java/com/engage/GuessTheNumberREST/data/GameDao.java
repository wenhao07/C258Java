package com.engage.GuessTheNumberREST.data;

import java.util.List;

import com.engage.GuessTheNumberREST.models.Game;

public interface GameDao {
	Game add(Game game);
	
	List<Game> getAll();
	
	Game findById(int id);
	
	boolean update(Game game);
	
	boolean deleteById(int id);

	boolean isFinished(int id);
}
