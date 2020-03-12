package com.bridgelabz.fundoonotes.serviceimplementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.ReminderDto;
import com.bridgelabz.fundoonotes.dto.UpdateNote;
import com.bridgelabz.fundoonotes.dto.UserNote;
import com.bridgelabz.fundoonotes.exception.NoteException;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.model.NoteInformation;
import com.bridgelabz.fundoonotes.model.UserDemo;
import com.bridgelabz.fundoonotes.repository.UserNoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.UserNoteService;
import com.bridgelabz.fundoonotes.utils.JWTGenerator;
@Service
@PropertySource("classpath:message.properties")
public class UserNoteServiceImplementation implements UserNoteService {

	@Autowired
	private UserNoteRepository userNoteRepository;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private Environment env;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private JWTGenerator jwtGenerate;

	@Override
	public List<NoteInformation> getAllNotes() {
		List<NoteInformation> notes=new ArrayList<>();
		userNoteRepository.findAll().forEach(notes::add);

		return notes;
	}
	@Transactional
	@Override
	public NoteInformation addNotes(UserNote notes, String token) {
		NoteInformation note=new NoteInformation();
		try {
			Long id = (Long) jwtGenerate.parseJWT(token);
			UserDemo user = userRepository.getUserById(id)
					.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY,env.getProperty("104")));
		

				BeanUtils.copyProperties(notes, note);
				note.setColor("ash");
				note.setIsArcheived(0);
				note.setCreateDateAndTime(LocalDateTime.now());
				note.setIsPinned(0);
				note.setIsTrashed(0);
				note.setReminder(null);
				note.setTitle(notes.getTitle());
				note.setDescription(notes.getDescription());
				user.getNotes().add(note);
				
