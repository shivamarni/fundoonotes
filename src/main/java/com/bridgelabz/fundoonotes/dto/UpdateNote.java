package com.bridgelabz.fundoonotes.dto;

public class UpdateNote {
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "UpdateNote [title=" + title + "]";
	}
	

}
