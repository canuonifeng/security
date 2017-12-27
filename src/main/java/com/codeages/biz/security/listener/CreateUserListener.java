package com.codeages.biz.security.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.codeages.biz.security.entity.User;
import com.codeages.biz.security.event.CreateUserEvent;

@Component
public class CreateUserListener implements ApplicationListener<CreateUserEvent> {
	public final static Logger logger = LoggerFactory.getLogger(CreateUserListener.class);

	@Async
	@Override
	public void onApplicationEvent(CreateUserEvent event) {
		User user = (User) event.getSource();
		logger.debug(user.getNickname() + " 用户被创建，" + user);

	}

}
