package com.bridgelabz.fundoonotes.serviceimplementation;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.exception.LabelException;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.model.NoteInformation;
import com.bridgelabz.fundoonotes.model.UserDemo;
import com.bridgelabz.fundoonotes.repository.UserNoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.UserCollabrator;
import com.bridgelabz.fundoonotes.utils.JWTGenerator;
@Service
public class UserCollabratorImplementation implements UserCollabrator {

	@Autowired
	private UserNoteRepository userNoteRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JWTGenerator jwtGenerate;
	
	@Transactional
	@Override
	
	public List<NoteInformation> addCollabrator(long noteId, String token,String email) {

		Optional<UserDemo> collabrator = userRepository.findUserByEmail(email);

		if (collabrator.isPresent()) {
			try {
				long uid = (long) jwtGenerate.parseJWT(token);
				System.out.println("UserId:" + uid);

				List<NoteInformation> note = userNoteRepository.findNoteByUserId(uid);
				if (note != null) {
					NoteInformation data = note.stream().filter(t -> t.getNoteId() == noteId).findFirst()
							.orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, "NoteId not Exist"));

					collabrator.ifPresent(t -> t.getCollabrate().add(data));

					System.out.println("da::" + data);

					return note;
				}
			} catch (Exception e) {
				// throw new UserException("User doesnot exist with this id");
			}

		}
		return null;

	}

	@Transactional
	@Override
	public List<NoteInformation> getAllCollabrators(String token) {
		UserDemo user1=new UserDemo();
		long id = (long) jwtGenerate.parseJWT(token);
		List<NoteInformation> user = user1.getCollabrate();
		return user;

//		if (user != null) {
//			return user;
//		}
//		throw new UserException(HttpStatus.BAD_GATEWAY, "user not exist");
	}

	@Transactional
	@Override
	public NoteInformation deleteCollabrator(long noteId, String token,String email) {
		Optional<UserDemo> collabrator = userRepository.findUserByEmail(email);
		// User user=null;
		if (collabrator.isPresent()) {
			try {
				long uid = (long) jwtGenerate.parseJWT(token);
				System.out.println("UserId:" + uid);

				List<NoteInformation> note = userNoteRepository.findNoteByUserId(uid);
				if (note != null) {
					NoteInformation data = note.stream().filter(t -> t.getNoteId() == noteId).findFirst()
							.orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, "NoteId not Exist"));

					collabrator.ifPresent(t -> t.getCollabrate().remove(note));

					System.out.println("da::" + data);

					return data;
				}
			} catch (Exception e) {
				// throw new UserException("User doesnot exist with this id");
			}

		}
		return null;
	}
}
