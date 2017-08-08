package com.edu.biz.org.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edu.biz.org.entity.Faculty;
import com.edu.biz.org.entity.FacultyStatus;

public interface FacultyService {

	public Faculty createFaculty(Faculty faculty);
	
	public Faculty updateFaculty(Faculty faculty);
	
	public Faculty changeFacultyStatus(Long id, FacultyStatus status);
	
	public Boolean deleteFaculty(Long id);
	
	public Faculty getFaculty(Long id);
	
	public Boolean checkCode(String code,Long facultyId);
	
	public Faculty getFacultyByCode(String code);
	
	public Page<Faculty> searchFaculty(Map<String, Object> conditions, Pageable pageable);
}
