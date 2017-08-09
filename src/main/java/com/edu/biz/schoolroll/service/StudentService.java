package com.edu.biz.schoolroll.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edu.biz.schoolroll.entity.Student;

public interface StudentService {
	
	public Student createStudent(Student student);
	
	public Student updateStudent(Student student);
	
	public Boolean deleteStudent(Long id);
	
	public Student getStudent(Long id);
	
	public Page<Student> searchStudents(Map<String, Object> conditions, Pageable pageable);
	
	public Long countStudent(Map<String, Object> conditions);
}