package com.bridgelabz.fundoonotes.service;

import java.util.List;
import java.util.Optional;

import com.bridgelabz.fundoonotes.model.NoteInformation;
import com.bridgelabz.fundoonotes.model.UserDemo;

public interface UserCollabrator {
	List<NoteInformation> addCollabrator(long noteId, String token,String email);
	
	List<UserDemo> getAllCollabrators(String token);
	
	NoteInformation deleteCollabrator(long noteId, String token,String email);

}
