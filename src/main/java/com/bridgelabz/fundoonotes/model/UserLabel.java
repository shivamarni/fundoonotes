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
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserLabel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long lable_id;
	@Column
	private String lableName;
	@Column
	private long userId;
	@Column
	private LocalDateTime UpdateDateAndTime;
	
	@ManyToMany(fetch=FetchType.EAGER,cascade = CascadeType.PERSIST)
	private List<NoteInformation> notesList;

		
	}
	

	
	


