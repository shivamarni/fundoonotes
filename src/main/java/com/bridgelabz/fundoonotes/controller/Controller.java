package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.UserUpdate;
import com.bridgelabz.fundoonotes.dto.UserRegister;
import com.bridgelabz.fundoonotes.model.UserDemo;
import com.bridgelabz.fundoonotes.response.ResponseMessageStatus;
import com.bridgelabz.fundoonotes.response.UserInfo;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.utils.JWTGenerator;

@RestController

public class Controller {
	@Autowired
	private UserService userService;
	@Autowired
	private JWTGenerator jwtGenerator;

	@GetMapping(value="/login/{id}")
	public ResponseEntity<UserInfo> loginUser(@PathVariable String id)
	{
		UserDemo result=userService.login(id);
		if(result!=null)
		{
			String token=jwtGenerator.jwtToken(result.getUserId());
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("login successful",result.getName())
					.body(new UserInfo(token,"200 ok",result));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserInfo("login failed","400 NOT_FOUND",result));

	}
	@PostMapping(value="/register")
	public ResponseEntity<ResponseMessageStatus>register(@RequestBody UserRegister userRecord)
	{
		UserDemo user=userService.register(userRecord);
		if(user!=null)
		{
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ResponseMessageStatus("registration Successful",200,userRecord));
		}
		else
		{

			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
					.body(new ResponseMessageStatus("Already existing user",400,userRecord));
		}
	}
	@GetMapping(value="/verify/{token}")
	public ResponseEntity<ResponseMessageStatus> verify(@PathVariable("token")String token) throws Exception
	{
		boolean verification=userService.verifyToken(token);
		if(verification)
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseMessageStatus("verified",200,token));
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseMessageStatus("not verified",400,token));
	}
	@PutMapping(value="/forgot-password")
	public ResponseEntity<ResponseMessageStatus> forgotPassword(@RequestBody UserUpdate updateDto)
	{
		UserDemo result=userService.forgotPassword(updateDto);
		if(result!=null)
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new ResponseMessageStatus("password updated successfully",200,User.class));

		}
		else
		{
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessageStatus("email not matched",402,updateDto.getEmail()));
		}
	}
	@GetMapping("/get-all-users")
	public List<UserDemo> getAllUsers()
	{
		return userService.getAllUsers();
	}
	@DeleteMapping(value="/delete/{id}")
	public void deleteUser(@PathVariable String id)
	{
		userService.removeUsers(id);
	}

}