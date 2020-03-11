package com.bridgelabz.fundoonotes.dto;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Component
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserRegister {
	
	private String name;
	private String email;
	private String password;
	private long mobile;

	

}
