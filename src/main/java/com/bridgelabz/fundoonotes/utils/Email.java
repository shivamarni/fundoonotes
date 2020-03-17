package com.bridgelabz.fundoonotes.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Email {
	private String emailId;
	private String to;
	private String subject;
	private String body;
}
