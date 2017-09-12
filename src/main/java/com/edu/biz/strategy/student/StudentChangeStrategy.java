package com.edu.biz.strategy.student;

import com.edu.biz.schoolroll.entity.StudentChangeLog;

public interface StudentChangeStrategy {
	
	public void audit(StudentChangeLog log);
}
