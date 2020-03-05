package com.bridgelabz.fundoonotes.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
public class NoteInformation {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer noteId;
	@Column
	private String title;
	@Column
	private String description;
	@Column
	private int isArcheived;
	@Column
	private int isPinned;
	@Column
	private int isTrashed;
	@Column
	private LocalDateTime createDateAndTime;
	@Column
	private LocalDateTime updateDateAndTime;
	@Column
	private LocalDateTime reminder;
	@Column
	private String color;
	
	@ManyToOne
	UserDemo user;
	
	public int getUser() {
		return user.getUserId();
	}
	public void setUser(UserDemo user) {
		this.user = user;
	}
	public Integer getNoteId() {
		return noteId;
	}
	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}
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
	public int getIsArcheived() {
		return isArcheived;
	}
	public void setIsArcheived(int isArcheived) {
		this.isArcheived = isArcheived;
	}
	public int getIsPinned() {
		return isPinned;
	}
	public void setIsPinned(int isPinned) {
		this.isPinned = isPinned;
	}
	public int getIsTrashed() {
		return isTrashed;
	}
	public void setIsTrashed(int isTrashed) {
		this.isTrashed = isTrashed;
	}
	public LocalDateTime getCreateDateAndTime() {
		return createDateAndTime;
	}
	public void setCreateDateAndTime(LocalDateTime createDateAndTime) {
		this.createDateAndTime = createDateAndTime;
	}
	public LocalDateTime getUpdateDateAndTime() {
		return updateDateAndTime;
	}
	public void setUpdateDateAndTime(LocalDateTime updateDateAndTime) {
		this.updateDateAndTime = updateDateAndTime;
	}
	public LocalDateTime getReminder() {
		return reminder;
	}
	public void setReminder(LocalDateTime reminder) {
		this.reminder = reminder;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	@Override
	public String toString() {
		return "NoteInformation [noteId=" + noteId + ", title=" + title + ", description=" + description
				+ ", isArcheived=" + isArcheived + ", isPinned=" + isPinned + ", isTrashed=" + isTrashed
				+ ", createDateAndTime=" + createDateAndTime + ", updateDateAndTime=" + updateDateAndTime
				+ ", reminder=" + reminder + ", color=" + color + ", user=" + user + "]";
	}
	

}
