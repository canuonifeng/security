package com.edu.biz.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class BaseService {
	@Autowired
	protected ApplicationContext applicationContext;
}
