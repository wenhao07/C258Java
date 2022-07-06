package com.engage.GuessTheNumberREST.controllers;

import java.sql.SQLSyntaxErrorException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.converter.HttpMessageNotReadableException;

@ControllerAdvice
@RestController
public class GuessTheNumberExceptionHandler extends ResponseEntityExceptionHandler {

	private static final String EMPTY_DATABASE_MESSAGE = "Game id does not exists. Please try again with another id";
	private static final String HTTP_MESSAGE = "Please ensure gameId and guess are integers in your request body.";
	private static final String STRING_INDEX_MESSAGE = "Please ensure that your guess contains four digits";
	private static final String SQL_MESSAGE = "Unable to query from database";

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public final ResponseEntity<Error> handleEmptyResultDataAccess(EmptyResultDataAccessException ex, WebRequest request) {
		Error err = new Error();
		err.setMessage(EMPTY_DATABASE_MESSAGE);
		return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(StringIndexOutOfBoundsException.class)
	public final ResponseEntity<Error> handleStringIndexOutOfBounds(StringIndexOutOfBoundsException ex,
			WebRequest request) {
		Error err = new Error();
		err.setMessage(STRING_INDEX_MESSAGE);
		return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(SQLSyntaxErrorException.class)
	public final ResponseEntity<Error> handleSQLSyntaxError(SQLSyntaxErrorException ex,
			WebRequest request){
		Error err = new Error();
		err.setMessage(SQL_MESSAGE);
		return new ResponseEntity<>(err, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Override
	//@ExceptionHandler(value = HttpMessageNotReadableException.class) public final
	protected ResponseEntity<Object> handleHttpMessageNotReadable(
			HttpMessageNotReadableException ex,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request){ 
		Error err = new Error(); 
		err.setMessage(HTTP_MESSAGE); return new ResponseEntity<>(err,
						HttpStatus.BAD_REQUEST); }
		 
}
