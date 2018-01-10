package com.codeages.biz.security.dao;

import java.util.List;

import com.codeages.biz.security.entity.RolePermission;
import com.codeages.framework.base.BaseDao;

public interface RolePermissionDao extends BaseDao<RolePermission> {
	
	public List<RolePermission> findByRoleId(Long id);
	
	public void deleteByRoleId(Long id);
}
