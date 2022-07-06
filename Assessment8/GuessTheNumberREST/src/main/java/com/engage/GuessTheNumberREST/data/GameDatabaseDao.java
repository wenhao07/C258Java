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

import com.engage.GuessTheNumberREST.models.Game;

@Repository
@Profile("database")
public class GameDatabaseDao implements GameDao {
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public GameDatabaseDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public Game add(Game game) {
		final String sql = "INSERT INTO game(answer) VALUES(?);";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update((Connection conn) ->{
			PreparedStatement statement = conn.prepareStatement(
					sql,
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, game.getAnswer());
			return statement;
		}, keyHolder);
		
		game.setGameId(keyHolder.getKey().intValue());
		return game;
	}

	@Override
	public List<Game> getAll() {
		final String sql = "SELECT gameId, answer, finished FROM game;";
		return jdbcTemplate.query(sql, new GameMapper());
	}

	@Override
	public Game findById(int id) {
		final String sql = "SELECT gameId, answer, finished FROM game WHERE gameId = ?;";
		return jdbcTemplate.queryForObject(sql, new GameMapper(), id);
	}

	@Override
	public boolean update(Game game) {
		final String sql = "UPDATE game SET answer = ?, finished = ? WHERE gameId = ?;";
		return jdbcTemplate.update(sql, game.getAnswer(), game.isFinished(), game.getGameId()) > 0;
	}

	@Override
	public boolean deleteById(int id) {
		final String sql = "DELETE FROM game WHERE gameId = ?;";
		return jdbcTemplate.update(sql, id) > 0;
	}

	private static final class GameMapper implements RowMapper<Game>{
		
		@Override
		public Game mapRow(ResultSet rs, int index) throws SQLException{
			Game g = new Game();
			g.setGameId(rs.getInt("gameId"));
			g.setAnswer(rs.getString("answer"));
			g.setFinished(rs.getBoolean("finished"));
			return g;
		}
	}
	
	@Override
	public boolean isFinished(int id) {
		return this.findById(id).isFinished();
	}
}
