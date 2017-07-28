package com.edu.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import com.edu.core.PermissionEvaluator;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class PermissionConfig extends GlobalMethodSecurityConfiguration {

	private DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();

	@Autowired
	private PermissionEvaluator cpe;

	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		expressionHandler.setPermissionEvaluator(cpe);
		return expressionHandler;
	}
}
