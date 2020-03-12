package com.bridgelabz.fundoonotes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoteException extends RuntimeException
{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HttpStatus statusCode;
	private String statusMessage;

}
