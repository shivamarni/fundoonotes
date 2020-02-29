package com.bridgelabz.fundoonotes.dto;

import org.springframework.stereotype.Component;

@Component
public class UserLogin {
	private String email;
	private String password;

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "LoginDetails [email=" + email + ", password=" + password + "]";
	}


}
