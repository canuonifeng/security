package com.edu.biz.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class BaseService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	protected ApplicationContext applicationContext;
}
