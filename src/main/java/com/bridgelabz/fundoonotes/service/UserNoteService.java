package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.UpdateNote;
import com.bridgelabz.fundoonotes.dto.UserNote;
import com.bridgelabz.fundoonotes.model.NoteInformation;

public interface UserNoteService {
	
	List<NoteInformation> getAllNotes();

	NoteInformation addNotes(UserNote notes, String token);

	List<NoteInformation> updateNotes(String token, long id, UpdateNote updateDto);
	
	NoteInformation getNote(long id);

	List<NoteInformation> getNoteByUserId(long id);

	NoteInformation removeNotes(String token, long id);


	String archieveNote(long id, String token);

	String pinNote(long id, String token);

	List<NoteInformation> getAlltrashednotes(String token);

	List<NoteInformation> getarchieved(String token);

	String addColour(long id, String token, String colour);

	List<NoteInformation> getAllPinneded(String token);

	//String addReminder(String id, String token, ReminderDto reminder);

	String removeReminder(long id, String token);
	
}
