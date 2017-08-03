package com.edu.biz.security.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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
import com.edu.biz.org.entity.Faculty;
import com.edu.biz.org.service.FacultyService;
import com.edu.biz.security.dao.UserDao;
import com.edu.biz.security.dao.specification.UserSpecification;
import com.edu.biz.security.entity.Organization;
import com.edu.biz.security.entity.Role;
import com.edu.biz.security.entity.User;
import com.edu.biz.security.entity.UserStatus;
import com.edu.biz.security.event.CreateUserEvent;
import com.edu.biz.security.service.OrgService;
import com.edu.biz.security.service.RoleService;
import com.edu.biz.security.service.UserService;
import com.edu.biz.validgroup.Create;
import com.edu.core.exception.InvalidParameterException;
import com.edu.core.exception.NotFoundException;
import com.edu.core.exception.ServiceException;
import com.edu.core.util.BeanUtils;

@Service
public class UserServiceImpl extends BaseService implements UserService, UserDetailsService {
	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleService roleService;

	@Autowired
	private OrgService orgService;
	
	@Autowired
	private FacultyService facultyService;

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
	@Validated({ Create.class })
	public User createUser(User user) {
		this.filterOrg(user);
		this.filterFaculty(user);
		this.filterRole(user);
		if(!this.checkUserName(user.getUsername(), null)){
			throw new ServiceException("406","用户名已被占用");
		}
		String salt = getRandomString(16);
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String password = encoder.encodePassword(user.getPassword(), salt);
		user.setSalt(salt);
		user.setPassword(password);

		this.filterOrg(user);
		this.filterFaculty(user);

		user = userDao.save(user);
		applicationContext.publishEvent(new CreateUserEvent(user));
		return user;
	}

	private void filterFaculty(User user) {
		if (null != user.getFaculty() && null == user.getFaculty().getId()) {
			user.setFaculty(null);
		}
		
		if (null != user.getFaculty() && null != user.getFaculty().getId()) {
			Faculty faculty = facultyService.getFaculty(user.getFaculty().getId());
			if (null == faculty) {
				throw new NotFoundException("院系"+user.getFaculty().getId()+"不存在") ;
			}
		}
	}

	private void filterOrg(User user) {
		if (null != user.getOrg() && null == user.getOrg().getId()) {
			user.setOrg(null);
		}

		if (null != user.getOrg() && null != user.getOrg().getId()) {
			Organization org = orgService.getOrg(user.getOrg().getId());
			if (null == org) {
				throw new NotFoundException("组织机构#"+user.getOrg().getId()+"不存在") ;
			}
		}
	}
	
	private void filterRole(User user) {
		if (null != user.getRoles()) {
			for (Role validateRole : user.getRoles()) {
				if (null != validateRole.getId()) {
					Role role = roleService.getRole(validateRole.getId());
					if (null == role) {
						throw new NotFoundException("角色#"+validateRole.getId()+"不存在") ;
					}
				}
			}
		}
	}

	public User getUserById(Long id) {
		return userDao.findOne(id);
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
	@Validated
	public User updateUser(User user) {
		this.filterOrg(user);
		this.filterFaculty(user);
		this.filterRole(user);
		User savedUser = userDao.findOne(user.getId());
		
		if (null == savedUser) {
			throw new NotFoundException("用户不存在");
		}
		if (!this.checkUserName(user.getUsername(), user.getId())) {
			throw new ServiceException("406", "用户名已被占用");
		}
		// BeanUtils.copyPropertiesWithCopyProperties(user, savedUser, "username",
		// "email", "nickname","name","hpone","gender","");
		BeanUtils.copyPropertiesWithIgnoreProperties(user, savedUser, "id", "password", "salt", "createdTime",
				"updatedTime");
		return userDao.save(savedUser);
	}
	
	@Override
	@Validated
	public User changeUserStatus(Long id, UserStatus status) {
		User savedUser = userDao.findOne(id);
		if (null == savedUser) {
			throw new NotFoundException("用户不存在");
		}
		savedUser.setStatus(status);
		return userDao.save(savedUser);
	}

	@Override
	public boolean deleteUser(Long id) {
		try {
			userDao.delete(id);
		} catch (EmptyResultDataAccessException e) {
			logger.error("需要删除的用户:" + id + " 不存在！");
		}
		return null == userDao.findOne(id);
	}

	@Override
	public void setNewPassword(Long id, String oldPassword, String newPassword) {
		if (null == oldPassword || null == newPassword) {
			throw new InvalidParameterException("密码不能为空");
		}
		
		User user = userDao.findOne(id);
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String password = encoder.encodePassword(oldPassword, user.getSalt());
		if (!password.equals(user.getPassword())) {
			throw new InvalidParameterException("密码校验错误");
		}

		user.setSalt(getRandomString(16));
		user.setPassword(encoder.encodePassword(newPassword, user.getSalt()));
		userDao.save(user);
	}

	@Override
	public Boolean checkUserName(String userName, Long userId) {
		User user = userDao.getByUsername(userName);
		if (null == user) {
			return true;
		}
		if (user.getId().equals(userId)) {
			return true;
		}
		return false;
	}
}
