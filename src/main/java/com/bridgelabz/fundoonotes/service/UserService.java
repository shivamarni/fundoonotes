package com.bridgelabz.fundoonotes.service;
import java.util.List;

import com.bridgelabz.fundoonotes.dto.UserUpdate;
import com.bridgelabz.fundoonotes.dto.UserRegister;
import com.bridgelabz.fundoonotes.model.UserDemo;

public interface UserService {

	public UserDemo login(String id);
	public UserDemo register(UserRegister userRecord);
	public UserDemo forgotPassword(UserUpdate updateDto);
	public List<UserDemo> getUsers();
	public void removeUsers(String id);
	Boolean verify(String token);
}
