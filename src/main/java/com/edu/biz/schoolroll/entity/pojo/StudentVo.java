package com.edu.biz.schoolroll.entity.pojo;

import java.util.List;

import com.edu.biz.schoolroll.entity.Student;
import com.edu.biz.teaching.entity.GradedSubject;
import com.edu.biz.teaching.entity.GradedSubjectResult;

public class StudentVo extends Student {
	
	private List<GradedSubject> gradedSubjects;
	private List<GradedSubjectResult> gradedSubjectResults;
	
	public List<GradedSubject> getGradedSubjects() {
		return gradedSubjects;
	}

	public void setGradedSubjects(List<GradedSubject> gradedSubjects) {
		this.gradedSubjects = gradedSubjects;
	}

	public List<GradedSubjectResult> getGradedSubjectResults() {
		return gradedSubjectResults;
	}

	public void setGradedSubjectResults(List<GradedSubjectResult> gradedSubjectResults) {
		this.gradedSubjectResults = gradedSubjectResults;
	}
	
}
