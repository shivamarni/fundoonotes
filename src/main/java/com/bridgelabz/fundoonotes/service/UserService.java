package com.bridgelabz.fundoonotes.service;
import java.util.List;

import org.hibernate.sql.Update;

import com.bridgelabz.fundoonotes.dto.UserLogin;
import com.bridgelabz.fundoonotes.dto.UserRegister;
import com.bridgelabz.fundoonotes.dto.UserUpdate;
import com.bridgelabz.fundoonotes.model.UserDemo;

public interface UserService {

	public String login(UserLogin userDto);
	public UserDemo register(UserRegister userRecord);
	public UserDemo forgotPassword(String newpassword,String token);
	public List<UserDemo> getAllUsers();
	
	Boolean verifyToken(String token);

	String emailVerify(String email);

	UserDemo getUser(String token);	

}
