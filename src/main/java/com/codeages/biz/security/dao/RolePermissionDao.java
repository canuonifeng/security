package com.codeages.biz.security.dao;

import java.util.List;

import com.codeages.base.BaseDao;
import com.codeages.biz.security.entity.RolePermission;

public interface RolePermissionDao extends BaseDao<RolePermission> {
	
	public List<RolePermission> findByRoleId(Long id);
	
	public void deleteByRoleId(Long id);
}
