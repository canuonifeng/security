package com.edu.biz.security.service;

import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edu.biz.security.entity.Role;

public interface RoleService {
	public Role createRole(Role role);

	public Role updateRole(Role role);

	public boolean deleteRole(Long id);

	public Role getRole(Long id);

	public Page<Role> searchRoles(Map<String, Object> conditions, Pageable pageable);

	public Set<String> findByPermissionCodes(Set<String> roleCodes);
}
