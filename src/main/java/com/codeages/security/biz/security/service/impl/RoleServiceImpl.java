package com.codeages.security.biz.security.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codeages.framework.base.BaseService;
import com.codeages.security.biz.security.dao.RoleDao;
import com.codeages.security.biz.security.dao.RolePermissionDao;
import com.codeages.security.biz.security.dao.specification.RoleSpecification;
import com.codeages.security.biz.security.entity.Role;
import com.codeages.security.biz.security.entity.RolePermission;
import com.codeages.security.biz.security.service.RoleService;

@Service
public class RoleServiceImpl extends BaseService implements RoleService {

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private RolePermissionDao rolePermissionDao;

	@Override
	public Role createRole(Role role) {
		return this.roleDao.save(role);
	}

	@Transactional
	@Override
	public Role updateRole(Role role) {
		this.rolePermissionDao.deleteByRoleId(role.getId());
		return this.roleDao.save(role);
	}

	@Override
	public Boolean checkCode(String code, Long roleId) {
		Role role = roleDao.getByCode(code);
		if (null == role) {
			return true;
		}
		if (role.getId().equals(roleId)) {
			return true;
		}
		return false;
	}

	@Override
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
			List<RolePermission> permissions = role.getRolePermissions();
			for (RolePermission permission : permissions) {
				permissionCodes.add(permission.getPermissionCode());
			}
		}
		return permissionCodes;
	}

	@Override
	public List<Role> findRoles(Map<String, Object> conditions) {
		return roleDao.findAll(new RoleSpecification(conditions), new Sort(Direction.DESC, "createdTime"));
	}

	@Override
	public List<RolePermission> findRolePermissionByRoleId(Long roleId) {
		return rolePermissionDao.findByRoleId(roleId);
	}
}
