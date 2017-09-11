package com.edu.biz.factory;

import com.edu.biz.schoolroll.entity.ChangeType;

public class StudentChangeAuditFactory  {
	public Object create(ChangeType type) throws Exception{
		return Class.forName("com.edu.biz.strategy.student."+(type.toString().substring(0,1).toUpperCase() + type.toString().substring(1))).newInstance();
	}
}
