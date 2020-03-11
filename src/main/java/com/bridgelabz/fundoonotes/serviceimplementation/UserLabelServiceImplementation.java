package com.bridgelabz.fundoonotes.serviceimplementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

import com.bridgelabz.fundoonotes.dto.UpdateLabel;
import com.bridgelabz.fundoonotes.dto.UserLabelDto;
import com.bridgelabz.fundoonotes.exception.NoteNotFoundException;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.model.NoteInformation;
import com.bridgelabz.fundoonotes.model.UserDemo;
import com.bridgelabz.fundoonotes.model.UserLabel;
import com.bridgelabz.fundoonotes.repository.LabelRepository;
import com.bridgelabz.fundoonotes.repository.UserNoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.UserLabelService;
import com.bridgelabz.fundoonotes.service.UserNoteService;
import com.bridgelabz.fundoonotes.utils.JWTGenerator;
@Service
@PropertySource("classpath:Message.properties")
public class UserLabelServiceImplementation implements UserLabelService{

	@Autowired
	private UserNoteRepository userNoteRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserNoteService noteService;

	@Autowired
	private Environment env;

	@Autowired
	private LabelRepository labelRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private JWTGenerator jwtGenerate;
	
	@Transactional
	@Override
	public List<UserLabel> getAllLables() {
		List<UserLabel> notes = new ArrayList<>();

		labelRepository.findAll().forEach(notes::add);
		return notes;
	}
	@Transactional 
	@Override
	public UserLabel createLable(UserLabelDto labelDto, String token) {
		long id=jwtGenerate.parseJWT(token);
		UserDemo user=userRepository.getUserById(id);
		if(user!=null)
		{
			UserLabel label=new UserLabel();
			//UserLabel label=modelMapper.map(labelDto,UserLabel.class);
			BeanUtils.copyProperties(labelDto, label);
			label.setLableName(labelDto.getName());
			label.setUserId(id);
			label.setUpdateDateAndTime(LocalDateTime.now());
			UserLabel result = labelRepository.save(label);
			return result;
		}
		return null;
	}

	@Transactional
	@Override
	public List<UserLabel> removeLabel(String token, long id) {
		//int lId = Integer.parseInt(id);
		List<UserLabel> list = this.getLableByUserId(token);

		Optional<UserLabel> data = null;
		try {
			if (list != null) {
				data = list.stream().filter(t -> t.getUserId() == id).findFirst();
				data.ifPresent(da -> {
					labelRepository.delete(da);
				});
				if (data.equals(Optional.empty())) {
					return null;
				}
			}
		} catch (Exception ae) {
			throw new NoteNotFoundException("user Record Not Exist");
		}

		return list;
	}
	@Transactional
	@Override
	public List<UserLabel> getLableByUserId(String token) {
		int id = (Integer) jwtGenerate.parseJWT(token);
		List<UserLabel> label = labelRepository.findLabelByUserId(id);
		if (label != null) {
			System.out.println(label+"userlbbb");
			return label;

		}
		return null;
	}
	@Transactional
	@Override
	public UserLabel getLableById(long id) {
		UserLabel label = labelRepository.findLabelById(id);
		if (label != null) {
			return label;
		}
		return null;
	}
	@Transactional

	public UserLabel updateLabel(long id, String token, UpdateLabel LabelDto) {
		//int lId = Integer.parseInt(id);
		List<UserLabel> list = this.getLableByUserId(token);

		try {
			if (list != null) {
				Optional<UserLabel> data = list.stream().filter(t -> t.getUserId()==id).findFirst();
				data.ifPresent(da -> {
					da.setLableName(LabelDto.getName());
					da.setUpdateDateAndTime(LocalDateTime.now());
					labelRepository.save(da);
				});
				
				if (data.equals(Optional.empty())) {
					return null;
				}
			}
		} catch (Exception ae) {
			throw new NoteNotFoundException("Label Record Not Exist");
		}

		return list.get(0);
	}

	@Transactional
	@Override
	public UserLabel addNotesToLabel(long labelId, long noteId, String token) {
		long id=jwtGenerate.parseJWT(token);
		UserDemo user=userRepository.getUserById(id);
		if(user!=null)
		{
			try
			{
				NoteInformation note=userNoteRepository.findNoteById(noteId);
				UserLabel label=labelRepository.findLabelById(labelId);
				label.getNotesList().add(note);
				labelRepository.save(label);
				return label;

			}catch(Exception e)
			{
				throw new UserException(HttpStatus.BAD_GATEWAY,env.getProperty("502"));
			}
		}
		return null;


	}
}


