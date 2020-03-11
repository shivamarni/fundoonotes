package com.bridgelabz.fundoonotes.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoteResponse {
	
	private String message;
	
	private Object notes;
	

}
