package com.bridgelabz.fundoonotes.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;
@Data
public class UserException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	private HttpStatus statusCode;
	private String statusMessage;
	
	public  UserException(HttpStatus statusCode, String message) {
		super(message);
		this.statusCode=statusCode;
		this.statusMessage=message;
		
	}

}
