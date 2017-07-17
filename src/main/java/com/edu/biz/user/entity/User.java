package com.edu.biz.user.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.edu.biz.base.BaseEntity;

@Entity
@Table(name = "user")
public class User extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;
	private String nickname;
	private String email;
	private String salt;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}
}
