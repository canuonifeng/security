package com.codeages.security.biz.security.dao;

import org.springframework.stereotype.Repository;

import com.codeages.framework.base.BaseDao;
import com.codeages.security.biz.security.entity.User;

@Repository
public interface UserDao extends BaseDao<User> {

	public User getByUsername(String userName);
	
	public User getByEmail(String email);
	
}
