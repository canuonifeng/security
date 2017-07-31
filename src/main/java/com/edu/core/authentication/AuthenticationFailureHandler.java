package com.edu.core.authentication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.edu.core.ResponseBodyWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuthenticationFailureHandler
		implements org.springframework.security.web.authentication.AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setCharacterEncoding("UTF-8");
		Map<String, String> map = new HashMap<String, String>();
		ResponseBodyWrapper responseWrapper = new ResponseBodyWrapper(map);
		responseWrapper.setStatus(HttpServletResponse.SC_UNAUTHORIZED + "");
		responseWrapper.setMessage("用户名或密码错误");

		ObjectMapper mapper = new ObjectMapper();
		response.getWriter().append(mapper.writeValueAsString(responseWrapper));
		response.setContentType("application/json");
		response.setStatus(200);
	}

}
