package com.bridgelabz.fundoonotes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.dto.UserLogin;
import com.bridgelabz.fundoonotes.dto.UserRegister;
import com.bridgelabz.fundoonotes.model.UserDemo;
import com.bridgelabz.fundoonotes.response.ResponseMessageStatus;
import com.bridgelabz.fundoonotes.response.UserInfo;
import com.bridgelabz.fundoonotes.service.AmazonS3ClientService;
import com.bridgelabz.fundoonotes.service.UserService;

@RestController
@RequestMapping("/users")
@PropertySource("classpath:message.properties")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private Environment env;

	@Autowired
    private AmazonS3ClientService amazonS3ClientService;

	@PostMapping(value = "/login")
	public ResponseEntity<UserInfo> loginUser(@Valid @RequestBody UserLogin user, BindingResult result) {
		if (result.hasErrors())
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(new UserInfo());
		String token = userService.login(user);
		if (token != null) {

			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new UserInfo(token, env.getProperty("100"), user));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserInfo(env.getProperty("106"), "", user));

	}

	@PostMapping(value = "/add-user")
	public ResponseEntity<UserInfo> register(@Valid @RequestBody UserRegister userRecord, BindingResult result) {
		
		UserDemo user = userService.register(userRecord);
		if (user != null) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new UserInfo(env.getProperty("101"), "200-ok", userRecord));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new UserInfo(env.getProperty("106"), "", userRecord));
	}

	
	@PutMapping(value = "/{newPassword}/{token}")
	public ResponseEntity<UserInfo> forgetPassword(@PathVariable String newPassword, @PathVariable String token) {

		UserDemo result = userService.forgotPassword(newPassword, token);
		if (result != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new UserInfo(env.getProperty("108"), "200-ok", result));
		}
		return null;
	}

	@GetMapping(value="users/verify/{token}")
	public ResponseEntity<ResponseMessageStatus> verify(@PathVariable("token") String token) throws Exception {
		boolean verification = userService.verifyToken(token);
		if (verification) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseMessageStatus("verified", 200, token));
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new ResponseMessageStatus("not verified", 400, token));
	}
	
	@PostMapping(value = "/{emailId}")
	public ResponseEntity<UserInfo> emailVerify(@PathVariable String emailId) {

		String result = userService.emailVerify(emailId);
		if (result != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new UserInfo(result,env.getProperty("107"),200));
		}
		return null;
	}
	
	@GetMapping(value="/get-all-users")
	public List<UserDemo> getAllUsers()
	{
		return userService.getAllUsers();
	}
	@PostMapping(value="/uploadProfile")
    public Map<String, String> uploadProfile(@RequestPart(value = "file") MultipartFile file,@RequestPart("token") String token)
    {
        this.amazonS3ClientService.uploadFileToS3Bucket(file, true,token);

        Map<String, String> response = new HashMap<>();
        response.put("message", "file [" + file.getOriginalFilename() + "] uploading request submitted successfully.");

        return response;
    }

    @DeleteMapping(value="/deleteProfile")
    public Map<String, String> deleteProfile(@RequestParam("file_name") String fileName,@RequestPart("token") String token)
    {
        this.amazonS3ClientService.deleteFileFromS3Bucket(fileName,token);

        Map<String, String> response = new HashMap<>();
        response.put("message", "file [" + fileName + "] removing request submitted successfully.");

        return response;
    }
}