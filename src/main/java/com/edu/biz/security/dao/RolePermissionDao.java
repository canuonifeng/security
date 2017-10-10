package com.edu.biz.security.dao;

import java.util.List;

import com.edu.biz.base.BaseDao;
import com.edu.biz.security.entity.RolePermission;

public interface RolePermissionDao extends BaseDao<RolePermission> {
	
	public List<RolePermission> findByRoleId(Long id);
	
	public void deleteByRoleId(Long id);
}
