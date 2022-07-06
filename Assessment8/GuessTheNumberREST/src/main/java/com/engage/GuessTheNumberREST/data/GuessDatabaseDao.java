package com.engage.GuessTheNumberREST.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import com.engage.GuessTheNumberREST.models.Guess;

@Repository
@Profile("database")
public class GuessDatabaseDao implements GuessDao {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public GuessDatabaseDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Guess add(Guess guess) {
		final String sql = "INSERT INTO guess(guess, exact, partial, timestamp, gameId)"
				+ "VALUES(?,?,?,?,?);";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update((Connection conn) ->{
			PreparedStatement statement = conn.prepareStatement(
					sql,
					Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, guess.getGuess());
			statement.setInt(2, guess.getExact());
			statement.setInt(3, guess.getPartial());
			statement.setTimestamp(4, guess.getTimestamp());
			statement.setInt(5, guess.getGameId());
			return statement;
		}, keyHolder);
		
		guess.setGuessId(keyHolder.getKey().intValue());
		return guess;
	}

	@Override
	public List<Guess> getAll() {
		final String sql = "SELECT guessId, guess, exact, partial, timestamp, gameId FROM guess;";
		return jdbcTemplate.query(sql, new GuessMapper());
	}

	@Override
	public Guess findById(int id) {
		final String sql = "SELECT guessId, guess, exact, partial, timestamp, gameId  WHERE guessId = ?;";
		return jdbcTemplate.queryForObject(sql, new GuessMapper(), id);
	}

	@Override
	public boolean update(Guess guess) {
		final String sql = "UPDATE game SET "
				+ "guess = ?, "
				+ "exact = ?, "
				+ "partial = ?, "
				+ "timestamp = ? "
				+ "gameId = ?, "
				+ "WHERE guessId = ?"
				+ "ORDER BY timestamp DESC;";
		return jdbcTemplate.update(sql, 
				guess.getGuess(), 
				guess.getExact(), 
				guess.getPartial(), 
				guess.getTimestamp(),
				guess.getGameId(),  
				guess.getGuessId()) > 0;
	}

	@Override
	public boolean deleteById(int id) {
		final String sql = "DELETE FROM guess WHERE guessId = ?;";
		return jdbcTemplate.update(sql, id) > 0;
	}
	

	@Override
	public List<Guess> findByGameId(int id) {
		final String sql = "SELECT guessId, guess, exact, partial, timestamp, gameId "
				+ "FROM guess WHERE gameId = ? ORDER BY timestamp DESC;";
		return jdbcTemplate.query(sql, new GuessMapper(), id);
	}
	
	private static final class GuessMapper implements RowMapper<Guess>{
		
		@Override
		public Guess mapRow(ResultSet rs, int index) throws SQLException{
			Guess g = new Guess();
			g.setGuessId(rs.getInt("guessId"));
			g.setGuess(rs.getInt("guess"));
			g.setExact(rs.getInt("exact"));
			g.setPartial(rs.getInt("partial"));
			g.setTimestamp(rs.getTimestamp("timestamp"));
			g.setGameId(rs.getInt("gameId"));
			return g;
		}
	}
}
