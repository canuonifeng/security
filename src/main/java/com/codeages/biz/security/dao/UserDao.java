package com.codeages.biz.security.dao;

import org.springframework.stereotype.Repository;

import com.codeages.biz.base.BaseDao;
import com.codeages.biz.security.entity.User;

@Repository
public interface UserDao extends BaseDao<User> {

	public User getByUsername(String userName);
	
	public User getByEmail(String email);
	
}
