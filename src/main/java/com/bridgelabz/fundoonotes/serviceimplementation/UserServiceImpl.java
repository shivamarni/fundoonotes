package com.bridgelabz.fundoonotes.serviceimplementation;
import java.time.LocalDateTime;
import java.util.ArrayList;
/**
 * @author:shiva
 */
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.dto.UserLogin;
import com.bridgelabz.fundoonotes.dto.UserRegister;
import com.bridgelabz.fundoonotes.model.UserDemo;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.utils.JWTGenerator;
import com.bridgelabz.fundoonotes.utils.MailMessage;
@Service
@PropertySource("classpath:message.properties")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncode;
	
	@Autowired
	private Environment env;
	@Autowired
	private JavaMailSenderImpl senderimp;
	@Autowired
	private JWTGenerator jwtGenerate;

	@Transactional
	@Override
	public String login(UserLogin userDto) {

		UserDemo userDetails = new UserDemo();


		UserDemo user = userRepository.findUserByEmail(userDto.getEmail())
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("103")));

		try {
			BeanUtils.copyProperties(userDto, userDetails);
			if ((user.isVerified() == true) && passwordEncode.matches(userDto.getPassword(), user.getPassword())) {

				String token = jwtGenerate.jwtToken(user.getUserId());
				this.mailservice();
				MailMessage.sendingMail(userDetails, senderimp, token);
				return token;
			}else {
				throw new UserException(HttpStatus.BAD_REQUEST,env.getProperty("105"));
			}

		} catch (Exception ex) {
			throw new UserException(HttpStatus.INTERNAL_SERVER_ERROR,env.getProperty("500"));
		}
	}

	@Transactional
	@Override
	public UserDemo register(UserRegister userDto) {

		UserDemo userDetails = new UserDemo();
		
			Optional<UserDemo> useremail = userRepository.findUserByEmail(userDto.getEmail());
			
			if (useremail.isPresent())
				throw new UserException(HttpStatus.ALREADY_REPORTED,env.getProperty("102"));

			try {
		BeanUtils.copyProperties(userDto, userDetails);

		
		userDetails.setPassword(passwordEncode.encode(userDto.getPassword()));
		userDetails.setDate(LocalDateTime.now());
		UserDemo result = userRepository.save(userDetails);

	

		this.mailservice();
		MailMessage.sendingMail(userDetails, senderimp, jwtGenerate.jwtToken(userDetails.getUserId()));

		return result;

		} catch (Exception ae) {
			ae.printStackTrace();
			throw new UserException(HttpStatus.INTERNAL_SERVER_ERROR, env.getProperty("500"));
		}
	}

	@Transactional
	@Override
	public UserDemo forgotPassword(String newpassword,String token) {
		long id = (Long) jwtGenerate.parseJWT(token);
		UserDemo user = userRepository.getUserById(id)
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("104")));
		user.setPassword( passwordEncode.encode(newpassword));
		return userRepository.save(user);
	}




	@Transactional
	@Override
	public List<UserDemo> getAllUsers() {

		List<UserDemo> ls = new ArrayList<>();
		userRepository.findAll().forEach(ls::add);
		return ls;
	}

	@Transactional
	@Override
	public Boolean verifyToken(String token) {

		long id = (Long)jwtGenerate.parseJWT(token);
		UserDemo user = userRepository.getUserById(id)
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY,env.getProperty("104")));

		user.setVerified(true);
		boolean users = userRepository.save(user) != null ? true : false;

		return users;
	}

	public JavaMailSenderImpl mailservice() {
		senderimp.setUsername(System.getenv("email"));
		senderimp.setPassword(System.getenv("password"));
		senderimp.setPort(587);
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		senderimp.setJavaMailProperties(prop);
		return senderimp;
	}

	@Override
	public String emailVerify(String email) {
		UserDemo user = userRepository.findUserByEmail(email)
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY,env.getProperty("104")));
		
		String token = jwtGenerate.jwtToken(user.getUserId());
		this.mailservice();
		MailMessage.sendingMail(user, senderimp,token);
		return token;
	}

	@Override
	public UserDemo getUser(String token) {
		long id = (Long) jwtGenerate.parseJWT(token);
		UserDemo user = userRepository.getUserById(id)
				.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("104")));

		return user;
	}
}





