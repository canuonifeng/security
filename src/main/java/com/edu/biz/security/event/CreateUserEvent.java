package com.edu.biz.security.event;

import org.springframework.context.ApplicationEvent;

import com.edu.biz.security.entity.User;

public class CreateUserEvent extends ApplicationEvent {

	private static final long serialVersionUID = -8961286941724042456L;
	
	public CreateUserEvent(User User) {
		super(User);
	}

}
