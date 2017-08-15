package com.edu.biz.teachingresources.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edu.biz.teachingresources.entity.Teacher;

public interface TeacherService {
	
	public Teacher createTeacher(Teacher teacher);
	
	public Teacher updateTeacher(Teacher teacher);
	
	public Boolean deleteTeacher(Long id);
	
	public Teacher getTeacher(Long id);
	
	public Page<Teacher> searchTeachers(Map<String, Object> conditions, Pageable pageable);
}
