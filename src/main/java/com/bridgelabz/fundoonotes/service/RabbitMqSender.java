package com.bridgelabz.fundoonotes.service;

import org.springframework.stereotype.Service;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
@Service
public class RabbitMqSender {
	@Autowired
   private AmqpTemplate rabbitTemplate;
	
	@Value("${fundoo.rabbitmq.exchange}")
	private String exchange;
	
	@Value("${fundoo.rabbitmq.routingkey}")
	private String routingkey;	
	
	public void send(String company) {
		rabbitTemplate.convertAndSend(exchange, routingkey, company);
		System.out.println("Send msg = " + company);
	    
	}
}
