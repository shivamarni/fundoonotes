package com.bridgelabz.fundoonotes.service;

import java.util.ArrayList;
import java.util.List;

import com.bridgelabz.fundoonotes.dto.UpdateNote;
import com.bridgelabz.fundoonotes.dto.UserNote;
import com.bridgelabz.fundoonotes.model.NoteInformation;

public interface UserNoteService {
	
	NoteInformation addNotes(UserNote notes,String tokens);
	List<NoteInformation> getAllNotes();
	List<NoteInformation> updateNotes(String token,String id,UpdateNote updateDto);
	NoteInformation getNote(String id);
	List<NoteInformation> getNoteByUserId(String id); 
	NoteInformation deleteNotes(String token,String id);
	ArrayList<String> sortByName();
	List<String> ascendingSortByName();
	

}
