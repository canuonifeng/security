package com.edu.biz.schoolroll.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.edu.biz.schoolroll.entity.Classroom;

public interface ClassroomService {
	
	public Classroom createClassroom(Classroom classroom);
	
	public Classroom updateClassroom(Classroom classroom);
	
	public Boolean deleteClassroom(Long id);
	
	public Classroom getClassroom(Long id);
	
	public Boolean checkCode(String code,Long classroomId);
	
	public Classroom getClassroomByCode(String code);
	
	public Page<Classroom> searchClassroom(Map<String, Object> conditions, Pageable pageable);
	
	public int countByMajorId(Long majorId);
}
