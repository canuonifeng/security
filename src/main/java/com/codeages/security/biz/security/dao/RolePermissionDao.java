package com.codeages.security.biz.security.dao;

import java.util.List;

import com.codeages.framework.base.BaseDao;
import com.codeages.security.biz.security.entity.RolePermission;

public interface RolePermissionDao extends BaseDao<RolePermission> {
	
	public List<RolePermission> findByRoleId(Long id);
	
	public void deleteByRoleId(Long id);
}
