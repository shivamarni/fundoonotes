package com.bridgelabz.fundoonotes.serviceimplementation;
import java.sql.Date;
import java.util.ArrayList;
/**
 * @author:shiva
 */
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.configuration.AppConfiguration;
import com.bridgelabz.fundoonotes.dto.UserUpdate;
import com.bridgelabz.fundoonotes.dto.UserLogin;
import com.bridgelabz.fundoonotes.dto.UserRegister;
import com.bridgelabz.fundoonotes.model.UserDemo;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.utils.JWTGenerator;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AppConfiguration config;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private JWTGenerator generate;

	@Transactional
	@Override
	public UserDemo login(String token) {
		int id=(Integer)generate.parseJWT(token);
		UserDemo user=userRepository.getUserById(id);
		if(user!=null)
		{
			return user;
		}
		return null;
	}

	@Transactional
	@Override
	public UserDemo register(UserRegister userDto) {
		UserDemo userEmail=userRepository.getUserByEmail(userDto.getEmail());
		if(userEmail==null)
		{
			userDto.setPassword(config.passwordEncoder().encode(userDto.getPassword()));
			UserDemo user=(UserDemo)modelMapper.map(userDto, UserDemo.class);
			user.setDate(new Date(System.currentTimeMillis()));
			user.setVerified("false");
			UserDemo result=userRepository.save(user);
			return result;
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
			user.setPassword(config.passwordEncoder().encode(user.getPassword()));
			return userRepository.save(user);
		}

		return null;
	}

	@Transactional
	@Override
	public List<UserDemo> getUsers() {
		List<UserDemo> ls=new ArrayList<>();
		userRepository.findAll().forEach(ls::add);
		return ls;


	}

	@Override
	public void removeUsers(String token) {
		int id=(Integer)generate.parseJWT(token);
		List<UserDemo> list=this.getUsers();
		for(UserDemo ls:list)
		{
			if(ls.getUserId()==id)
			{
				userRepository.delete(ls);
			}
		}

	}
	@Override
	public  Boolean verify(String token) {

		int id=(Integer)generate.parseJWT(token);
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
}





