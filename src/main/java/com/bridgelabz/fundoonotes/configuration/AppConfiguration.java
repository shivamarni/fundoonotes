package com.bridgelabz.fundoonotes.configuration;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
@Configuration
public class AppConfiguration {
	@Bean
	public  PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
	@Bean 
	public JavaMailSenderImpl mail()
	{
		return new JavaMailSenderImpl();
	}

}
