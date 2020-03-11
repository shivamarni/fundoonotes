package com.bridgelabz.fundoonotes.service;
import java.util.List;

import org.hibernate.sql.Update;

import com.bridgelabz.fundoonotes.dto.UserLogin;
import com.bridgelabz.fundoonotes.dto.UserRegister;
import com.bridgelabz.fundoonotes.dto.UserUpdate;
import com.bridgelabz.fundoonotes.model.UserDemo;

public interface UserService {

	public UserDemo login(UserLogin userDto);
	public UserDemo register(UserRegister userRecord);
	public UserDemo forgotPassword(UserUpdate updateDto);
	public List<UserDemo> getAllUsers();
	//public UserDemo removeUsers(String id);
	Boolean verifyToken(String token);
}
