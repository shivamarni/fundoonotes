package com.bridgelabz.fundoonotes.exception;

import java.sql.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bridgelabz.fundoonotes.response.ExceptionResponse;
@Controller
public class ResponseEntityException extends ResponseEntityExceptionHandler{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    @ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<ExceptionResponse> handlerUserNotFoundException(UserNotFoundException ex,WebRequest request)
	{
		ExceptionResponse exceptionResponse=new ExceptionResponse(new Date(0),ex.getMessage(),request.getDescription(false));
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}
	{
		
	}

}
