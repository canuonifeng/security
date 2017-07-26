package com.edu.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationEntryPoint authEntryPoint;

	@Autowired
	private UserDetailsService userDetailService;

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;

	@Bean
	public RememberMeServices rememberMeServices() {
		SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
		rememberMeServices.setAlwaysRemember(true);
		return rememberMeServices;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().anyRequest().authenticated();
		http.rememberMe().rememberMeServices(rememberMeServices());
		http.formLogin().loginProcessingUrl("/loginCheck").successHandler(authenticationSuccessHandler);
		http.httpBasic().authenticationEntryPoint(authEntryPoint);
		http.addFilterAt(new LoginFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailService);
		authenticationProvider.setPasswordEncoder(encoder);
		ReflectionSaltSource saltSource = new ReflectionSaltSource();
		saltSource.setUserPropertyToUse("salt");
		authenticationProvider.setSaltSource(saltSource);
		auth.authenticationProvider(authenticationProvider);
	}
}