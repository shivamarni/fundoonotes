package com.bridgelabz.fundoonotes.response;

public class NoteResponse {
	private String message;
	private int statusCode;
	private Object notes;
	public NoteResponse(String message, int statusCode, Object notes) {
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.notes = notes;
	}
	public NoteResponse(String message, Object notes) {
		super();
		this.message = message;
		this.notes = notes;
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
	public Object getNotes() {
		return notes;
	}
	public void setNotes(Object notes) {
		this.notes = notes;
	}
	
	

}
