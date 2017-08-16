package com.edu.biz.teachingres.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edu.biz.teachingres.entity.Teacher;
import com.edu.biz.teachingres.entity.TeacherStatus;

public interface TeacherService {
	
	public Teacher createTeacher(Teacher teacher);
	
	public Teacher updateTeacher(Teacher teacher);
	
	public Teacher changeTeacherStatus(Long id, TeacherStatus status);
	
	public Boolean deleteTeacher(Long id);
	
	public Teacher getTeacher(Long id);
	
	public Page<Teacher> searchTeachers(Map<String, Object> conditions, Pageable pageable);
}
