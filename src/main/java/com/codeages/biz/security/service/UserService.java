package com.codeages.biz.security.service;

import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.codeages.biz.security.entity.User;
import com.codeages.biz.security.entity.UserStatus;

public interface UserService {

	public Page<User> searchUsers(Map<String, Object> conditions, Pageable pageable);

	public User createUser(User user);

	public User updateUser(User user);
	
	public User changeUserStatus(Long id, UserStatus status);
	
	public Boolean checkUserName(String userName,Long userId);
	
	public Boolean checkEmail(String email,Long userId);

	public boolean deleteUser(Long id);
	
	public User getUserById(Long id);
	
	public boolean isAdmin();
	
	public Set<String> findCurrentUserPermissionCodes();
	
	public void setNewPassword(Long id, String oldPassword, String newPassword);
}