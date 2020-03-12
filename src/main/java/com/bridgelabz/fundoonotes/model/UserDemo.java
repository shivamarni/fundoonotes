package com.bridgelabz.fundoonotes.model;
/**
 * @author:shiva
 */

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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserDemo {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long  userId;
	@Column
	private String name;
	@Column
	private String email;
	@Column
	private long mobile;
	@Column
	private String password;
	@Column
	private boolean isVerified;
	@Column
	private LocalDateTime date;
	
	
	@OneToMany(targetEntity = NoteInformation.class)
	@JoinColumn(name="userId")
	private List<NoteInformation> notes;
	
	@ManyToMany(cascade = CascadeType.ALL)
	public List<NoteInformation> collabrate;
	
	@OneToMany(targetEntity = UserLabel.class)
	@JoinColumn(name="userId")
	public List<UserLabel> label;
	
//		
//    @ManyToMany
//    @JsonIgnore
//	public List<NoteInformation> getCollabrator() {
//		
//		return collabrate;
//	}
	 


}
