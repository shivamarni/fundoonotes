package com.bridgelabz.fundoonotes.serviceimplementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.UpdateNote;
import com.bridgelabz.fundoonotes.dto.UserNote;
import com.bridgelabz.fundoonotes.model.NoteInformation;
import com.bridgelabz.fundoonotes.model.UserDemo;
import com.bridgelabz.fundoonotes.repository.UserNoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.UserNoteService;
import com.bridgelabz.fundoonotes.utils.JWTGenerator;
@Service
public class UserNoteServiceImplementation implements UserNoteService {

	@Autowired
	private UserNoteRepository userNoteRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private JWTGenerator jwtGenerate;

	@Transactional
	@Override
	public NoteInformation addNotes(UserNote notes, String token) {
		try
		{
			Integer id=(Integer)jwtGenerate.parseJWT(token);
			UserDemo user=userRepository.getUserById(id);
			System.out.println(user);
			if(user!=null)
			{
				NoteInformation note=(NoteInformation)modelMapper.map(notes, NoteInformation.class);
				System.out.println("------------"+note);

				note.setTitle(note.getTitle());
				note.setDescription(note.getDescription());
				note.setIsPinned(0);
				note.setIsArcheived(0);
				note.setIsTrashed(0);
				note.setReminder(null);
				note.setColor("cyan");
				note.setUser(user);
				note.setCreateDateAndTime(LocalDateTime.now());


				return userNoteRepository.save(note);
			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}


		return null;
	}

	@Transactional
	@Override
	public List<NoteInformation> getAllNotes() {
		List<NoteInformation> notes=new ArrayList<>();
		userNoteRepository.findAll().forEach(notes::add);

		return notes;
	}

	@Transactional
	@Override
	public List<NoteInformation> updateNotes(String token,String id, UpdateNote updateDto) {
		List<NoteInformation> notes=this.getNoteByUserId(token);
		int userId=Integer.parseInt(id);
		try
		{
			Optional<NoteInformation> data=notes.stream().filter(t->t.getNoteId()==userId).findFirst();
			data.ifPresent(da->{
				da.setTitle(updateDto.getTitle());
				da.setUpdateDateAndTime(LocalDateTime.now());
				userNoteRepository.save(da);
			});
			if(data.equals(Optional.empty()))
			{
				return null;
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return notes;
	}

	@Transactional
	@Override
	public NoteInformation getNote(String id) {
		NoteInformation notes=userNoteRepository.findNoteById(Integer.parseInt(id));
		if(notes!=null)
		{
			return notes;
		}
		return null;
	}

	@Transactional
	@Override
	public List<NoteInformation> getNoteByUserId(String token) {
		int userId=(Integer) jwtGenerate.parseJWT(token);

		try
		{
			List<NoteInformation> user=userNoteRepository.findNoteByUserId(userId);
			if(user!=null)
			{
				return user;
			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}

	@Transactional
	@Override
	public NoteInformation deleteNotes(String token,String id) {
		List<NoteInformation> notes=this.getNoteByUserId(token);
		int userId=Integer.parseInt(id);
		try
		{
			if(notes!=null)
			{
				Optional<NoteInformation> data=notes.stream().filter(t->t.getNoteId()==userId).findFirst();
				data.ifPresent(da->{
					userNoteRepository.delete(da);
				});
				if(data.equals(Optional.empty()))
				{
					return null;
				}
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return notes.get(0);
	}

	@Transactional
	@Override
	public ArrayList<String> sortByName() {
		ArrayList<String> titles=new ArrayList<>();
		List<NoteInformation> noteList=this.getAllNotes();
		noteList.forEach(t->{
			titles.add(t.getTitle());
		});
		Collections.sort(titles,Collections.reverseOrder());

		return titles;
	}

	@Transactional
	@Override
	public List<String> ascendingSortByName() {
		ArrayList<String> titles=new ArrayList<>();
		List<NoteInformation> noteList=this.getAllNotes();
		noteList.forEach(t->{titles.add(t.getTitle());
		});
		Collections.sort(titles,Collections.reverseOrder());


		return titles;
	}



}
