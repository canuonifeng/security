package com.codeages.security.biz.security.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.codeages.security.biz.security.entity.Role;
import com.codeages.security.biz.security.entity.RolePermission;

public interface RoleService {
	public Role createRole(Role role);

	public Role updateRole(Role role);

	public boolean deleteRole(Long id);
	
	public Boolean checkCode(String code, Long id);

	public Role getRole(Long id);

	public Page<Role> searchRoles(Map<String, Object> conditions, Pageable pageable);

	public Set<String> findByPermissionCodes(Set<String> roleCodes);

	public List<Role> findRoles(Map<String, Object> conditions);
	
	public List<RolePermission> findRolePermissionByRoleId(Long roleId);
}
