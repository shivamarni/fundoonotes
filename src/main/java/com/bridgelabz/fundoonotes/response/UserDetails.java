package com.bridgelabz.fundoonotes.response;

public class UserDetails {
	private String token;
	private String message;
	private Object object;


	public UserDetails(String token, String i, Object object) {
		this.token = token;
		this.message = i;
		this.object = object;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}


}
