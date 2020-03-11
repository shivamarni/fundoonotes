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

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.UserUpdate;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.dto.UserLogin;
import com.bridgelabz.fundoonotes.dto.UserRegister;
import com.bridgelabz.fundoonotes.model.UserDemo;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.utils.JWTGenerator;
import com.bridgelabz.fundoonotes.utils.MailMessage;
@Service
@PropertySource("classpath:Message.properties")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncode;;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private Environment env;
	@Autowired
	private JavaMailSenderImpl senderimp;
	@Autowired
	private JWTGenerator jwtGenerate;
	
    @Transactional
	@Override
	public UserDemo login(UserLogin userDto) {
		Optional<UserDemo> user = userRepository.getUserByEmail(userDto.getEmail());
				
		if(user!=null) {
		UserDemo userDetails = new UserDemo();
		
		BeanUtils.copyProperties(userDto, userDetails);
		
		System.out.println("----::"+passwordEncode.matches(userDetails.getPassword(), userDto.getPassword()));
		System.out.println("user:"+userDetails.getPassword());
		System.out.println("dto pass:"+userDto.getPassword());
		if (!(passwordEncode.matches(userDetails.getPassword(), userDto.getPassword()))) {
			
			return userDetails;
		}
		}
		throw new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("400"));
	}
	
    @Transactional
	@Override
	public UserDemo register(UserRegister userDto) {
		Optional<UserDemo> useremail = userRepository.getUserByEmail(userDto.getEmail());
		System.out.println(useremail);
		if (useremail!=null)
			
		try {
			

			UserDemo user = new UserDemo();
			BeanUtils.copyProperties(userDto, user);

		
			user.setPassword(passwordEncode.encode(userDto.getPassword()));
			user.setDate(LocalDateTime.now());
			user.setVerified(false);
			UserDemo result = userRepository.save(user);

			

			this.mailservice();
			MailMessage.sendingMail(user, senderimp, jwtGenerate.jwtToken(user.getUserId()));

			return result;

		} catch (Exception ae) {
			throw new UserException(HttpStatus.INTERNAL_SERVER_ERROR, env.getProperty("500"));
		}
		return null;
	}
    @Transactional
	@Override
	public UserDemo forgotPassword(UserUpdate updateDto) {
    	try
    	{
    		Optional<UserDemo> user=userRepository.getUserByEmail(updateDto.getEmail());
    		//System.out.println("email:"+user.getEmail());
    		UserDemo userInfo=modelMapper.map(updateDto,UserDemo.class);
    		userInfo.setPassword(passwordEncode.encode(userInfo.getPassword()));
    		return userRepository.save(userInfo);
    	}catch(Exception e)
    	{
    		throw new UserException(HttpStatus.BAD_GATEWAY,env.getProperty("502"));
    	}
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

		int id = (Integer) jwtGenerate.parseJWT(token);
		UserDemo user = userRepository.getUserById(id);
		System.out.println(user.getName());
		user.setVerified(true);
		UserDemo users = userRepository.save(user);
		if (users != null) {
			return true;
		}
		return false;
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
}





