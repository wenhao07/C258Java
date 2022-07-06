package com.engage.GuessTheNumberREST.data;

import java.util.List;

import com.engage.GuessTheNumberREST.models.Guess;

public interface GuessDao {
	
	Guess add(Guess guess);
	
	List<Guess> getAll();
	
	Guess findById(int id);
	
	boolean update(Guess guess);
	
	boolean deleteById(int id);
	
	List<Guess> findByGameId(int id);
}
