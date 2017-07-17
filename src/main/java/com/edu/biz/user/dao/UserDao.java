package com.edu.biz.user.dao;

import org.springframework.stereotype.Repository;

import com.edu.biz.base.BaseDao;
import com.edu.biz.user.entity.User;

@Repository
public interface UserDao extends BaseDao<User> {
	
}
