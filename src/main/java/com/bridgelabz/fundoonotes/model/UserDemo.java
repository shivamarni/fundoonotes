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
	
	
	@OneToMany(cascade =CascadeType.ALL,targetEntity = NoteInformation.class,fetch=FetchType.LAZY)
	@JoinColumn(name="userId")
	private List<NoteInformation> notes;
	
	@ManyToMany
	public List<NoteInformation> collabrate;
	
	@OneToMany(cascade =CascadeType.ALL,targetEntity = UserLabel.class,fetch=FetchType.LAZY)
	@JoinColumn(name="userId")
	public List<UserLabel> label;
	
		

	public List<NoteInformation> getCollabrator() {
		
		return collabrate;
	}
	 


}
