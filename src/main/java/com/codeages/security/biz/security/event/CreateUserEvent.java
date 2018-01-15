package com.codeages.security.biz.security.event;

import org.springframework.context.ApplicationEvent;

import com.codeages.security.biz.security.entity.User;

public class CreateUserEvent extends ApplicationEvent {

	private static final long serialVersionUID = -8961286941724042456L;
	
	public CreateUserEvent(User user) {
		super(user);
	}

}
