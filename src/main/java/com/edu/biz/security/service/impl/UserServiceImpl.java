package com.edu.biz.security.service.impl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.edu.biz.base.BaseService;
import com.edu.biz.security.dao.UserDao;
import com.edu.biz.security.dao.specification.UserSpecification;
import com.edu.biz.security.entity.User;
import com.edu.biz.security.service.UserService;

@Service
@Validated
public class UserServiceImpl extends BaseService implements UserService, UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Override
	public Page<User> searchUsers(Map<String, String> conditions, Pageable pageable) {
		return userDao.findAll(new UserSpecification(conditions), pageable);
	}

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		User user = userDao.getByUsername(name);
		return user;
	}

	@Override
	public User createUser(User user) {
		String salt = getRandomString(16);
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String password = encoder.encodePassword(user.getPassword(), salt);
		user.setSalt(salt);
		user.setPassword(password);
		return userDao.save(user);
	}

	private String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

}
