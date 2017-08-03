package com.edu.biz.security.dao;

import java.util.Set;

import org.springframework.stereotype.Repository;

import com.edu.biz.base.BaseDao;
import com.edu.biz.security.entity.User;

@Repository
public interface UserDao extends BaseDao<User> {

	public User getByUsername(String userName);
}
