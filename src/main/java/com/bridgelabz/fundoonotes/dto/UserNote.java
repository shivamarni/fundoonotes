package com.bridgelabz.fundoonotes.dto;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data

public class UserNote {
	@NotNull
	private String title;
	@NotNull
	private String description;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "UserNote [title=" + title + ", description=" + description + "]";
	}
	
	

}
