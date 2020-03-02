package com.bridgelabz.fundoonotes.serviceimplementation;
import java.sql.Date;
import java.util.ArrayList;
/**
 * @author:shiva
 */
import java.util.List;
import java.util.Properties;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.configuration.AppConfiguration;
import com.bridgelabz.fundoonotes.dto.UserUpdate;
import com.bridgelabz.fundoonotes.dto.UserRegister;
import com.bridgelabz.fundoonotes.model.UserDemo;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.utils.JWTGenerator;
import com.bridgelabz.fundoonotes.utils.MailMessage;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AppConfiguration configuration;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private JWTGenerator jwtGenerate;

	@Autowired
	private JavaMailSenderImpl mailSenderImplementation;

	@Transactional
	@Override
	public UserDemo login(String token) {
		int id=(Integer)jwtGenerate.parseJWT(token);
		UserDemo userDemo=userRepository.getUserById(id);
		if(userDemo!=null)
		{
			return userDemo;
		}
		return null;
	}

	@Transactional
	@Override
	public UserDemo register(UserRegister userDto) {
		UserDemo userEmail=userRepository.getUserByEmail(userDto.getEmail());

			if(userEmail==null)
		
		{
			userDto.setPassword(configuration.passwordEncoder().encode(userDto.getPassword()));
			UserDemo user=(UserDemo)modelMapper.map(userDto, UserDemo.class);
			user.setDate(new Date(System.currentTimeMillis()));
			user.setVerified("false");
			UserDemo info=userRepository.save(user);
			this.mailservice();
			MailMessage.sendingMail(user,mailSenderImplementation,jwtGenerate.jwtToken(user.getUserId()));
			return info;
		}
		

		return null;
	}

	@Transactional
	@Override
	public UserDemo forgotPassword(UserUpdate userDto) {
		UserDemo userEmail=userRepository.getUserByEmail(userDto.getEmail());
		
			if(userEmail!=null)
		
		{
			UserDemo user=(UserDemo)modelMapper.map(userDto, UserDemo.class);
			user.setPassword(configuration.passwordEncoder().encode(user.getPassword()));
			return userRepository.save(user);
		}

		return null;
	}

	@Transactional
	@Override
	public List<UserDemo> getAllUsers() {
		List<UserDemo> ls=new ArrayList<>();
		userRepository.findAll().forEach(ls::add);
		return ls;


	}

	@Override
	public void removeUsers(String token) {
		int id=(Integer)jwtGenerate.parseJWT(token);
		List<UserDemo> list=this.getAllUsers();
		for(UserDemo ls:list)
		{
			if(ls.getUserId()==id)
			{
				userRepository.delete(ls);
			}
		}

	}
	@Transactional
	@Override
	public  Boolean verifyToken(String token) {

		int id=(Integer)jwtGenerate.parseJWT(token);
		System.out.println(id);
		UserDemo user=userRepository.getUserById(id);
		System.out.println(user.getName());
		user.setVerified("true");
		UserDemo users=userRepository.save(user);
		if(users!=null)
		{
			return true;
		}

		return false;
	}
	public JavaMailSenderImpl mailservice()
	{
		mailSenderImplementation.setUsername(System.getenv("email"));
		mailSenderImplementation.setPassword(System.getenv("password"));
		mailSenderImplementation.setPort(587);
		Properties properties=new Properties();
		properties.put("mail.smtp.auth","true");
		properties.put("mail.smtp.starttls.enable","true");
		properties.put("mail.smtp.host","smtp.gmail.com");
		properties.put("mail.smtp.port","587");
		mailSenderImplementation.setJavaMailProperties(properties);
		return mailSenderImplementation;

	}
}





