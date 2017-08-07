package com.edu.biz.schoolroll.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.edu.biz.base.BaseService;
import com.edu.biz.schoolroll.dao.ClassroomDao;
import com.edu.biz.schoolroll.entity.Classroom;
import com.edu.biz.schoolroll.service.ClassroomService;

@Service
public class ClassroomServiceImpl extends BaseService implements ClassroomService {
	@Autowired
	private ClassroomDao classroomDao;
	@Override
	public Classroom createClassroom(Classroom major) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Boolean deleteClassroom(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Classroom getClassroom(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Classroom getClassroomByCode(String code) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Page<Classroom> searchClassroom(Map<String, Object> conditions, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Classroom updateClassroom(Classroom major) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Boolean checkCode(String code, Long classroomId) {
		// TODO Auto-generated method stub
		return null;
	}

}
