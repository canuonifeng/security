package com.edu.core.authentication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.edu.core.ResponseWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

	@Override
	public void commence(final HttpServletRequest request, final HttpServletResponse response,
			final AuthenticationException authException) throws IOException, ServletException {

		Map<String, String> map = new HashMap<String, String>();
		ResponseWrapper responseWrapper = new ResponseWrapper(map);
		responseWrapper.setStatus(String.valueOf(HttpServletResponse.SC_UNAUTHORIZED));
		responseWrapper.setMessage(authException.getMessage());
		
		ObjectMapper mapper = new ObjectMapper();
		response.getWriter().append(mapper.writeValueAsString(responseWrapper));
		response.setContentType("application/json");
		response.setStatus(200);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		setRealmName("DeveloperStack");
		super.afterPropertiesSet();
	}
}