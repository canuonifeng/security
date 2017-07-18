package com.edu.biz.security.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.security.dao.UserDao;
import com.edu.biz.security.dao.specification.UserSpecification;
import com.edu.biz.security.entity.User;
import com.edu.biz.security.service.UserService;

@Service
public class UserServiceImpl extends BaseService implements UserService, UserDetailsService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public Page<User> searchUsers(Map<String, String> conditions, Pageable pageable) {
		return userDao.findAll(new UserSpecification(conditions), pageable);
	}
	
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		return userDao.getByUsername(name);
	}

	@Override
	public User createUser(User user) {
		return userDao.save(user);
	}

}
