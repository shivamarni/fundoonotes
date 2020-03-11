package com.bridgelabz.fundoonotes.dto;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserNote {
	@NotNull
	private String title;
	@NotNull
	private String description;
	
	
	

}
