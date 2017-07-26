package com.edu.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {
	
	private boolean postOnly = true;
	
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException(
					"Authentication method not supported: " + request.getMethod());
		}
		
		Map<String, String> map = readBody(request);
		String username = map.get("username");
		String password = map.get("password");

		if (username == null) {
			username = "";
		}

		if (password == null) {
			password = "";
		}

		username = username.trim();

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				username, password);

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	private Map<String, String> readBody(HttpServletRequest request) {
		StringBuffer stringBuffer = new StringBuffer();
		try {
			BufferedReader reader = request.getReader();
			String line = null;
			while (null != (line = reader.readLine())) {
				stringBuffer.append(reader.readLine());
			}
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(stringBuffer.toString(), Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
