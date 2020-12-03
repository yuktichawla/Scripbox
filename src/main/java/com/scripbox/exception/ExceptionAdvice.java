package com.scripbox.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.scripbox.model.Errors;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Component
@Slf4j
public class ExceptionAdvice {
	
	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<Errors> handleApplicationException (ApplicationException ex){
		return new ResponseEntity<>(new Errors(ex.getErrorCode(),ex.getErrorMessage()),HttpStatus.valueOf(ex.getStatusCode()));
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Errors> handleException (Exception ex){
		log.error("error - {}",ex);
		return new ResponseEntity<>(new Errors("TECHNICAL_ERR","Technical error"),HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
