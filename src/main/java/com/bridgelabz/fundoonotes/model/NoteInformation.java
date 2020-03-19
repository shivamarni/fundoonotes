package com.bridgelabz.fundoonotes.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NoteInformation {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long noteId;
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
	
//	@ManyToOne
//	UserDemo user;
//
//	@ManyToMany(fetch=FetchType.EAGER,cascade = CascadeType.PERSIST)
//	UserLabel userLabel;
	//@JoinTable(name="note_labels",joinColumns = {@JoinColumn(name="lableId")},inverseJoinColumns = {@JoinColumn(name="noteId")})
	@ManyToMany(cascade = CascadeType.ALL)
	private List<UserLabel> label;

	

}
