package com.bridgelabz.fundoonotes.response;

public class ResponseStatus {
	private String message;
	private int statusCode;
	private Object object;

	public ResponseStatus(String message, int statusCode, Object object) {
		this.message = message;
		this.statusCode = statusCode;
		this.object = object;
	}

	public ResponseStatus(String message, int statusCode) {
		this.message = message;
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}




}
