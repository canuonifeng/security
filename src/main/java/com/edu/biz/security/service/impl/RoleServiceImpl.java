package com.edu.biz.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.security.dao.RoleDao;
import com.edu.biz.security.entity.Role;
import com.edu.biz.security.service.RoleService;

@Service
public class RoleServiceImpl extends BaseService implements RoleService{

	@Autowired
	private RoleDao roleDao;
	
	public Role createRole(Role role) {
		return this.roleDao.save(role);
	}
}
