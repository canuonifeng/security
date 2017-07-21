package com.edu.biz.security.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edu.biz.security.entity.User;

public interface UserService {
	
	public Page<User> searchUsers(Map<String, String> conditions, Pageable pageable);

	public User createUser(User user);
}