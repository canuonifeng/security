package com.codeages.security.biz.security.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.codeages.framework.authentication.PermissionsProvider;
import com.codeages.security.biz.security.entity.User;
import com.codeages.security.biz.security.entity.UserStatus;

public interface UserService extends PermissionsProvider {

	public Page<User> searchUsers(Map<String, Object> conditions, Pageable pageable);

	public User createUser(User user);

	public User updateUser(User user);
	
	public User changeUserStatus(Long id, UserStatus status);
	
	public Boolean checkUserName(String userName,Long userId);
	
	public Boolean checkEmail(String email,Long userId);

	public boolean deleteUser(Long id);
	
	public User getUserById(Long id);
	
	public boolean isAdmin();
	
	public void setNewPassword(Long id, String oldPassword, String newPassword);
}
