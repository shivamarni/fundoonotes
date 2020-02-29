package com.bridgelabz.fundoonotes.model;
/**
 * @author:shiva
 */

import java.sql.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity

public class UserDemo {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int  userId;
	private String name;
	private String email;
	private long mobile;
	private String password;
	private String isVerified;
	private Date date;

	//no arguments constructor
	public UserDemo()
	{

	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getMobile() {
		return mobile;
	}
	public void setMobile(long mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getVerified() {
		return isVerified;
	}
	public void setVerified(String b) {
		this.isVerified = b;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "UserSet [userId=" + userId + ", name=" + name + ", email=" + email + ", mobile=" + mobile
				+ ", password=" + password + ", isVerified=" + isVerified + ", date=" + date + "]";
	}


}
