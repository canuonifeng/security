package com.edu.biz.schoolroll.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edu.biz.schoolroll.entity.StudentChange;
import com.edu.biz.schoolroll.entity.StudentChangeLog;

public interface StudentChangeService {
	
	public StudentChange createStudentChange(StudentChange studentChange);
	
	public StudentChange updateStudentChange(StudentChange studentChange);
	
	public Boolean deleteStudentChange(Long id);
	
	public StudentChange getStudentChange(Long id);
	
	public List<StudentChange> findStudentChangeByStudentId(Long id);
	
	public Page<StudentChange> searchStudentChanges(Map<String, Object> conditions, Pageable pageable);
	
	public void audit(StudentChangeLog log);
}
