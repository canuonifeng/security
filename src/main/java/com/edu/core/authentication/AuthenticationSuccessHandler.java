package com.edu.core.authentication;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.edu.biz.security.entity.User;
import com.edu.biz.security.service.UserService;
import com.edu.core.ResponseBodyWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AuthenticationSuccessHandler
		implements org.springframework.security.web.authentication.AuthenticationSuccessHandler {

	@Autowired
	private UserService userService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		User currenUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userService.getUserById(currenUser.getId());
		user.setLastLoginTime(new Date());
		user.setLastLoginIp(getIpAddr(request));
		userService.updateUser(user);
		ObjectMapper mapper = new ObjectMapper();
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("token", request.getSession().getId());
		response.getWriter().append(mapper.writeValueAsString(new ResponseBodyWrapper(map)));
		response.setContentType("application/json");
		response.setStatus(200);
	}

	private String getIpAddr(HttpServletRequest request) {
		String proxs[] = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP",
				"HTTP_X_FORWARDED_FOR" };
		String ip = null;
		for (String prox : proxs) {
			ip = request.getHeader(prox);
			if (null == ip || ip.trim().isEmpty() || "unknown".equalsIgnoreCase(ip)) {
				continue;
			} else {
				break;
			}
		}
		if (null == ip || ip.isEmpty()) {
			return request.getRemoteAddr();
		}
		return ip;
	}

}
