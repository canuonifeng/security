package com.edu.core;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.edu.biz.security.entity.User;
import com.edu.biz.security.service.UserService;
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
		response.getWriter().append(mapper.writeValueAsString(new ResponseWrapper("ok")));
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
