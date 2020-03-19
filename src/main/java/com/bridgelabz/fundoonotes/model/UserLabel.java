package com.bridgelabz.fundoonotes.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
//	@Column
//	private long userId;
	@Column
	private LocalDateTime UpdateDateAndTime;
	
	 @ManyToMany(mappedBy = "label")
	 @JsonIgnore
     private List<NoteInformation> note;

		
	}
	

	
	


