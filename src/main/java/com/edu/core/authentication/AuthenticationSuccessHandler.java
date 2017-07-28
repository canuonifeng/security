package com.edu.core.authentication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.edu.core.ResponseBodyWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuthenticationSuccessHandler
		implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		ObjectMapper mapper = new ObjectMapper();
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("token", request.getSession().getId());
		response.getWriter().append(mapper.writeValueAsString(new ResponseBodyWrapper(map)));
		response.setContentType("application/json");
		response.setStatus(200);
	}

}
