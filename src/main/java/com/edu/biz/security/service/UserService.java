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
	
	public Boolean checkUserName(String userName,Long userId);

	public boolean deleteUser(Long id);
	
	public User getUserById(Long id);
	
	public boolean isAdmin();
	
	public Set<String> findCurrentUserPermissionCodes();
	
	public void setNewPassword(Long id, String oldPassword, String newPassword);

	public Long countByOrgId(Long orgId);
	
	public Set<User> findByOrgId(Long orgId);
}
