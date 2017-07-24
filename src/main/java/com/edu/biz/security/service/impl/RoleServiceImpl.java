package com.edu.biz.security.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.security.dao.RoleDao;
import com.edu.biz.security.dao.specification.RoleSpecification;
import com.edu.biz.security.entity.Permission;
import com.edu.biz.security.entity.Role;
import com.edu.biz.security.service.RoleService;

@Service
public class RoleServiceImpl extends BaseService implements RoleService {

	@Autowired
	private RoleDao roleDao;

	public Role createRole(Role role) {
		return this.roleDao.save(role);
	}

	public Role updateRole(Role role) {
		return this.roleDao.save(role);
	}

	public boolean deleteRole(Long id) {
		roleDao.delete(id);
		return null == roleDao.findOne(id);
	}

	@Override
	public Role getRole(Long id) {
		return roleDao.findOne(id);
	}

	@Override
	public Page<Role> searchRoles(Map<String, Object> conditions, Pageable pageable) {
		return roleDao.findAll(new RoleSpecification(conditions), pageable);
	}

	@Override
	public Set<String> findByPermissionCodes(Set<String> roleCodes) {
		List<Role> roles = roleDao.findByCodeIn(roleCodes);
		Set<String> permissionCodes = new HashSet<String>();
		for (Role role : roles) {
			List<Permission> permissions = role.getPermissions();
			for (Permission permission : permissions) {
				permissionCodes.add(permission.getName());
			}
		}
		return permissionCodes;
	}
}
