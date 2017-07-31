package com.edu.core.config;

import java.io.Serializable;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.core.Authentication;

import com.edu.biz.security.service.UserService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class PermissionConfig extends GlobalMethodSecurityConfiguration {

	private DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();

	@Autowired
	private UserService userService;

	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		expressionHandler.setPermissionEvaluator(getPermissionEvaluator());
		return expressionHandler;
	}

	@Bean
	public PermissionEvaluator getPermissionEvaluator() {
		return new PermissionEvaluator() {
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
		};
	}
}
