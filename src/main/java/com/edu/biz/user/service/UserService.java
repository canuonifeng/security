package com.edu.biz.user.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edu.biz.user.entity.User;

public interface UserService {
	
	public Page<User> searchUsers(Map<String, String> conditions, Pageable pageable);

	public User createUser(User user);
}
