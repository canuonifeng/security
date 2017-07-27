package com.edu.biz.security.service;

import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edu.biz.security.entity.User;

public interface UserService {

	public Page<User> searchUsers(Map<String, Object> conditions, Pageable pageable);

	public User createUser(User user);

	public User updateUser(User user);

	public boolean deleteUser(Long id);
	
	public User getUserById(Long id);
	
	public boolean isAdmin();
	
	public Set<String> findCurrentUserPermissionCodes();
	
	public void setNewPassword(Long id, String oldPassword, String newPassword);
}
