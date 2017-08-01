package com.edu.core.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

import com.edu.core.ResponseWrapper;
import com.edu.core.authentication.AuthenticationEntryPoint;
import com.edu.core.authentication.AuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationEntryPoint authEntryPoint;

	@Autowired
	private UserDetailsService userDetailService;

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;

	@Bean
	public RememberMeServices rememberMeServices() {
		SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
		rememberMeServices.setAlwaysRemember(true);
		return rememberMeServices;
	}

	@Bean
	public HttpSessionCsrfTokenRepository getCsrfTokenRepository() {
		return new HttpSessionCsrfTokenRepository();
	}

	@Bean
	public AccessDeniedHandler getAccessDeniedHandler() {
		return new AccessDeniedHandler() {
			@Override
			public void handle(HttpServletRequest request, HttpServletResponse response,
					AccessDeniedException accessDeniedException) throws IOException, ServletException {
				ResponseWrapper err = new ResponseWrapper(accessDeniedException.getMessage());
				err.setMessage(accessDeniedException.getMessage());
				err.setStatus(String.valueOf(HttpServletResponse.SC_FORBIDDEN));

				ObjectMapper mapper = new ObjectMapper();
				response.getWriter().append(mapper.writeValueAsString(err));
				response.setContentType("application/json");
				response.setStatus(200);
			}
		};
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.rememberMe().rememberMeServices(rememberMeServices());
		http.httpBasic().authenticationEntryPoint(authEntryPoint);

		AuthenticationFilter authFilter = new AuthenticationFilter();
		authFilter.setAuthenticationManager(this.authenticationManagerBean());
		authFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
		authFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
		http.addFilterAt(authFilter, UsernamePasswordAuthenticationFilter.class);
		http.formLogin().successHandler(authenticationSuccessHandler).failureHandler(authenticationFailureHandler);
		http.authorizeRequests().antMatchers("/csrf-token").permitAll().anyRequest().authenticated();
		http.exceptionHandling().accessDeniedHandler(getAccessDeniedHandler());
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