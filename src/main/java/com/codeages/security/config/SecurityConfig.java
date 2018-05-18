package com.codeages.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.codeages.framework.authentication.AuthenticationProvider;
import com.codeages.framework.config.AuthenticationConfig;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends AuthenticationConfig {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(getAuthenticationProvider());
	}

	@Bean
	public AuthenticationProvider getAuthenticationProvider() {
		return new AuthenticationProvider(userDetailsService);
	}
}
