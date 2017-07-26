package com.edu.biz.org.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edu.biz.org.entity.Faculty;

public interface FacultyService {

	public Faculty createFaculty(Faculty faculty);
	
	public Faculty updateFaculty(Faculty faculty);
	
	public Boolean deleteFaculty(Long id);
	
	public Faculty getFaculty(Long id);
	
	public Faculty getFacultyByCode(String code);
	
	public Page<Faculty> searchFaculty(Map<String, Object> conditions, Pageable pageable);
}
