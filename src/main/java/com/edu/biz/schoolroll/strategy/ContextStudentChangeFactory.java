package com.edu.biz.schoolroll.strategy;

import java.util.Map;

import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.schoolroll.entity.StudentChange;

public class ContextStudentChangeFactory {
	private Map<String, StudentChangeStrategy> strategyMap;
	private StudentChange change;
	private Student student;

	public ContextStudentChangeFactory(StudentChange change, Student student) {
		this.change = change;
		this.student = student;
	}

	public Map<String, StudentChangeStrategy> getStrategyMap() {
		return strategyMap;
	}

	public void setStrategyMap(Map<String, StudentChangeStrategy> strategyMap) {
		this.strategyMap = strategyMap;
	}

	public Student audit(String type) {
		return this.strategyMap.get(type).changeStudentField(this.change, this.student);
	}
}
