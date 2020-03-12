package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.ReminderDto;
import com.bridgelabz.fundoonotes.dto.UpdateNote;
import com.bridgelabz.fundoonotes.dto.UserNote;
import com.bridgelabz.fundoonotes.model.NoteInformation;
import com.bridgelabz.fundoonotes.response.NoteResponse;
import com.bridgelabz.fundoonotes.response.ResponseMessageStatus;
import com.bridgelabz.fundoonotes.service.UserLabelService;
import com.bridgelabz.fundoonotes.service.UserNoteService;

@RestController
@RequestMapping("/notes")
@PropertySource("classpath:message.properties")
public class NoteController {
	@Autowired
	private UserNoteService userNoteService;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UserLabelService labelService;
	
	
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
	@PutMapping("/pin/{noteId}/users/{token}")
	public ResponseEntity<NoteResponse> pin(@PathVariable long noteId, @PathVariable String token) {
		  NoteInformation note = userNoteService.pinNote(noteId, token);
		   return ResponseEntity.status(HttpStatus.CREATED)
					.body(new NoteResponse(env.getProperty("205"),note));
	}
	
	@PutMapping("/archieve/{noteId}/users/{token}")
	public ResponseEntity<NoteResponse> archieve(@PathVariable long noteId, @PathVariable String token) {
		    NoteInformation note = userNoteService.archieveNote(noteId, token);
		    return ResponseEntity.status(HttpStatus.CREATED)
					.body(new NoteResponse(env.getProperty("206"),note));
	}
	
	@GetMapping("/notes/getAllArchieve/users/{token}")
	public ResponseEntity<NoteResponse> getArchieve(@PathVariable String token) {
		  List<NoteInformation> note = userNoteService.getarchieved(token);
		  return ResponseEntity.status(HttpStatus.CREATED)
					.body(new NoteResponse(env.getProperty("206"),note));
	}
	@PutMapping("/{colour}/{noteId}/users/{token}")
	public ResponseEntity<NoteResponse> addColour(@PathVariable long noteId, @PathVariable String colour,
			@PathVariable String token) {
		   String note = userNoteService.addColour(noteId, token, colour);
		   return ResponseEntity.status(HttpStatus.CREATED)
					.body(new NoteResponse(env.getProperty("207"),note));
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

		@PostMapping("/addremainder/{noteId}/users/{token}")
		public ResponseEntity<NoteResponse> addRemainder(@PathVariable String token, @PathVariable long noteId,
				@RequestBody ReminderDto remainder) {
			     String note = userNoteService.addReminder(noteId, token, remainder);
			     return ResponseEntity.status(HttpStatus.CREATED)
							.body(new NoteResponse(env.getProperty("206"),note));
		}
		@DeleteMapping("/removeRemainder/{noteId}/users/{token}")
		public ResponseEntity<NoteResponse> removeRemainder(@PathVariable String token, @PathVariable long noteId) {
			     String note = userNoteService.removeReminder(noteId, token);
			      return ResponseEntity.status(HttpStatus.CREATED)
							.body(new NoteResponse(env.getProperty("206"),note));
		}
		@GetMapping(value = "/notes/ascendingSortByTitle")
		public ResponseEntity<NoteResponse> ascSortByNoteTitle() {
			List<String> note = userNoteService.ascSortByName();
			 return ResponseEntity.status(HttpStatus.CREATED)
						.body(new NoteResponse(env.getProperty("212"),note));
		}

		@GetMapping(value = "/notes/descSortByTitle")
		public ResponseEntity<NoteResponse> descSortByNoteTitle() {
			List<String> note = userNoteService.sortByName();
			 return ResponseEntity.status(HttpStatus.CREATED)
						.body(new NoteResponse(env.getProperty("213"),note));
		}
		@GetMapping(value = "/ascendingSortByName")
		public ResponseEntity<NoteResponse> ascSortByLabelName() {
			List<String> note = labelService.ascsortByName();
			 return ResponseEntity.status(HttpStatus.CREATED)
						.body(new NoteResponse(env.getProperty("212"),note));
		}
		@GetMapping(value = "/descSortByName")
		public ResponseEntity<NoteResponse> descSortByLabelName() {
			List<String> note = labelService.sortByName();
			 return ResponseEntity.status(HttpStatus.CREATED)
						.body(new NoteResponse(env.getProperty("213"),note));
		}
		
	
	@GetMapping("/notes/user/notes")
	public List<NoteInformation> getAllNotes() {

		return userNoteService.getAllNotes();
	}

}
