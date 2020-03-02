package com.bridgelabz.fundoonotes.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoonotes.model.UserDemo;
@Component
public class MailMessage {
	
	public static void sendingMail(UserDemo user,JavaMailSenderImpl mailsender,String token)
	{
		SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
		simpleMailMessage.setTo(user.getEmail());
		simpleMailMessage.setSubject("registration confirming");
		simpleMailMessage.setText("hello"+user.getName()+"verify the user:\n"+"http://localhost:8083/verify/"+token);
		mailsender.send(simpleMailMessage);
	}

}
