package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.UpdateNote;
import com.bridgelabz.fundoonotes.dto.UserNote;
import com.bridgelabz.fundoonotes.model.NoteInformation;
import com.bridgelabz.fundoonotes.response.NoteResponse;
import com.bridgelabz.fundoonotes.service.UserNoteService;

@RestController
public class NoteController {
	@Autowired
	private UserNoteService userNoteService;
	
	@PostMapping(value="/notes/create/{token}")
	public ResponseEntity<NoteResponse> createNotes(@RequestBody UserNote notes, @PathVariable("token") String token)
	{
		System.out.println("shiva");
		NoteInformation note=userNoteService.addNotes(notes,token);
		System.out.println(note);
		if(note!=null)
		{
			return ResponseEntity.status(HttpStatus.CREATED).body(new NoteResponse("note details saved",200,notes));
		}
		else
		{
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new NoteResponse("Already existing user",400,notes));
		}
		
		
	}
	@GetMapping("/notes/user/notes")
	public List<NoteInformation> getAllNotes()
	{
		return userNoteService.getAllNotes();
	}
	
	@GetMapping(value="/notes/{id}")
	public ResponseEntity<NoteResponse> getNote(@PathVariable String id)
	{
		NoteInformation result=userNoteService.getNote(id);
		if(result!=null)
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new NoteResponse("200 ok",result));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new NoteResponse("note not exit",result));
	}
	
	@PutMapping(value="/notes/{id}/users/{token}")
	public ResponseEntity<NoteResponse> updateNote(@PathVariable String token, @PathVariable String id,@RequestBody UpdateNote dto)
	{
		List<NoteInformation> note=userNoteService.updateNotes(token, id, dto);
		if(note!=null)
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new NoteResponse("title updated", dto));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new NoteResponse("Note does not exit",dto));
		
	}
	
	@GetMapping(value="/notes/users/{token}")
	public ResponseEntity<NoteResponse> getNotesByUserId(@PathVariable String token)
	{
		List<NoteInformation> result=userNoteService.getNoteByUserId(token);
		
		if(result!=null)
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("note title", "successful")
					.body(new NoteResponse("200_ok", result));
		}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new NoteResponse("not exiting user",result));
	}
	@DeleteMapping(value="/notes/{id}/users/{token}")
	public ResponseEntity<NoteResponse> deleteNote(@PathVariable String id,@PathVariable String token)
	{
		NoteInformation result=userNoteService.deleteNotes(token, id);
		if(result==null)
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("note title", "successful")
					.body(new NoteResponse("200_ok", result));
		}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new NoteResponse("not exiting user",result));
	}

	
}
