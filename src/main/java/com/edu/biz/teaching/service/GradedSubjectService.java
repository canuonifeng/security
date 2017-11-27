package com.edu.biz.teaching.service;

import java.util.List;
import java.util.Map;

import com.edu.biz.teaching.entity.GradedSubject;
import com.edu.biz.teaching.entity.GradedSubjectResult;

public interface GradedSubjectService {
	
	public GradedSubject createGradedSubject(GradedSubject gradedSubject);
	
	public Boolean deleteGradedSubject(Long id);

	public GradedSubjectResult createResult(GradedSubjectResult gradedSubjectResult);
	
	public GradedSubjectResult updateResult(GradedSubjectResult gradedSubjectResult);

	public List<GradedSubject> findGradedSubjects(Map<String, Object> conditions);

	public List<GradedSubjectResult> findGradedSubjectResults(Map<String, Object> map);

	public GradedSubjectResult getGradedSubjectResult(Map<String, Object> map);
	
	public GradedSubject updateGradedSubject(GradedSubject gradedSubject);
}
