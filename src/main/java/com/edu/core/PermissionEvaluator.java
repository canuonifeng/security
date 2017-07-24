package com.edu.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.edu.biz.security.service.RoleService;

@Service
public class PermissionEvaluator implements org.springframework.security.access.PermissionEvaluator {

	@Autowired
	private RoleService roleService;
	
	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		Set<String> roleCodes = new HashSet<String>();
		for (GrantedAuthority grantedAuthority : authorities) {
			roleCodes.add(grantedAuthority.getAuthority());
		}
		
		if(roleCodes.contains("ROLE_SUPER_ADMIN")) {
			return true;
		}
		
		Set<String> permissions = roleService.findByPermissionCodes(roleCodes);
		return permissions.contains(targetDomainObject.toString()+":"+permission.toString());
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
