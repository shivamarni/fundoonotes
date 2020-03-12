package com.bridgelabz.fundoonotes.serviceimplementation;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.bridgelabz.fundoonotes.dto.UpdateLabel;
import com.bridgelabz.fundoonotes.dto.UserLabelDto;
import com.bridgelabz.fundoonotes.dto.UserNote;
import com.bridgelabz.fundoonotes.exception.LabelException;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.model.NoteInformation;
import com.bridgelabz.fundoonotes.model.UserDemo;
import com.bridgelabz.fundoonotes.model.UserLabel;
import com.bridgelabz.fundoonotes.repository.LabelRepository;
import com.bridgelabz.fundoonotes.repository.UserNoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.response.LabelResponse;
import com.bridgelabz.fundoonotes.service.UserLabelService;
import com.bridgelabz.fundoonotes.service.UserNoteService;
import com.bridgelabz.fundoonotes.utils.JWTGenerator;
@Service
@PropertySource("classpath:message.properties")
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
		public UserLabel createLable(UserLabelDto labelDto, String token) {

			long userId = (long) jwtGenerate.parseJWT(token);

			UserDemo user = userRepository.getUserById(userId)
					.orElseThrow(() -> new UserException(HttpStatus.BAD_GATEWAY, env.getProperty("104")));

			UserLabel label = (UserLabel) modelMapper.map(labelDto,UserLabel.class);
			 user.getLabel().add(label);
		
			return labelRepository.save(label);

		}
	
		@Transactional
		@Override
		public List<UserLabel> removeLabel(String token, long id) {
			//int lId = Integer.parseInt(id);
			List<UserLabel> list = this.getLableByUserId(token);
	
			Optional<UserLabel> data = null;
			try {
				if (list != null) {
					data = list.stream().filter(t -> t.getLable_id() == id).findFirst();
					
					data.ifPresent(da -> {
						labelRepository.delete(da);
					});
					if (data.equals(Optional.empty())) {
						return null;
					}
				}
			} catch (Exception ae) {
				ae.printStackTrace();
				//throw new NoteNotFoundException("user Record Not Exist");
			}
	
			return list;
		}
		@Transactional
		@Override
		public List<UserLabel> getLableByUserId(String token) {
			long id = (Long) jwtGenerate.parseJWT(token);
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
					Optional<UserLabel> data = list.stream().filter(t -> t.getLable_id()==id).findFirst();
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
				ae.printStackTrace();
				//throw new NoteNotFoundException("Label Record Not Exist");
			}
	
			return list.get(0);
		}
	
		@Transactional
		@Override
		public UserLabel addNotesToLabel(UserNote notes,String token,long labelId) {

			NoteInformation note = noteService.addNotes(notes, token);

			List<UserLabel> lables = this.getLableByUserId(token);
			UserLabel labelInfo;
			try {
				labelInfo = lables.stream().filter(t -> t.getLable_id() == labelId).findFirst()
						.orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, "Label Not Exist"));

				this.addExistingNotesToLabel(note.getNoteId(), token, labelId);
				
			} catch (Exception ae) {
				ae.printStackTrace();
				throw new LabelException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Notes not added to Label due to Internel server problem");
			}

			return labelInfo;
		
	
	
		}
		@Transactional
		@Override
		public ArrayList<String> sortByName() {
			ArrayList<String> list=new ArrayList<>();
			List<UserLabel> list1=this.getAllLables();
			list1.forEach(t->{
				list.add(t.getLableName());
			});
			Collections.sort(list,Collections.reverseOrder());
			return list;
		}
		@Transactional
		@Override
		public List<String> ascsortByName() {
			// TODO Auto-generated method stub
			ArrayList<String> list=new ArrayList<>();
			List<UserLabel> list1=this.getAllLables();
			list1.forEach(t->{
				list.add(t.getLableName());
			});
			Collections.sort(list);
			return list;
		}
		@Transactional
		@Override
		public boolean addExistingNotesToLabel(String noteTitle, String token, String labelName) {
			
			return false;
		}
		@Transactional
		@Override
		public boolean addExistingNotesToLabel(long noteId, String token, long labelId) {
			long userId = (long) jwtGenerate.parseJWT(token);

			List<NoteInformation> notes = userNoteRepository.findNoteByUserId(userId);

			UserLabel label = labelRepository.findLabelById(labelId);

			
				NoteInformation noteInfo = notes.stream().filter(t -> t.getNoteId() == noteId).findFirst()
						.orElseThrow(() -> new LabelException(HttpStatus.BAD_REQUEST, env.getProperty("301")));
			try {
				return noteInfo.getLabel().add(label);
				//return label.getNote().add(noteInfo);

			} catch (Exception ae) {
				throw new LabelException(HttpStatus.INTERNAL_SERVER_ERROR,
						"Notes not added to Label due to Internel server problem");
			}
		}

		@Transactional
		@Override
		public List<UserLabel> getAllLables() {
			List<UserLabel> notes = new ArrayList<>();

			labelRepository.findAll().forEach(notes::add);
			return notes;
		}
		
   
}


