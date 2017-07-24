package com.edu.core;

import java.io.Serializable;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.edu.biz.security.service.UserService;

@Component
public class PermissionEvaluator implements org.springframework.security.access.PermissionEvaluator {

	@Autowired
	private UserService userService;

	@Override
	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

		if (userService.isAdmin()) {
			return true;
		}

		Set<String> permissions = userService.findCurrentUserPermissionCodes();
		return permissions.contains(targetDomainObject.toString() + ":" + permission.toString());
	}

	@Override
	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
			Object permission) {
		return false;
	}

}