				return userNoteRepository.save(note);
	
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,env.getProperty("104"));
		}
	}
	@Transactional
	@Override
	public List<NoteInformation> updateNotes(String token, long id, UpdateNote updateDto) {
List<NoteInformation> notes = this.getNoteByUserId(id);

		
		//int nId = Integer.parseInt(id);
		try {
			if (notes != null) {
				Optional<NoteInformation> data = notes.stream().filter(t -> t.getNoteId() == id).findFirst();
				data.ifPresent(da -> {
					da.setTitle(updateDto.getTitle());
					da.setUpdateDateAndTime(LocalDateTime.now());
					userNoteRepository.save(da);
				});
				if (data.equals(Optional.empty())) {
					return null;
				}
			}
		} catch (Exception ae) {
			ae.printStackTrace();
			//throw new NoteException("Label Record Not Exist");
		}
		return notes;
	}
	@Transactional
	@Override
	public NoteInformation getNote(long id) {
		 
			NoteInformation notes = userNoteRepository.findNoteById(id);
			if (notes != null) {
				return notes;
			}

			return null;
	}
	
	@Transactional
	@Override
	public NoteInformation removeNotes(String token,long id) {

		List<NoteInformation> notes = this.getNoteByUserId(id);
		
		//int nId = Integer.parseInt(id);
		try {
			if (notes != null) {
				Optional<NoteInformation> data = notes.stream().filter(t -> t.getNoteId() == id).findFirst();
				data.ifPresent(da -> {
					userNoteRepository.delete(da);
				});
				if (data.equals(Optional.empty())) {
					return null;
				}
			}
		} catch (Exception ae) {
			throw new UserException(HttpStatus.ALREADY_REPORTED,env.getProperty("208"));
		}
		return notes.get(0);
	}
	@Transactional
	@Override
	public NoteInformation archieveNote(long id, String token) {
		List<NoteInformation> notes = this.getNoteByUserId(id);
		
		//int nId = Integer.parseInt(id);
		try {
			if (notes != null) {
				Optional<NoteInformation> data = notes.stream().filter(t -> t.getNoteId() == id).findFirst();
				data.ifPresent(da -> {
					da.setIsPinned(0);
					da.setIsArcheived(1);
					da.setUpdateDateAndTime(LocalDateTime.now());
					userNoteRepository.save(da);
				});
				
			}
		} catch (Exception ae) {
	
			throw new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("404"));
		}
		return null;

		
	}
	
	@Transactional
	@Override
	public NoteInformation pinNote(long id, String token) {
		List<NoteInformation> notes = this.getNoteByUserId(id);
		
		//int nId = Integer.parseInt(id);
		try {
			if (notes != null) {
				Optional<NoteInformation> data = notes.stream().filter(t -> t.getNoteId() == id).findFirst();
				data.ifPresent(da -> {
					da.setIsPinned(1);
					da.setIsArcheived(0);
					da.setUpdateDateAndTime(LocalDateTime.now());
					userNoteRepository.save(da);
				});
				
			}
		} catch (Exception ae) {
			throw new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("404"));
		}
		return null;

	}
	@Transactional
	@Override
	public List<NoteInformation> getAlltrashednotes(String token) {

		try {
			long userid = (Long) jwtGenerate.parseJWT(token);
			Optional<UserDemo> userData = userRepository.getUserById(userid);
			if (userData != null) {
				List<NoteInformation> list = userNoteRepository.restoreNote(userid);
				return list;
			}
		} catch (Exception e) {
			throw new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("404"));
		}
		return null;
	}
	@Transactional
	@Override
	public List<NoteInformation> getarchieved(String token) {
		try {
			long userid = (Long) jwtGenerate.parseJWT(token);
			Optional<UserDemo> userData = userRepository.getUserById(userid);
			if (userData != null) {
				List<NoteInformation> list = userNoteRepository.getArchievedNotes(userid);
				return list;
			}
		} catch (Exception e) {
			throw new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("404"));
		}
		return null;
	}
	@Transactional
	@Override
	public String addColour(long id, String token, String colour) {
	
		List<NoteInformation> notes = this.getNoteByUserId(id);
		
		//int nId = Integer.parseInt(id);
		try {
			if (notes != null) {
				Optional<NoteInformation> data = notes.stream().filter(t -> t.getNoteId() == id).findFirst();
				data.ifPresent(da -> {
					da.setColor(colour);
					userNoteRepository.save(da);
				});
				if (data.equals(Optional.empty())) {
					return "notes not coloured";
				}
			}
		} catch (Exception ae) {
			throw new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("404"));
		}
		return colour;
	}
	@Transactional  
	@Override
	public List<NoteInformation> getAllPinneded(String token) {
		try {
			long userid = (Long) jwtGenerate.parseJWT(token);
			Optional<UserDemo> userData = userRepository.getUserById(userid);
			if (userData != null) {
				List<NoteInformation> list = userNoteRepository.getPinnededNotes(userid);
				return list;
			}
		} catch (Exception e) {
			throw new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("404"));
		}
		return null;
	}
	@Transactional
	@Override
	public String removeReminder(long id, String token) {
		List<NoteInformation> notes = this.getNoteByUserId(id);
		
	//	int nId = Integer.parseInt(id);
		try {
			if (notes != null) {
				Optional<NoteInformation> data = notes.stream().filter(t -> t.getNoteId() == id).findFirst();
				data.ifPresent(noteData -> {
					noteData.setReminder(null);
					userNoteRepository.save(noteData);
				});
				if (data.equals(Optional.empty())) {
					return "notes not pinned";
				}
			}
		} catch (Exception ae) {
			throw new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("404"));
		}
		return token;

	}
	@Transactional
	@Override
	public ArrayList<String> sortByName() {
		ArrayList<String> noteTitles = new ArrayList<>();
		List<NoteInformation> Notelist = this.getAllNotes();
				Notelist.forEach(t -> {
			noteTitles.add(t.getTitle());
		});
		Collections.sort(noteTitles, Collections.reverseOrder());
		return noteTitles;
	}
	@Transactional
	@Override
	public List<String> ascSortByName() {
		ArrayList<String> noteTitles = new ArrayList<>();
		List<NoteInformation> Notelist = this.getAllNotes();
		
		Notelist.forEach(t -> {
			noteTitles.add(t.getTitle());
		});
		Collections.sort(noteTitles);
		return noteTitles;
	}
	@Transactional
	@Override
	public String addReminder(long id, String token, ReminderDto reminder) {
		
		List<NoteInformation> notes = this.getNoteByUserId(id);
		
		//int nId = Integer.parseInt(id);
		try {
			if (notes != null) {
				NoteInformation noteData = notes.stream().filter(t -> t.getNoteId() == id).findFirst()
						.orElseThrow(() -> new UserException(HttpStatus.BAD_REQUEST, env.getProperty("104")));
				//data.ifPresent(noteData -> {
					noteData.setReminder(reminder.getReminder());
					userNoteRepository.save(noteData);
				//});
				if (noteData.equals(Optional.empty())) {
					return "notes not pinned";
				}
			}
		} catch (Exception ae) {
			throw new NoteException(HttpStatus.INTERNAL_SERVER_ERROR,"Note Record Not added Reminder due to Internal Error");
		}
		return token;

	}
	
	@Override
	public List<NoteInformation> getNoteByUserId(long id) {
		

		try {
			List<NoteInformation> user = userNoteRepository.findNoteByUserId(id);

			if (user != null) {
				return user;
			}
		} catch (Exception ae) {
			ae.printStackTrace();
			//throw new NoteNotFoundException("invalid Number registered");
		}
		return null;
	}
	

	

}
