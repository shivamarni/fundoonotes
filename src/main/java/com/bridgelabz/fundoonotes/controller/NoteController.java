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
import com.bridgelabz.fundoonotes.response.ResponseMessageStatus;
import com.bridgelabz.fundoonotes.service.UserNoteService;

@RestController
public class NoteController {
	@Autowired
	private UserNoteService userNoteService;
	
	@PostMapping(value = "/notes/users/{token}")
	public ResponseEntity<ResponseMessageStatus> createNotes(@RequestBody UserNote notes, @PathVariable String token) {

		
		NoteInformation note = userNoteService.addNotes(notes, token);
		if (note != null) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new ResponseMessageStatus("Note Details Saved Successfully", 200, notes));
		} else {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
					.body(new ResponseMessageStatus("Already existing user", 400, notes));
		}
	}

	@PutMapping(value = "/notes/{id}/users/{token}")
	public ResponseEntity<NoteResponse> updateNote(@PathVariable String token, @PathVariable long id,
			@RequestBody UpdateNote dto) {
		List<NoteInformation> note = userNoteService.updateNotes(token, id, dto);
		if (note != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new NoteResponse("Note Title Updated", dto));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new NoteResponse("Note Not Exist", dto));
	}

	

	@GetMapping(value = "/notes/{id}")
	public ResponseEntity<NoteResponse> getNote(@PathVariable long id) {
		NoteInformation result = userNoteService.getNote(id);
		if (result != null) {
			// String token = generator.jwtToken(result.getId());
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new NoteResponse("200-OK", result));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new NoteResponse("Note Not Exist", result));
	}
	

	@GetMapping(value = "/notes/users/{token}")
	public ResponseEntity<NoteResponse> getNotesByUserId(@PathVariable long id) {
		List<NoteInformation> result = userNoteService.getNoteByUserId(id);
		if (result != null) {
			// return result;
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new NoteResponse("200-OK", result));
		}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
				.body(new NoteResponse("Not an existing user", result));
	}

	
	@DeleteMapping(value = "/notes/{noteId}/users/{token}")
	public ResponseEntity<NoteResponse> deleteNote(@PathVariable long noteId, @PathVariable String token) {
		NoteInformation result = userNoteService.removeNotes(token, noteId);
		if (result == null) {
			// return result;
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("Note Title", "sucess")
					.body(new NoteResponse("200-OK", result));
		}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new NoteResponse("Not existing user", result));
	}
	
	@GetMapping("/notes/getAllArchieve/users/{token}")
	public ResponseEntity<ResponseMessageStatus> getArchieve(@PathVariable String token) {
		List<NoteInformation> list = userNoteService.getarchieved(token);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessageStatus(" archieved notes", 200, list));
	}

	
	@GetMapping("/notes/getAlltrashed/users/{token}")
	public ResponseEntity<ResponseMessageStatus> getTrashed(@PathVariable String token) {
		List<NoteInformation> list = userNoteService.getAlltrashednotes(token);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessageStatus(" trashed notes", 200, list));
	}

	@GetMapping("/notes/getAllPinned/users/{token}")
	public ResponseEntity<ResponseMessageStatus> getPinned(@PathVariable String token) {
		List<NoteInformation> list = userNoteService.getAllPinneded(token);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessageStatus(" pinned notes", 200, list));
	}

	
	@GetMapping("/notes/user/notes")
	public List<NoteInformation> getAllNotes() {

		return userNoteService.getAllNotes();
	}

}
