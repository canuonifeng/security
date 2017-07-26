package com.edu.biz.security.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.edu.biz.base.BaseService;
import com.edu.biz.security.dao.UserDao;
import com.edu.biz.security.dao.specification.UserSpecification;
import com.edu.biz.security.entity.User;
import com.edu.biz.security.event.CreateUserEvent;
import com.edu.biz.security.service.RoleService;
import com.edu.biz.security.service.UserService;
import com.edu.core.exception.ServiceException;

@Service
public class UserServiceImpl extends BaseService implements UserService, UserDetailsService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleService roleService;

	@Override
	public Page<User> searchUsers(Map<String, Object> conditions, Pageable pageable) {
		return userDao.findAll(new UserSpecification(conditions), pageable);
	}

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		User user = userDao.getByUsername(name);
		return user;
	}

	@Override
	@Validated
	public User createUser(User user) {
		String salt = getRandomString(16);
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String password = encoder.encodePassword(user.getPassword(), salt);
		user.setSalt(salt);
		user.setPassword(password);
		user = userDao.save(user);
		applicationContext.publishEvent(new CreateUserEvent(user));
		return user;
	}

	@Override
	public boolean isAdmin() {
		return findCurrentUserRoleCodes().contains("ROLE_SUPER_ADMIN");
	}

	@Override
	public Set<String> findCurrentUserPermissionCodes() {
		return roleService.findByPermissionCodes(findCurrentUserRoleCodes());
	}

	private Set<String> findCurrentUserRoleCodes() {
		Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
				.getAuthorities();
		Set<String> roleCodes = new HashSet<String>();
		for (GrantedAuthority grantedAuthority : authorities) {
			roleCodes.add(grantedAuthority.getAuthority());
		}
		return roleCodes;
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

	@Override
	public User updateUser(User user) {
		User savedUser = userDao.findOne(user.getId());
		if(null == savedUser) {
			throw new ServiceException("404","用户不存在");
		}
//		BeanUtils.copyProperties(user, savedUser, "password","salt", "createdTime","updatedTime");
		return userDao.save(user);
	}

	@Override
	public boolean deleteUser(Long id) {
		try{
			userDao.delete(id);
		}
		catch (EmptyResultDataAccessException e) {
			logger.error("需要删除的用户:"+id+" 不存在！");
		}
		return null == userDao.findOne(id);
	}

}
