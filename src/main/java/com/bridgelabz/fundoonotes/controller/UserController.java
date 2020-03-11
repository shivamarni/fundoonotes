package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.UserUpdate;
import com.bridgelabz.fundoonotes.dto.UserLogin;
import com.bridgelabz.fundoonotes.dto.UserRegister;
import com.bridgelabz.fundoonotes.model.UserDemo;
import com.bridgelabz.fundoonotes.response.ResponseMessageStatus;
import com.bridgelabz.fundoonotes.response.UserInfo;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.utils.JWTGenerator;

@RestController

public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private JWTGenerator jwtGenerator;

	@PostMapping(value = "/user/login")
	public ResponseEntity<UserInfo> loginUser(@RequestBody UserLogin user) {
		UserDemo result = userService.login(user);
		if (result != null) {
			String parseToken = jwtGenerator.jwtToken(result.getUserId());
			return ResponseEntity.status(HttpStatus.ACCEPTED)
			              .body(new UserInfo(parseToken, "login successfull", result));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new UserInfo("login failed", "400_Not_Found", result));

	}
	


	@PostMapping(value = "/user/add-users")
	public ResponseEntity<ResponseMessageStatus> register(@RequestBody UserRegister userRecord) {
		UserDemo user = userService.register(userRecord);
		System.out.println("-----"+user);
		if (user != null) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ResponseMessageStatus("registration Successfull", 200, userRecord));
		} else {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
					.body(new ResponseMessageStatus("Already existing user", 208, userRecord));
		}
	}

	

	@PutMapping(value = "/user/forgot")
	public ResponseEntity<ResponseMessageStatus> forgetPassword(@RequestBody UserUpdate updateDto) {

		UserDemo result = userService.forgotPassword(updateDto);
		if (result != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new ResponseMessageStatus("password Updated Successfully", 200, User.class));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new ResponseMessageStatus("email not matched", 402, updateDto.getEmail()));
		}
	}

	

	@GetMapping(value="/verify/{token}")
	public ResponseEntity<ResponseMessageStatus> verify(@PathVariable("token") String token) throws Exception {
		boolean verification = userService.verifyToken(token);
		if (verification) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseMessageStatus("verified", 200, token));
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseMessageStatus("not verified", 400, token));
	}
	
	@GetMapping(value="/get-all-users")
	public List<UserDemo> getAllUsers()
	{
		return userService.getAllUsers();
	}
}