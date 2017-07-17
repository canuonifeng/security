package com.edu.biz.user.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.edu.biz.user.dao.UserDao;
import com.edu.biz.user.dao.specification.UserSpecification;
import com.edu.biz.user.entity.User;
import com.edu.biz.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Override
	public Page<User> searchUsers(Map<String, String> conditions, Pageable pageable) {
		return userDao.findAll(new UserSpecification(conditions), pageable);
	}

	@Override
	public User createUser(User user) {
		return userDao.save(user);
	}
	
}
