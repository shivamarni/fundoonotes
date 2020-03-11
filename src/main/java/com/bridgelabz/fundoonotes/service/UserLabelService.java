package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.UpdateLabel;
import com.bridgelabz.fundoonotes.dto.UserLabelDto;
import com.bridgelabz.fundoonotes.model.UserLabel;

public interface UserLabelService {

	
	
	List<UserLabel> getAllLables();

	UserLabel createLable(UserLabelDto labelDto, String token);

	List<UserLabel> removeLabel(String token, long id);

	List<UserLabel> getLableByUserId(String token);

	UserLabel getLableById(long id);

	UserLabel updateLabel(long id, String token, UpdateLabel LabelDto);

	UserLabel addNotesToLabel(long labelId, long noteId, String token);
}


