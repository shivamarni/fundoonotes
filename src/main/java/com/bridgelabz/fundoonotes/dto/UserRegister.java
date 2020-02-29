package com.bridgelabz.fundoonotes.dto;
/**
 * @author:shiva
 */
import java.sql.Date;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
public class UserRegister {
	private String name;
	private String email;
	private String password;
	private long mobile;

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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getMobile() {
		return mobile;
	}
	public void setMobile(long mobile) {
		this.mobile = mobile;
	}


}
