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

import com.bridgelabz.fundoonotes.dto.UpdateLabel;
import com.bridgelabz.fundoonotes.dto.UserLabelDto;
import com.bridgelabz.fundoonotes.dto.UserNote;
import com.bridgelabz.fundoonotes.model.UserLabel;
import com.bridgelabz.fundoonotes.response.LabelResponse;
import com.bridgelabz.fundoonotes.response.NoteResponse;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.UserLabelService;

@RestController
@RequestMapping("/labels")
@PropertySource("classpath:message.properties")
public class LabelController {

	@Autowired
	UserLabelService labelService;
	
	@Autowired
	private Environment env;

	@PostMapping(value = "/label/{token}/notes")
	public ResponseEntity<LabelResponse> createLabel(@RequestBody UserLabelDto label, @PathVariable String token) {

		UserLabel note = labelService.createLable(label, token);
		if (note != null) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new LabelResponse("Note Details Saved Successfully", 200,note));
		} else {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
					.body(new LabelResponse("Already existing user",400, note));
		}
	}


	@PutMapping(value = "/labels/{id}/users/{token}")
	public ResponseEntity<NoteResponse> updateLabel(@PathVariable String token,@PathVariable long id, @RequestBody UpdateLabel dto) {
		UserLabel label = labelService.updateLabel(id,token, dto);
		if (label != null) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new NoteResponse("Updated successfully", dto));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new NoteResponse("Note Not Exist", label));
	}


	@DeleteMapping(value = "/labels/{id}/{token}")
	public ResponseEntity<LabelResponse> deleteLabel(@PathVariable long id,@PathVariable String token) {
		List<UserLabel> result = labelService.removeLabel(token,id);
		if (result != null) {

			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new LabelResponse("Record Deleted succesfully", 200, result));
		}
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
				.body(new LabelResponse("Already Deleted user", 400, result));
	}
	@PostMapping(value = "/{lid}/{token}/{noteId}")
	public ResponseEntity<Response> addExistingNotesToLabel(long noteId, String token, long labelId) {
		boolean label = labelService.addExistingNotesToLabel(noteId, token, labelId);
		if(label)
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response(env.getProperty("301"),200));
		return null;

	}

	@PostMapping(value = "/{lid}/{token}")
	public ResponseEntity<LabelResponse> addNotesToLabel(@RequestBody UserNote label, @PathVariable String token,@PathVariable long lid) {

		UserLabel lnote = labelService.addNotesToLabel(label, token,lid);
		
		 return ResponseEntity.status(HttpStatus.CREATED)
					.body(new LabelResponse(env.getProperty("301"),200,lnote));
	

	}
	@GetMapping(value = "/label/notes/{id}")
	public ResponseEntity<LabelResponse> getLabel(@PathVariable long id) {
		UserLabel result = labelService.getLableById(id);
		if (result != null) {
			// String token = generator.jwtToken(result.getId());
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("Note Title", result.getLableName())
					.body(new LabelResponse("200-OK", 200, result));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new LabelResponse("Label not existing", 400, result));
	}
	
	


	@GetMapping("/label/notes")
	public List<UserLabel> getAllLables() {
		return labelService.getAllLables();
	}



	@GetMapping(value = "/label/user/{token}")
	public ResponseEntity<LabelResponse> getLabelByUserId(@PathVariable String token) {
		List<UserLabel> result = labelService.getLableByUserId(token);
		if (result != null) {
			// return result;
			return ResponseEntity.status(HttpStatus.ACCEPTED).header("Note Title", "success")
					.body(new LabelResponse("200-OK", 200, result));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new LabelResponse("user not Exist", 400, result));
	}
}
	

